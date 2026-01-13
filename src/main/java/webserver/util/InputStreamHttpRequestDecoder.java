package webserver.util;

import app.exception.BadRequestException;
import app.exception.InternalServerErrorException;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;
import webserver.http.header.HttpRequestHeader;
import webserver.http.header.HttpRequestHeader.HttpRequestHeaderBuilder;
import webserver.http.header.HttpRequestHeaderHead;
import webserver.http.parser.Parser;

/**
 * InputStream을 파싱하여 HttpRequest 객체를 생성하는 클래스
 */
public class InputStreamHttpRequestDecoder {

    private static final Logger log = LoggerFactory.getLogger(InputStreamHttpRequestDecoder.class);

    /**
     * InputStream을 파싱하여 HttpRequest 객체를 생성하는 클래스
     * @param in 클라이언트 Request의 InputStream
     * @return 파싱된 HttpRequest 객체
     */
    public static HttpRequest decode(
        InputStream in,
        Parser<String, HttpRequestHeaderHead> httpRequestHeaderHeadParser,
        Parser<String, HttpField> httpFieldParser) {

        try {
            BufferedInputStream bin = new BufferedInputStream(in);
            List<String> headerLines = readHeaderBytes(bin);
            HttpRequestHeaderBuilder builder = HttpRequestHeader.builder();

            // 첫 줄(헤더 헤드) 파싱
            HttpRequestHeaderHead httpRequestHead = httpRequestHeaderHeadParser.parse(headerLines.get(0));
            builder.version(httpRequestHead.version())
                .method(httpRequestHead.method())
                .uri(new URI(httpRequestHead.rawRequestUri()));

            // 나머지 필드 파싱 (반환값은 읽기 전용)
            List<HttpField> readOnlyFields = parseHttpFields(headerLines, builder, httpFieldParser);

            // 필드 정보를 토대로 body가 존재하는 경우에만 body를 처리
            Optional<Integer> hasBodies = readOnlyFields.stream()
                .filter(field -> field.key() == HttpFieldKey.CONTENT_LENGTH)
                .map(field -> Integer.parseInt(Objects.requireNonNull(field.getFirstSingleValue())))
                .findFirst();

            if (hasBodies.isPresent()) {
                int contentLength = hasBodies.get();
                byte[] body = new byte[contentLength];
                parseBody(bin, body);
                return new HttpRequest(builder.build(), new String(body, StandardCharsets.UTF_8));
            }

            return new HttpRequest(builder.build(), null);

        } catch (IOException e) {
            throw new InternalServerErrorException("Error reading request", e);
        } catch (URISyntaxException e) {
            throw new BadRequestException("Invalid URI syntax: " + e.getMessage());
        }
    }

    /**
     * InputStream에서 HTTP Header의 바이트를 읽음 (AI 도움으로 생성)
     * @param in InputStream
     * @return HTTP Header 바이트 배열
     * @throws IOException 읽기 실패 시 발생
     */
    public static List<String> readHeaderBytes(InputStream in) throws IOException {
        ByteArrayOutputStream headerBuffer = new ByteArrayOutputStream();
        int prev = -1, curr;
        int crlfCount = 0;

        // 헤더의 끝 (\r\n\r\n)까지 읽기
        while ((curr = in.read()) != -1) {
            headerBuffer.write(curr);

            if (prev == '\r' && curr == '\n') {
                crlfCount++;
                if (crlfCount == 2) {
                    break;
                }
            } else if (curr != '\r') {
                crlfCount = 0;
            }

            prev = curr;
        }

        String headerString = headerBuffer.toString(StandardCharsets.US_ASCII);
        return Arrays.asList(headerString.split("\r\n"));
    }

    /**
     * BufferedReader에서 HTTP Header 필드들을 읽고 파싱하여 HttpRequestHeaderBuilder에 추가
     * @param lines BufferedReader
     * @param builder HttpRequestHeaderBuilder
     * @param httpFieldParser HttpField 파서
     * @return 파싱된 HttpField 리스트로, 빌더로 생성된 필드 리스트와는 별도로 생성된 읽기 전용 불변 리스트
     * @throws IOException 읽기 또는 파싱에 실패한 경우 발생
     */
    public static List<HttpField> parseHttpFields(List<String> lines, HttpRequestHeaderBuilder builder,
        Parser<String, HttpField> httpFieldParser) throws IOException {
        List<HttpField> returns = new ArrayList<>();

        lines.stream().skip(1) // 첫 줄은 헤더 헤드이므로 건너뜀
            .forEach(line -> {
                HttpField parsedField = httpFieldParser.parse(line);
                builder.field(parsedField);
                returns.add(parsedField); // 별도의 리스트에도 추가
            });

        return Collections.unmodifiableList(returns);
    }

    public static void parseBody(InputStream inputStream, byte[] target) throws IOException {
        int totalRead = 0;
        while (totalRead < target.length) {
            int read = inputStream.read(target, totalRead, target.length - totalRead);
            if (read == -1) {
                throw new IOException("Unexpected EOF while reading HTTP body");
            }
            totalRead += read;
        }
    }
}

package webserver.util;

import app.exception.BadRequestException;
import app.exception.InternalServerErrorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.field.HttpField;
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
            // InputStream을 행 단위로 읽기 준비
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = Optional.ofNullable(reader.readLine())
                .orElseThrow(() -> new BadRequestException("Empty request"));

            // Request의 첫 line(Head 부분) 파싱
            HttpRequestHeaderHead httpRequestHead = httpRequestHeaderHeadParser.parse(line);
            HttpRequestHeaderBuilder builder = HttpRequestHeader.builder()
                .version(httpRequestHead.version())
                .method(httpRequestHead.method())
                .uri(new URI(httpRequestHead.rawRequestUri()));

            // 나머지 필드 파싱
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                builder.field(httpFieldParser.parse(line));
            }

            log.debug("End: {}", line == null ? "null" : "empty string");
            log.debug("reader.ready()? {}", reader.ready());

            // 헤더만 있는 경우
            if (!reader.ready()) {
                return new HttpRequest(builder.build(), null);
            }

            // header & body 구분을 위한 빈 줄 읽기
            reader.readLine();

            StringBuilder bodyBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                bodyBuilder.append(line).append("\n");
            }

            return new HttpRequest( builder.build(), bodyBuilder.toString());

        } catch (IOException e) {
            throw new InternalServerErrorException("Error reading request", e);
        } catch (URISyntaxException e) {
            throw new BadRequestException("Invalid URI syntax: " + e.getMessage());
        }
    }
}

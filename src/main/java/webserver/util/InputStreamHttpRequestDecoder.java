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
                .orElseThrow(() -> new RuntimeException("Empty request"));

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

            // TODO: Body 파싱 필요
            return new HttpRequest( builder.build(), "");

        } catch (IOException e) {
            throw new InternalServerErrorException("Error reading request", e);
        } catch (URISyntaxException e) {
            throw new BadRequestException("Invalid URI syntax: " + e.getMessage());
        }
    }
}

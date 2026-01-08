package webserver.http.parser;

import app.exception.BadRequestException;
import webserver.http.HttpMethod;
import webserver.http.HttpVersion;
import webserver.http.header.HttpRequestHeaderHead;

/**
 * HTTP Request의 Head 부분을 파싱하는 Parser 인터페이스 구현체
 */
public class HttpRequestHeaderHeadParser implements Parser<String, HttpRequestHeaderHead> {

    @Override
    public HttpRequestHeaderHead parse(String rawRequestLine) {
        String[] parts = rawRequestLine.split(" ");
        if (parts.length != 3) {
            throw new BadRequestException("Invalid HTTP request line: " + rawRequestLine);
        }

        return HttpRequestHeaderHead.builder()
            .method(HttpMethod.fromString(parts[0]))
            .rawRequestUri(parts[1])
            .version(HttpVersion.fromString(parts[2]))
            .build();
    }
}

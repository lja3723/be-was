package http.parser;

import http.HttpMethod;
import http.HttpVersion;
import http.header.HttpRequestHeaderHead;

/**
 * HTTP Request의 Head 부분을 파싱하는 Parser 인터페이스 구현체
 */
public class HttpRequestHeaderHeadParser implements Parser<HttpRequestHeaderHead, String> {

    @Override
    public HttpRequestHeaderHead parse(String rawRequestLine) {
        String[] parts = rawRequestLine.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid HTTP request line: " + rawRequestLine);
        }

        return new HttpRequestHeaderHead(
            HttpMethod.fromString(parts[0]),
            parts[1],
            HttpVersion.fromString(parts[2])
        );
    }
}

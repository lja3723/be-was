package webserver.http.parser;

import webserver.http.HttpMethod;
import webserver.http.HttpVersion;

/**
 * HTTP Request의 Head 부분을 파싱하는 Parser 인터페이스 구현체
 */
// TODO: 추후 무상태 클래스로 리팩터링 예정
public class HttpRequestHeadParserImpl implements HttpRequestHeadParser {

    private final String[] parts;

    public HttpRequestHeadParserImpl(String rawRequestLine) {
        this.parts = rawRequestLine.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid HTTP request line: " + rawRequestLine);
        }
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.fromString(parts[0]);
    }

    @Override
    public String getPath() {
        return parts[1];
    }

    @Override
    public HttpVersion getVersion() {
        return HttpVersion.fromString(parts[2]);
    }
}

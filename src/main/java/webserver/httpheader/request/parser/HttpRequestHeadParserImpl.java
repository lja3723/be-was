package webserver.httpheader.request.parser;

import webserver.httpheader.HttpMethod;
import webserver.httpheader.HttpVersion;

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

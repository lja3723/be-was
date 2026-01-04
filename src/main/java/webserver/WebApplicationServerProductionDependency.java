package webserver;

import webserver.http.parser.HttpFieldParser;
import webserver.http.parser.HttpFieldParserImpl;
import webserver.http.parser.HttpRequestHeaderHeadParser;
import webserver.http.parser.HttpRequestHeaderHeadParserImpl;

/**
 * Web Application Server의 Production Environment에서 사용되는 의존성 구현체
 */
public class WebApplicationServerProductionDependency implements WebApplicationServerDependency {

    private final HttpFieldParser httpFieldParser = new HttpFieldParserImpl();
    private final HttpRequestHeaderHeadParser httpRequestHeaderHeadParser = new HttpRequestHeaderHeadParserImpl();

    @Override
    public HttpFieldParser getHttpFieldParser() {
        return httpFieldParser;
    }

    @Override
    public HttpRequestHeaderHeadParser getHttpRequestHeaderHeadParser() {
        return httpRequestHeaderHeadParser;
    }

}

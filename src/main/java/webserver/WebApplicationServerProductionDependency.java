package webserver;

import webserver.http.field.HttpField;
import webserver.http.header.HttpRequestHeaderHead;
import webserver.http.parser.HttpFieldParser;
import webserver.http.parser.HttpRequestHeaderHeadParser;
import webserver.http.parser.Parser;

/**
 * Web Application Server의 Production Environment에서 사용되는 의존성 구현체
 */
public class WebApplicationServerProductionDependency implements WebApplicationServerDependency {

    private final Parser<HttpField, String> httpFieldParser = new HttpFieldParser();
    private final Parser<HttpRequestHeaderHead, String> httpRequestHeaderHeadParser = new HttpRequestHeaderHeadParser();

    @Override
    public Parser<HttpField, String> getHttpFieldParser() {
        return httpFieldParser;
    }

    @Override
    public Parser<HttpRequestHeaderHead, String> getHttpRequestHeaderHeadParser() {
        return httpRequestHeaderHeadParser;
    }

}

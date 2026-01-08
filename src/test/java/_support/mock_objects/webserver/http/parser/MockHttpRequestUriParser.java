package _support.mock_objects.webserver.http.parser;

import webserver.http.ContentType;
import webserver.http.uri.HttpRequestUri;
import webserver.http.parser.Parser;

/**
 * HttpRequestUrl을 단순히 rawData로 초기화하는 Mock Parser 구현체
 */
public class MockHttpRequestUriParser implements Parser<String, HttpRequestUri> {

    @Override
    public HttpRequestUri parse(String rawData) {
        return new HttpRequestUri(rawData, rawData, ContentType.TEXT_HTML);
    }
}

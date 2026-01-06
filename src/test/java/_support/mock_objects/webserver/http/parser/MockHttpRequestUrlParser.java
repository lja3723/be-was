package _support.mock_objects.webserver.http.parser;

import webserver.http.ContentType;
import webserver.http.field.HttpRequestUrl;
import webserver.http.parser.Parser;

/**
 * HttpRequestUrl을 단순히 rawData로 초기화하는 Mock Parser 구현체
 */
public class MockHttpRequestUrlParser implements Parser<HttpRequestUrl, String> {

    @Override
    public HttpRequestUrl parse(String rawData) {
        return new HttpRequestUrl(rawData, rawData, ContentType.TEXT_HTML);
    }
}

package webserver.http.header;

import webserver.http.field.HttpRequestUrl;
import webserver.http.HttpMethod;
import webserver.http.HttpVersion;
import webserver.http.field.HttpField;
import webserver.http.header.HttpHeader.HttpHeaderBuilder;
import webserver.http.parser.Parser;

/**
 * HTTP Request Header를 표현하는 Data Class
 */
public record HttpRequestHeader(HttpHeader common, HttpMethod method, HttpRequestUrl url) {

    /**
     * HttpRequestHeader 객체를 빌드하기 위한 빌더 클래스
     */
    public static class HttpRequestHeaderBuilder {

        private final Parser<HttpRequestUrl, String> httpRequestUrlParser;
        private final HttpHeaderBuilder commonBuilder;
        private HttpMethod method;
        private String url;

        public HttpRequestHeaderBuilder(Parser<HttpRequestUrl, String> httpRequestUrlParser) {
            this.httpRequestUrlParser = httpRequestUrlParser;
            this.commonBuilder = HttpHeader.builder();
        }

        public HttpRequestHeaderBuilder version(HttpVersion version) {
            commonBuilder.version(version);
            return this;
        }

        public HttpRequestHeaderBuilder field(HttpField field) {
            commonBuilder.field(field);
            return this;
        }

        public HttpRequestHeaderBuilder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public HttpRequestHeaderBuilder url(String url) {
            this.url = url;
            return this;
        }

        public HttpRequestHeader build() {
            return new HttpRequestHeader(
                commonBuilder.build(),
                method,
                httpRequestUrlParser.parse(url)
            );
        }
    }

    /**
     * HttpRequestHeaderBuilder 인스턴스를 반환하는 정적 팩토리 메서드
     */
    public static HttpRequestHeaderBuilder builder(Parser<HttpRequestUrl, String> httpRequestUrlParser) {
        return new HttpRequestHeaderBuilder(httpRequestUrlParser);
    }
}

package webserver.http.header;

import webserver.http.field.HttpRequestUri;
import webserver.http.HttpMethod;
import webserver.http.HttpVersion;
import webserver.http.field.HttpField;
import webserver.http.header.HttpHeader.HttpHeaderBuilder;
import webserver.http.parser.Parser;

/**
 * HTTP Request Header를 표현하는 Data Class
 */
public record HttpRequestHeader(HttpHeader common, HttpMethod method, HttpRequestUri uri) {

    /**
     * HttpRequestHeader 객체를 빌드하기 위한 빌더 클래스
     */
    public static class HttpRequestHeaderBuilder {

        private final Parser<String, HttpRequestUri> httpRequestUriParser;
        private final HttpHeaderBuilder commonBuilder;
        private HttpMethod method;
        private String uri;

        public HttpRequestHeaderBuilder(Parser<String, HttpRequestUri> httpRequestUriParser) {
            this.httpRequestUriParser = httpRequestUriParser;
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

        public HttpRequestHeaderBuilder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public HttpRequestHeader build() {
            return new HttpRequestHeader(
                commonBuilder.build(),
                method,
                httpRequestUriParser.parse(uri)
            );
        }
    }

    /**
     * HttpRequestHeaderBuilder 인스턴스를 반환하는 정적 팩토리 메서드
     */
    public static HttpRequestHeaderBuilder builder(Parser<String, HttpRequestUri> httpRequestUriParser) {
        return new HttpRequestHeaderBuilder(httpRequestUriParser);
    }
}

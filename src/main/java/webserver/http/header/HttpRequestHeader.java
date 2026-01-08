package webserver.http.header;

import webserver.http.uri.HttpRequestUri;
import webserver.http.HttpMethod;
import webserver.http.HttpVersion;
import webserver.http.field.HttpField;
import webserver.http.header.HttpHeader.HttpHeaderBuilder;

/**
 * HTTP Request Header를 표현하는 Data Class
 */
public record HttpRequestHeader(HttpHeader common, HttpMethod method, HttpRequestUri uri) {

    /**
     * HttpRequestHeader 객체를 빌드하기 위한 빌더 클래스
     */
    public static class HttpRequestHeaderBuilder {

        private final HttpHeaderBuilder commonBuilder;
        private HttpMethod method;
        private HttpRequestUri uri;

        public HttpRequestHeaderBuilder() {
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

        public HttpRequestHeaderBuilder uri(HttpRequestUri uri) {
            this.uri = uri;
            return this;
        }

        public HttpRequestHeader build() {
            return new HttpRequestHeader(
                commonBuilder.build(),
                method,
                uri
            );
        }
    }

    /**
     * HttpRequestHeaderBuilder 인스턴스를 반환하는 정적 팩토리 메서드
     */
    public static HttpRequestHeaderBuilder builder() {
        return new HttpRequestHeaderBuilder();
    }
}

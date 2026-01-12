package webserver.http.header;

import webserver.http.HttpMethod;
import webserver.http.HttpVersion;

/**
 * HTTP Request Header의 Head 부분을 표현하는 Data Class
 */
public record HttpRequestHeaderHead(HttpMethod method, String rawRequestUri, HttpVersion version) {

    public static class HttpRequestHeaderHeadBuilder {
        private HttpMethod method;
        private String rawRequestUri;
        private HttpVersion version;

        public HttpRequestHeaderHeadBuilder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public HttpRequestHeaderHeadBuilder rawRequestUri(String rawRequestUri) {
            this.rawRequestUri = rawRequestUri;
            return this;
        }

        public HttpRequestHeaderHeadBuilder version(HttpVersion version) {
            this.version = version;
            return this;
        }

        public HttpRequestHeaderHead build() {
            return new HttpRequestHeaderHead(method, rawRequestUri, version);
        }
    }

    public static HttpRequestHeaderHeadBuilder builder() {
        return new HttpRequestHeaderHeadBuilder();
    }
}

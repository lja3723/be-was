package http.header;

import http.ContentType;
import http.HttpStatus;
import http.HttpVersion;
import http.header.HttpHeader.HttpHeaderBuilder;
import http.field.HttpField;
import http.field.HttpFieldKey;

/**
 * HTTP Response Header를 표현하는 Data Class
 */
public record HttpResponseHeader(HttpHeader common, HttpStatus status) {

    /**
     * HTTP Response Header를 빌드하기 위한 빌더 클래스
     */
    public static class HttpResponseHeaderBuilder {
        private final HttpHeaderBuilder commonBuilder;
        private HttpStatus status;

        public HttpResponseHeaderBuilder() {
            this.commonBuilder = HttpHeader.builder();
        }

        public HttpResponseHeaderBuilder version(HttpVersion version) {
            commonBuilder.version(version);
            return this;
        }

        public HttpResponseHeaderBuilder field(HttpField field) {
            commonBuilder.field(field);
            return this;
        }

        public HttpResponseHeaderBuilder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public HttpResponseHeaderBuilder contentType(ContentType contentType) {
            String fieldValue = contentType.getValue();

            // TODO: 추후 UTF-8 이외의 charset 도 처리할 수 있도록 수정 필요
            // TODO: 좀 더 추상화가 필요
            String category = fieldValue.split("/")[0];
            if (category.equals("text") || category.equals("application")) {
                fieldValue += "; charset=UTF-8";
            }
            this.commonBuilder.field(new HttpField(HttpFieldKey.CONTENT_TYPE, fieldValue));
            return this;
        }

        public HttpResponseHeaderBuilder body(byte[] body) {
            this.commonBuilder.field(new HttpField(HttpFieldKey.CONTENT_LENGTH, String.valueOf(body.length)));
            return this;
        }

        public HttpResponseHeader build() {
            return new HttpResponseHeader(
                commonBuilder.build(),
                status
            );
        }
    }

    public static HttpResponseHeaderBuilder builder() {
        return new HttpResponseHeaderBuilder();
    }
}

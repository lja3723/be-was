package webserver.http.header;

import webserver.http.ContentType;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.header.HttpHeader.HttpHeaderBuilder;
import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;

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
            this.commonBuilder.field(HttpField.builder()
                .key(HttpFieldKey.CONTENT_TYPE)
                .value(fieldValue)
                .build());
            return this;
        }

        public HttpResponseHeaderBuilder body(byte[] body) {
            this.commonBuilder.field(HttpField.builder()
                .key(HttpFieldKey.CONTENT_LENGTH)
                .value(String.valueOf(body.length))
                .build());
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

    /**
     * {@link HttpResponseHeader}를 HTTP Response Protocol을 준수하는 문자열로 인코딩함
     * @return 인코딩된 HTTP 응답 헤더 문자열
     */
    // TODO: 별도의 클래스로 분리 필요
    public String encode() {
        final String CRLF = "\r\n";
        StringBuilder builder = new StringBuilder();

        builder.append(this.common().version().getValue()).append(" ")
            .append(this.status().getCode()).append(" ")
            .append(this.status().getReasonPhrase()).append(CRLF);

        this.common().fields()
            .forEach(field -> {
                builder.append(field.key().getValue()).append(": ");

                if (field.values() != null) {
                    String fieldValues = field.values().stream()
                        .map(fieldValue -> {
                            StringBuilder fieldValueBuilder = new StringBuilder();
                            fieldValueBuilder.append(fieldValue.value());
                            if (fieldValue.parameters() != null) {
                                fieldValue.parameters().forEach(parameter -> fieldValueBuilder.append("; ")
                                    .append(parameter.key())
                                    .append("=")
                                    .append(parameter.value()));
                            }
                            return fieldValueBuilder.toString();

                        })
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("");
                    builder.append(fieldValues);
                }
                builder.append(CRLF);
            });

        builder.append(CRLF);
        return builder.toString();
    }
}

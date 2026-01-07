package webserver.http.field;

import java.util.ArrayList;
import java.util.List;

/**
 * HTTP request/response Header의 Field를 표현
 * @param key HTTP Header Field의 Key
 * @param values HTTP Header Field의 Value 목록으로, nullable함
 */
public record HttpField(HttpFieldKey key, List<HttpFieldValue> values) {

    public static class HttpFieldBuilder {
        private HttpFieldKey key = null;
        private List<HttpFieldValue> values = null;

        public HttpFieldBuilder key(HttpFieldKey key) {
            this.key = key;
            return this;
        }

        public HttpFieldBuilder key(String key) {
            return this.key(HttpFieldKey.fromString(key));
        }

        public HttpFieldBuilder value(HttpFieldValue value) {
            if (values == null) {
                values = new ArrayList<>();
            }
            this.values.add(value);
            return this;
        }

        public HttpFieldBuilder value(String value) {
            return this.value(HttpFieldValue.builder()
                .value(value.trim())
                .build());
        }

        public HttpField build() {
            return new HttpField(key, values);
        }
    }

    public String getFirstSingleValue() {
        if (values == null || values.isEmpty()) {
            return null;
        }
        return values.get(0).value();
    }

    public static HttpFieldBuilder builder() {
        return new HttpFieldBuilder();
    }
}
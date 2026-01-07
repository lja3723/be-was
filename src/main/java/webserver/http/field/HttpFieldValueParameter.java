package webserver.http.field;

/**
 * <p>HTTP Header Field Value의 Parameter를 표현하는 record</p>
 * 추후 {@link HttpFieldValue}의 parameters로 사용 예정
 */
public record HttpFieldValueParameter(String key, String value) {

    public static class HttpFieldValueParameterBuilder {
        private String key;
        private String value;

        public HttpFieldValueParameterBuilder key(String key) {
            this.key = key;
            return this;
        }

        public HttpFieldValueParameterBuilder value(String value) {
            this.value = value;
            return this;
        }

        public HttpFieldValueParameter build() {
            return new HttpFieldValueParameter(key, value);
        }
    }

    public static HttpFieldValueParameterBuilder builder() {
        return new HttpFieldValueParameterBuilder();
    }
}

package webserver.http.field;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>HTTP Header Field의 Value와 그에 대응하는 Parameter들을 표현</p>
 * 추후 {@link HttpField}의 value를 String이 아닌 List<HttpFieldValue>로 변경할 때 사용 예정
 * @param value HTTP Header Field의 Value
 * @param parameters HTTP Header Field Value의 Parameter 목록, nullable함
 */
public record HttpFieldValue(String value, List<HttpFieldValueParameter> parameters) {

    public static class HttpFieldValueBuilder {
        private String value = null;
        private List<HttpFieldValueParameter> parameters = null;

        public HttpFieldValueBuilder value(String value) {
            this.value = value;
            return this;
        }

        public HttpFieldValueBuilder parameter(HttpFieldValueParameter parameter) {
            if (parameters == null) {
                parameters = new ArrayList<>();
            }
            this.parameters.add(parameter);
            return this;
        }

        public HttpFieldValueBuilder parameter(String key, String value) {
            if (parameters == null) {
                parameters = new ArrayList<>();
            }
            return this.parameter(HttpFieldValueParameter.builder()
                .key(key)
                .value(value)
                .build());
        }

        public HttpFieldValue build() {
            return new HttpFieldValue(value, parameters);
        }
    }

    public static HttpFieldValueBuilder builder() {
        return new HttpFieldValueBuilder();
    }
}

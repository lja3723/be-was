package webserver.util;

import webserver.http.field.HttpField;

/**
 * {@link HttpField}를 HTTP Response Protocol을 준수하는 문자열로 인코딩함
 * @return 인코딩된 HTTP 응답 헤더 문자열
 */
public class HttpFieldEncoder {

    public static String encode(HttpField field) {
        StringBuilder builder = new StringBuilder();
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
        return builder.toString();
    }
}

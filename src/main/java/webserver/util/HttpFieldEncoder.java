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
                    if (fieldValue.parameters() == null) {
                        return fieldValueBuilder.toString();
                    }

                    for (int i = 0; i < fieldValue.parameters().size(); i++) {
                        if (0 < i || !fieldValue.value().isEmpty()) {
                            fieldValueBuilder.append("; ");
                        }
                        var parameter = fieldValue.parameters().get(i);
                        fieldValueBuilder.append(parameter.key());

                        if (parameter.value() != null) {
                            fieldValueBuilder.append("=")
                                .append(parameter.value());
                        }
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

package webserver.http.parser;

import app.exception.BadRequestException;
import java.util.Arrays;
import webserver.http.field.HttpField;
import webserver.http.field.HttpField.HttpFieldBuilder;
import webserver.http.field.HttpFieldKey;
import webserver.http.field.HttpFieldValue;
import webserver.http.field.HttpFieldValue.HttpFieldValueBuilder;
import webserver.http.field.HttpFieldValueParameter;

/**
 * HTTP Header Field의 한 줄을 파싱하는 Parser 구현체
 * <p>한 줄을 파싱하는 규칙은 <a href="https://www.rfc-editor.org/rfc/rfc9110.html#section-5">RFC 9110,
 * Section 5</a>를 참고함</p>
 */
public class HttpFieldParser implements Parser<String, HttpField> {

    /**
     * HTTP Header Field의 한 줄을 파싱하여 {@link HttpField} 객체로 반환한다.
     * @param rawFieldLine 파싱할 HTTP Header Field의 원시 문자열
     * @return 파싱된 {@link HttpField} 객체
     */
    @Override
    public HttpField parse(String rawFieldLine) {
        String[] split = rawFieldLine.split(":", 2);

        if (split .length != 2) {
            throw new BadRequestException("Invalid HTTP Field Line: " + rawFieldLine);
        }

        HttpFieldKey fieldKey = HttpFieldKey.fromString(split[0].trim());

        return switch (fieldKey) {
            // AI를 활용하여 comment 문법이 사용될 수 있는 모든 필드 유형을 알아내었음
            // 해당 필드는 value를 통째로 사용, comment를 처리하도록 별도 메서드로 위임
            // TODO: 향후 Date 필드는 별도의 파싱이 필요할 수 있음
            case USER_AGENT, SERVER, VIA, DATE -> parseCommentableField(fieldKey, split[1]);
            default -> parseUncommentableField(fieldKey, split[1]);
        };
    }

    public HttpField parseCommentableField(HttpFieldKey key, String rawValue) {
        return HttpField.builder()
            .key(key)
            .value(rawValue.trim())
            .build();
    }

    public HttpField parseUncommentableField(HttpFieldKey key, String rawValue) {
        HttpFieldBuilder builder = HttpField.builder().key(key);

        // ",,," 같은 경우도 "", "", "", "" 으로 인식하기 위해 split의 두번째 인자에 -1을 넣음
        String[] HttpFieldValues = rawValue.trim().split(",", -1);
        for (var httpFieldValue : HttpFieldValues) {
            builder.value(parseFieldValue(httpFieldValue.trim()));
        }

        return builder.build();
    }

    public HttpFieldValue parseFieldValue(String rawFieldValue) {
        HttpFieldValueBuilder fieldValueBuilder = HttpFieldValue.builder();
        String[] parts = rawFieldValue.trim().split(";", -1);
        if (parts.length <= 1) {
            return fieldValueBuilder
                .value(parts.length == 0 ? "" : parts[0].trim())
                .build();
        }

        fieldValueBuilder.value(parts[0].trim());
        Arrays.stream(parts)
            .skip(1)
            .map(this::parseFieldValueParameter)
            .forEach(fieldValueBuilder::parameter);

        return fieldValueBuilder.build();
    }

    public HttpFieldValueParameter parseFieldValueParameter(String rawParameter) {
        String[] paramSplit = rawParameter.trim().split("=", 2);
        if (paramSplit.length == 0) {
            throw new BadRequestException("Invalid HTTP Field Value Parameter: " + rawParameter);
        }

        return HttpFieldValueParameter.builder()
            .key(paramSplit[0].trim())
            .value((paramSplit.length == 2) ? paramSplit[1].trim() : "")
            .build();
    }
}

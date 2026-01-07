package webserver.http.parser;

import app.exception.BadRequestException;
import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;

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
            case USER_AGENT, SERVER, VIA -> parseCommentableField(fieldKey, split[1]);
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
        // ','로 split후 ';'로 split하는 알고리즘 구현 예정
        return HttpField.builder()
            .key(key)
            .value(rawValue) // TODO: 추후 key에 대응하는 값을 문자열이 아닌 객체로 저장하도록 변경
            .build();
    }
}

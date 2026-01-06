package webserver.http.parser;

import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;

/**
 * HTTP Header Field의 한 줄을 파싱하는 Parser 구현체
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

//        if (split .length != 2) {
//            throw new BadRequestException("Invalid HTTP Field Line: " + rawFieldLine);
//        }


        // TODO: 괄호 '()'를 모두 치환후, ','로 split후 ';'로 split. 그리고 치환되었던 ()를 원복하는 알고리즘 구현 예정

        return HttpField.builder()
            .key(split[0])
            .value(split[1]) // TODO: 추후 key에 대응하는 값을 문자열이 아닌 객체로 저장하도록 변경
            .build();
    }
}

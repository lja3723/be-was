package webserver.http.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;

/**
 * HTTP Header Field의 한 줄을 파싱하는 Parser 구현체
 */
public class HttpFieldParser implements Parser<HttpField, String> {
    private static final Logger log = LoggerFactory.getLogger(HttpFieldParser.class);

    /**
     * HTTP Header Field의 한 줄을 파싱하여 {@link HttpField} 객체로 반환한다.
     * @param rawFieldLine 파싱할 HTTP Header Field의 원시 문자열
     * @return 파싱된 {@link HttpField} 객체
     */
    @Override
    public HttpField parse(String rawFieldLine) {
        String[] split = rawFieldLine.split(":", 2);

        // TODO: 괄호 '()'를 모두 치환후, ','로 split후 ';'로 split. 그리고 치환되었던 ()를 원복하는 알고리즘 구현 예정

        return new HttpField(
            HttpFieldKey.fromString(split[0].trim()),
            split[1].trim() // TODO: 추후 key에 대응하는 값을 문자열이 아닌 객체로 저장하도록 변경
        );
    }
}

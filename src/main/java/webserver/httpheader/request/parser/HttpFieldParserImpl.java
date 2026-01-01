package webserver.httpheader.request.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httpheader.field.HttpField;
import webserver.httpheader.field.HttpFieldKey;

public class HttpFieldParserImpl implements HttpFieldParser {
    private static final Logger log = LoggerFactory.getLogger(HttpFieldParserImpl.class);

    //  괄호 ()를 모두 치환후, ,로 split후 ;로 split
    // 치환되었던 ()를 원복
    @Override
    public HttpField parse(String rawFieldLine) {
        HttpField result = new HttpField();
        String[] split = rawFieldLine.split(":", 2);
        result.key = HttpFieldKey.fromString(split[0].trim());

//        log.debug(rawFieldLine);

        //TODO: 추후 key에 대응하는 값을 문자열이 아닌 객체로 저장하도록 변경
        result.value = split[1].trim();

        return result;
    }
}

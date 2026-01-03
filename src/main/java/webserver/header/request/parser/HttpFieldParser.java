package webserver.header.request.parser;

import webserver.header.field.HttpField;

/**
 * HTTP Header Field의 한 줄을 파싱하는 Parser 인터페이스
 */
public interface HttpFieldParser {

    HttpField parse(String rawFieldLine);
}

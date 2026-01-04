package webserver.http.parser;

import webserver.http.HttpMethod;
import webserver.http.HttpVersion;
import webserver.http.header.HttpRequestHeaderHead;

/**
 * <p>HTTP Request의 Head 부분을 파싱하는 Parser 인터페이스</p>
 * request의 첫 번째 라인에서 얻을 수 있는 method, path, version 정보를 제공
 *
 */
public interface HttpRequestHeaderHeadParser {

    HttpRequestHeaderHead parse(String rawRequestLine);
}

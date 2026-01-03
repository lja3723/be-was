package webserver.httpheader.request.parser;

import webserver.httpheader.HttpMethod;
import webserver.httpheader.HttpVersion;

/**
 * <p>HTTP Request의 Head 부분을 파싱하는 Parser 인터페이스</p>
 * request의 첫 번째 라인에서 얻을 수 있는 method, path, version 정보를 제공
 *
 */
// TODO: 무상태 클래스로 리팩터링 예정, 그리고 HttpRequestHeadParserFactory 및 HttpRequestHeadParserFactoryImpl 제거
public interface HttpRequestHeadParser {

    HttpMethod getMethod();
    String getPath();
    HttpVersion getVersion();
}

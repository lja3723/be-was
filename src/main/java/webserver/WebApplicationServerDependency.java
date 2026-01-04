package webserver;

import webserver.http.field.HttpField;
import webserver.http.header.HttpRequestHeaderHead;
import webserver.http.parser.Parser;

/**
 * 웹 애플리케이션 서버가 필요로 하는 의존성들을 제공하는 인터페이스
 */
public interface WebApplicationServerDependency {

    /**
     * HTTP Header Field 파서를 반환
     */
    Parser<HttpField, String> getHttpFieldParser();

    /**
     * HTTP Request Head 파서 팩토리를 반환
     * <p>필요 없는 클래스로, 추후 간략화 예정</p>
     */
    Parser<HttpRequestHeaderHead, String> getHttpRequestHeaderHeadParser();

}

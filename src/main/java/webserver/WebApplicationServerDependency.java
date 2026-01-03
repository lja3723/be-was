package webserver;

import webserver.httpheader.request.header.HttpRequestHeaderFactory;
import webserver.httpheader.request.parser.HttpFieldParser;
import webserver.httpheader.request.parser.HttpRequestHeadParserFactory;
import webserver.httpheader.response.header.HttpResponseHeaderFactory;

/**
 * 웹 애플리케이션 서버가 필요로 하는 의존성들을 제공하는 인터페이스
 */
public interface WebApplicationServerDependency {

    /**
     * HTTP Header Field 파서를 반환
     */
    HttpFieldParser getHttpFieldParser();

    /**
     * HTTP Request Head 파서 팩토리를 반환
     * <p>필요 없는 클래스로, 추후 간략화 예정</p>
     */
    HttpRequestHeadParserFactory getHttpRequestHeadParserFactory();

    /**
     * HTTP Request Header 팩토리를 반환
     */
    HttpRequestHeaderFactory getHttpRequestHeaderFactory();

    /**
     * HTTP Response Header 팩토리를 반환
     * <p>필요 없는 클래스로, 추후 간략화 예정</p>
     */
    HttpResponseHeaderFactory getHttpResponseHeaderFactory();
}

package webserver;

import http.field.HttpField;
import http.field.HttpRequestUrl;
import http.header.HttpRequestHeader;
import http.header.HttpRequestHeaderHead;
import http.parser.Parser;
import responsehandler.HttpResponseHandler;
import router.Router;

/**
 * 웹 애플리케이션 서버가 필요로 하는 의존성들을 제공하는 인터페이스
 */
public interface WebApplicationServerDependency {

    /**
     * HTTP Header Field 파서를 반환
     */
    Parser<HttpField, String> getHttpFieldParser();

    /**
     * HTTP Request Head 파서를 반환
     */
    Parser<HttpRequestHeaderHead, String> getHttpRequestHeaderHeadParser();

    /**
     * HTTP Request URL 파서를 반환
     */
    Parser<HttpRequestUrl, String> getHttpRequestUrlParser();

    /**
     * Exception Handler Router를 반환
     */
    Router<Exception, HttpResponseHandler> getExceptionHandlerRouter();

    /**
     * HTTP Request Router를 반환
     */
    Router<HttpRequestHeader, HttpResponseHandler> getHttpRequestRouter();
}

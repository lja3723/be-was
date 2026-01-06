package webserver;

import webserver.http.field.HttpField;
import webserver.http.field.HttpRequestUrl;
import webserver.http.header.HttpRequestHeader;
import webserver.http.header.HttpRequestHeaderHead;
import webserver.http.parser.Parser;
import app.responsehandler.HttpResponseHandler;
import webserver.router.Router;

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

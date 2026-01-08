package dependency;

import webserver.handler.exception.ExceptionHandler;
import webserver.http.HttpRequest;
import webserver.http.field.HttpField;
import webserver.http.uri.HttpRequestUri;
import webserver.http.header.HttpRequestHeaderHead;
import webserver.http.parser.Parser;
import webserver.handler.HttpRequestHandler;
import webserver.router.Router;

/**
 * 웹 애플리케이션 서버가 필요로 하는 의존성들을 제공하는 인터페이스
 */
public interface WebApplicationServerDependency {

    /**
     * HTTP Header Field 파서를 반환
     */
    Parser<String, HttpField> getHttpFieldParser();

    /**
     * HTTP Request Head 파서를 반환
     */
    Parser<String, HttpRequestHeaderHead> getHttpRequestHeaderHeadParser();

    /**
     * HTTP Request URL 파서를 반환
     */
    Parser<String, HttpRequestUri> getHttpRequestUriParser();

    /**
     * Exception Handler Router를 반환
     */
    Router<Throwable, ExceptionHandler> getExceptionHandlerRouter();

    /**
     * HTTP Request Router를 반환
     */
    Router<HttpRequest, HttpRequestHandler> getHttpRequestRouter();

    /**
     * Static Resource Handler를 반환
     */
    HttpRequestHandler getStaticResourceHandler();
}

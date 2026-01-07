package webserver;

import webserver.http.HttpRequest;
import webserver.http.field.HttpField;
import webserver.http.field.HttpRequestUrl;
import webserver.http.header.HttpRequestHeaderHead;
import webserver.http.parser.HttpFieldParser;
import webserver.http.parser.HttpRequestHeaderHeadParser;
import webserver.http.parser.HttpRequestUrlParser;
import webserver.http.parser.Parser;
import app.responsehandler.HttpRequestHandler;
import webserver.router.ExceptionHandlerRouter;
import webserver.router.HttpRequestRouter;
import webserver.router.Router;

/**
 * Web Application Server의 Production Environment에서 사용되는 의존성 구현체
 */
public class WebApplicationServerProductionDependency implements WebApplicationServerDependency {

    private final Parser<String, HttpField> httpFieldParser = new HttpFieldParser();
    private final Parser<String, HttpRequestHeaderHead> httpRequestHeaderHeadParser = new HttpRequestHeaderHeadParser();
    private final Parser<String, HttpRequestUrl> httpRequestUrlParser = new HttpRequestUrlParser();
    private final Router<Throwable, HttpRequestHandler> exceptionHandlerRouter = new ExceptionHandlerRouter();
    private final Router<HttpRequest, HttpRequestHandler> httpRequestRouter = new HttpRequestRouter();

    @Override
    public Parser<String, HttpField> getHttpFieldParser() {
        return httpFieldParser;
    }

    @Override
    public Parser<String, HttpRequestHeaderHead> getHttpRequestHeaderHeadParser() {
        return httpRequestHeaderHeadParser;
    }

    @Override
    public Parser<String, HttpRequestUrl> getHttpRequestUrlParser() {
        return httpRequestUrlParser;
    }

    @Override
    public Router<Throwable, HttpRequestHandler> getExceptionHandlerRouter() {
        return exceptionHandlerRouter;
    }

    @Override
    public Router<HttpRequest, HttpRequestHandler> getHttpRequestRouter() {
        return httpRequestRouter;
    }
}

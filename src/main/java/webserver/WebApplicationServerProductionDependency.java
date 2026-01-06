package webserver;

import webserver.http.HttpRequest;
import webserver.http.field.HttpField;
import webserver.http.field.HttpRequestUrl;
import webserver.http.header.HttpRequestHeaderHead;
import webserver.http.parser.HttpFieldParser;
import webserver.http.parser.HttpRequestHeaderHeadParser;
import webserver.http.parser.HttpRequestUrlParser;
import webserver.http.parser.Parser;
import app.responsehandler.HttpResponseHandler;
import webserver.router.ExceptionHandlerRouter;
import webserver.router.HttpRequestRouter;
import webserver.router.Router;

/**
 * Web Application Server의 Production Environment에서 사용되는 의존성 구현체
 */
public class WebApplicationServerProductionDependency implements WebApplicationServerDependency {

    private final Parser<HttpField, String> httpFieldParser = new HttpFieldParser();
    private final Parser<HttpRequestHeaderHead, String> httpRequestHeaderHeadParser = new HttpRequestHeaderHeadParser();
    private final Parser<HttpRequestUrl, String> httpRequestUrlParser = new HttpRequestUrlParser();
    private final Router<Throwable, HttpResponseHandler> exceptionHandlerRouter = new ExceptionHandlerRouter();
    private final Router<HttpRequest, HttpResponseHandler> httpRequestRouter = new HttpRequestRouter();

    @Override
    public Parser<HttpField, String> getHttpFieldParser() {
        return httpFieldParser;
    }

    @Override
    public Parser<HttpRequestHeaderHead, String> getHttpRequestHeaderHeadParser() {
        return httpRequestHeaderHeadParser;
    }

    @Override
    public Parser<HttpRequestUrl, String> getHttpRequestUrlParser() {
        return httpRequestUrlParser;
    }

    @Override
    public Router<Throwable, HttpResponseHandler> getExceptionHandlerRouter() {
        return exceptionHandlerRouter;
    }

    @Override
    public Router<HttpRequest, HttpResponseHandler> getHttpRequestRouter() {
        return httpRequestRouter;
    }
}

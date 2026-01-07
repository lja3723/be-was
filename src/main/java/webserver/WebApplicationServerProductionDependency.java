package webserver;

import http.field.HttpField;
import http.field.HttpRequestUrl;
import http.header.HttpRequestHeader;
import http.header.HttpRequestHeaderHead;
import http.parser.HttpFieldParser;
import http.parser.HttpRequestHeaderHeadParser;
import http.parser.HttpRequestUrlParser;
import http.parser.Parser;
import responsehandler.HttpResponseHandler;
import router.ExceptionHandlerRouter;
import router.HttpRequestRouter;
import router.Router;

/**
 * Web Application Server의 Production Environment에서 사용되는 의존성 구현체
 */
public class WebApplicationServerProductionDependency implements WebApplicationServerDependency {

    private final Parser<HttpField, String> httpFieldParser = new HttpFieldParser();
    private final Parser<HttpRequestHeaderHead, String> httpRequestHeaderHeadParser = new HttpRequestHeaderHeadParser();
    private final Parser<HttpRequestUrl, String> httpRequestUrlParser = new HttpRequestUrlParser();
    private final Router<Exception, HttpResponseHandler> exceptionHandlerRouter = new ExceptionHandlerRouter();
    private final Router<HttpRequestHeader, HttpResponseHandler> httpRequestRouter = new HttpRequestRouter();

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
    public Router<Exception, HttpResponseHandler> getExceptionHandlerRouter() {
        return exceptionHandlerRouter;
    }

    @Override
    public Router<HttpRequestHeader, HttpResponseHandler> getHttpRequestRouter() {
        return httpRequestRouter;
    }
}

package dependency;

import app.exception.BadRequestException;
import app.exception.InternalServerErrorException;
import app.exception.ResourceNotFoundException;
import app.handler.ApplicationHandler;
import app.handler.TestHandler;
import app.handler.exception.BadRequestHttpRequestHandler;
import app.handler.exception.ResourceNotFoundHttpRequestHandler;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import webserver.handler.StaticResourceHandler;
import webserver.handler.exception.ExceptionHandler;
import webserver.handler.exception.InternalServerErrorHttpRequestHandler;
import webserver.http.HttpEndpoint;
import webserver.http.HttpRequest;
import webserver.http.field.HttpField;
import webserver.http.field.HttpRequestUrl;
import webserver.http.header.HttpRequestHeaderHead;
import webserver.http.parser.HttpFieldParser;
import webserver.http.parser.HttpRequestHeaderHeadParser;
import webserver.http.parser.HttpRequestUrlParser;
import webserver.http.parser.Parser;
import webserver.handler.HttpRequestHandler;
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
    private final Router<Throwable, ExceptionHandler> exceptionHandlerRouter = new ExceptionHandlerRouter(exceptionHandlerMap());
    private final Router<HttpRequest, HttpRequestHandler> httpRequestRouter = new HttpRequestRouter(getApplicationHandlerMap(), getStaticResourceHandler());

    private static final HttpRequestHandler staticResourceHandler = new StaticResourceHandler();

    // Exception 클래스별 HttpResponseHandler 매핑 초기화
    // TODO: 추후 애너테이션 & 리플렉션으로 자동 등록하는 방식으로 변경 고려
    private static Map<Class<? extends Throwable>, ExceptionHandler> exceptionHandlerMap() {
        return Map.of(
            BadRequestException.class, new BadRequestHttpRequestHandler(),
            ResourceNotFoundException.class, new ResourceNotFoundHttpRequestHandler(),
            InternalServerErrorException.class, new InternalServerErrorHttpRequestHandler()
        );
    }

    // ApplicationHandler 매핑 초기화
    // TODO: 추후 애너테이션 & 리플렉션으로 자동 등록하는 방식으로 변경 고려
    private static Map<HttpEndpoint, ApplicationHandler> getApplicationHandlerMap() {
        List<ApplicationHandler> applicationHandlers = List.of(
            new TestHandler()
        );

        return applicationHandlers.stream().collect(
            Collectors.toMap(
                ApplicationHandler::getEndpoint,
                handler -> handler
            )
        );
    }

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
    public Router<Throwable, ExceptionHandler> getExceptionHandlerRouter() {
        return exceptionHandlerRouter;
    }

    @Override
    public Router<HttpRequest, HttpRequestHandler> getHttpRequestRouter() {
        return httpRequestRouter;
    }

    @Override
    public HttpRequestHandler getStaticResourceHandler() {
        return staticResourceHandler;
    }
}

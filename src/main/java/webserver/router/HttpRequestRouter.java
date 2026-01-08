package webserver.router;

import app.handler.ApplicationHandler;
import java.util.Map;
import java.util.Objects;
import webserver.http.HttpEndpoint;
import webserver.http.HttpRequest;
import webserver.handler.HttpRequestHandler;

/**
 * HttpRequestHeader에 따른 HttpResponseHandler를 라우팅하는 Router
 */
public class HttpRequestRouter implements Router<HttpRequest, HttpRequestHandler> {

    private final Map<HttpEndpoint, ApplicationHandler> applicationHandlerMap;
    private final HttpRequestHandler staticResourceHandler;

    public HttpRequestRouter(Map<HttpEndpoint, ApplicationHandler> applicationHandlerMap, HttpRequestHandler staticResourceHandler) {
        this.applicationHandlerMap = applicationHandlerMap;
        this.staticResourceHandler = staticResourceHandler;
    }

    @Override
    public HttpRequestHandler route(HttpRequest httpRequest) {
        ApplicationHandler applicationHandler = applicationHandlerMap.get(
            new HttpEndpoint(httpRequest.header().method(), httpRequest.header().uri().resourcePath())
        );

        return Objects.requireNonNullElse(applicationHandler, staticResourceHandler);
    }
}

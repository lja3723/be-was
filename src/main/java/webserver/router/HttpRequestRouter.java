package webserver.router;

import app.handler.ApplicationHandler;
import java.util.Map;
import java.util.Objects;
import webserver.http.HttpEndpoint;
import webserver.http.HttpRequest;
import webserver.handler.HttpRequestHandler;
import webserver.util.FileExtensionExtractor;

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

        // 확장자가 붙은 요청은 정적 리소스 핸들러로 라우팅
        if (FileExtensionExtractor.get(httpRequest.header().uri().getPath()) != null) {
            return staticResourceHandler;
        }

        // TODO: 추후 Path Variable 지원이 필요할 경우 리팩터링 필요
        ApplicationHandler applicationHandler = applicationHandlerMap.get(
            new HttpEndpoint(httpRequest.header().method(), httpRequest.header().uri().getPath())
        );

        return Objects.requireNonNullElse(applicationHandler, staticResourceHandler);
    }
}

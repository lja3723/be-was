package webserver.router;

import app.exception.HttpMethodNotAllowedException;
import app.handler.ApplicationHandler;
import java.util.Arrays;
import java.util.Map;
import webserver.http.HttpEndpoint;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.handler.HttpRequestHandler;
import webserver.util.FileExtensionExtractor;
import webserver.util.StaticResourceLoader;

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
        String path = httpRequest.header().uri().getPath();
        if (FileExtensionExtractor.get(path) != null) {
            return staticResourceHandler;
        }

        // TODO: 추후 Path Variable 지원이 필요할 경우 리팩터링 필요
        ApplicationHandler applicationHandler = applicationHandlerMap.get(
            new HttpEndpoint(httpRequest.header().method(), path)
        );

        if (applicationHandler != null) {
            return applicationHandler;
        }

        if (StaticResourceLoader.loadResource(path) == null) {
            checkNotAllowedMethod(httpRequest);
        }

        return staticResourceHandler;
    }

    public void checkNotAllowedMethod(HttpRequest httpRequest) {

        boolean hasSupportedMethod = Arrays.stream(HttpMethod.values())
            .filter(method -> method != httpRequest.header().method())
            .anyMatch(method -> applicationHandlerMap.containsKey(
                new HttpEndpoint(method, httpRequest.header().uri().getPath())));

        if (hasSupportedMethod) {
            throw new HttpMethodNotAllowedException("HTTP Method " + httpRequest.header().method() + " is not allowed for " + httpRequest.header().uri().getPath());
        }
    }
}

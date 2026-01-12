package app.handler;

import webserver.handler.HttpRequestHandler;
import webserver.http.HttpEndpoint;
import webserver.http.HttpMethod;

/**
 * 사용자 애플리케이션 레이어의 HTTP Request를 핸들링하는 추상 클래스
 */
public abstract class ApplicationHandler extends HttpRequestHandler {

    protected final HttpEndpoint endpoint;

    public ApplicationHandler(HttpMethod method, String path) {
        this.endpoint = new HttpEndpoint(method, path);
    }

    public final HttpEndpoint getEndpoint() {
        return endpoint;
    }
}

package app.handler;

import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class TestHandler extends ApplicationHandler {

    public TestHandler() {
        super(HttpMethod.GET, "static/test");
    }

    @Override
    public HttpResponse handleResponse(HttpRequest httpRequest) {
        return null;
    }
}

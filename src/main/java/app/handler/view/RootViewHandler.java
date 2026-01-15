package app.handler.view;

import app.business.SecurityChecker;
import java.util.Map;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class RootViewHandler extends ViewHandler {

    private final SecurityChecker securityChecker;

    public RootViewHandler(SecurityChecker securityChecker) {
        super("/");
        this.securityChecker = securityChecker;
    }

    @Override
    protected HttpResponse preHandle(HttpRequest httpRequest) {
        return null;
    }

    @Override
    protected Map<String, Object> getTemplateValues(HttpRequest httpRequest) {

        return super.getTemplateValues(httpRequest);
    }
}

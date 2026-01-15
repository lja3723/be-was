package app.handler.view;

import app.business.SecurityChecker;
import app.handler.response.RedirectResponse;
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
        // 이미 로그인된 상태라면 로그인 된 메인 페이지인 "/main"으로 리다이렉트
        if (securityChecker.isLoggedIn(httpRequest)) {
            return RedirectResponse.to("/main");
        }
        return null;
    }
}

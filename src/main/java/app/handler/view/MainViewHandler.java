package app.handler.view;

import app.business.SecurityChecker;
import app.handler.response.RedirectResponse;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class MainViewHandler extends ViewHandler {

    private final SecurityChecker securityChecker;

    public MainViewHandler(SecurityChecker securityChecker) {
        super("/main");
        this.securityChecker = securityChecker;
    }

    @Override
    protected HttpResponse preHandle(HttpRequest httpRequest) {
        // 로그인이 안된 상태면 루트 페이지로 리다이렉션
        if (!securityChecker.isLoggedIn(httpRequest)) {
            return RedirectResponse.to("/");
        }
        return null;
    }
}

package app.handler.view;

import app.business.SecurityChecker;
import app.handler.response.RedirectResponse;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class LoginViewHandler extends ViewHandler {

    private final SecurityChecker securityChecker;

    public LoginViewHandler(SecurityChecker securityChecker) {
        super("/login");
        this.securityChecker = securityChecker;
    }

    @Override
    protected HttpResponse preHandle(HttpRequest httpRequest) {
        // 이미 로그인된 상태라면 "/"로 리다이렉트
        if (securityChecker.isLoggedIn(httpRequest)) {
            return RedirectResponse.to("/");
        }
        return null;
    }
}

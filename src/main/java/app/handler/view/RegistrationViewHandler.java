package app.handler.view;

import app.business.SecurityChecker;
import app.handler.response.RedirectResponse;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class RegistrationViewHandler extends ViewHandler {

    private final SecurityChecker securityChecker;

    public RegistrationViewHandler(SecurityChecker securityChecker) {
        super("/registration");
        this.securityChecker = securityChecker;
    }

    @Override
    protected HttpResponse preHandle(HttpRequest httpRequest) {
        // 로그인이 된 상태면 메인 페이지로 리다이렉션
        if (securityChecker.isLoggedIn(httpRequest)) {
            return RedirectResponse.to("/");
        }
        return null;
    }
}
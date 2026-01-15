package app.handler.view;

import app.business.SecurityChecker;
import app.handler.response.RedirectResponse;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class MyPageViewHandler extends ViewHandler {

    private final SecurityChecker securityChecker;

    public MyPageViewHandler(SecurityChecker securityChecker) {
        super("/mypage");
        this.securityChecker = securityChecker;
    }

    @Override
    protected HttpResponse preHandle(HttpRequest httpRequest) {
        // 로그인이 안된 상태면 로그인 페이지로 리다이렉션
        if (!securityChecker.isLoggedIn(httpRequest)) {
            return RedirectResponse.to("/login");
        }
        return null;
    }
}
package app.handler.view;

import app.business.SecurityChecker;
import app.handler.response.RedirectResponse;
import java.util.Map;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;
import webserver.util.CookieExtractor;

public class ArticleViewHandler extends ViewHandler {

    private final HttpSession httpSession;
    private final SecurityChecker securityChecker;

    public ArticleViewHandler(HttpSession httpSession, SecurityChecker securityChecker) {
        super("/article");
        this.httpSession = httpSession;
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

    @Override
    protected Map<String, Object> getTemplateValues(HttpRequest httpRequest) {

        String sid = CookieExtractor.getAttributeFrom(httpRequest, "sid");
        String userName = httpSession.getAttribute(sid, "userName");

        return Map.of("userName", userName);
    }
}
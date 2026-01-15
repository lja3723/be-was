package app.handler.view;

import app.business.SecurityChecker;
import app.business.UserBusiness;
import app.exception.UnauthorizedException;
import app.handler.response.RedirectResponse;
import app.model.User;
import java.util.Map;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

public class MainViewHandler extends ViewHandler {

    private final HttpSession httpSession;
    private final SecurityChecker securityChecker;
    private final UserBusiness userBusiness;

    public MainViewHandler(HttpSession httpSession, SecurityChecker securityChecker, UserBusiness userBusiness) {
        super("/main");
        this.httpSession = httpSession;
        this.securityChecker = securityChecker;
        this.userBusiness = userBusiness;
    }

    @Override
    protected HttpResponse preHandle(HttpRequest httpRequest) {
        // 로그인이 안된 상태면 루트 페이지로 리다이렉션
        if (!securityChecker.isLoggedIn(httpRequest)) {
            return RedirectResponse.to("/");
        }
        return null;
    }

    @Override
    protected Map<String, Object> getTemplateValues(HttpRequest httpRequest) {
        String userId = httpSession.getAttribute(securityChecker.getValidSessionId(httpRequest), "userId");

        User user = userBusiness.findById(userId)
            .orElseThrow(() -> new UnauthorizedException("User not found for ID: " + userId));

        return Map.of("username", user.getName());
    }
}

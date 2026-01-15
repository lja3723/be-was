package app.handler;

import app.business.SecurityChecker;
import app.handler.response.RedirectResponse;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;
import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;
import webserver.http.field.HttpFieldValue;

public class LogoutHandler extends ApplicationHandler {

    private final SecurityChecker securityChecker;
    private final HttpSession httpSession;

    public LogoutHandler(SecurityChecker securityChecker, HttpSession httpSession) {
        super(HttpMethod.POST, "/logout");
        this.securityChecker = securityChecker;
        this.httpSession = httpSession;
    }

    @Override
    public HttpResponse handleResponse(HttpRequest httpRequest) {
        String sid = securityChecker.getValidSessionId(httpRequest);
        httpSession.removeSession(sid);

        HttpResponse response = RedirectResponse.to("/");

        // Set-Cookie 헤더 추가하여 세션 쿠키 만료
        response.header().common().fields().add(HttpField.builder()
            .key(HttpFieldKey.SET_COOKIE)
            .value(HttpFieldValue.builder()
                .value("")
                .parameter("sid", "")
                .parameter("Max-Age", "0") // 쿠키를 만료시킨다.
                .parameter("Path", "/")
                .build())
            .build());

        return response;
    }
}

package app.handler;

import app.business.SecurityChecker;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;
import webserver.http.field.HttpFieldValue;
import webserver.http.header.HttpResponseHeader;

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

        return new HttpResponse(
            HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.FOUND)
                .field(HttpField.builder()
                    .key(HttpFieldKey.LOCATION)
                    .value("/")
                    .build())
                .field(HttpField.builder()
                    .key(HttpFieldKey.SET_COOKIE)
                    .value(HttpFieldValue.builder()
                        .value("")
                        .parameter("sid", "")
                        .parameter("Max-Age", "0") // 쿠키를 만료시킨다.
                        .parameter("Path", "/")
                        .build())
                    .build())
                .build()
            , null);
    }
}

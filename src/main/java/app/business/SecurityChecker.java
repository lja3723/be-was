package app.business;

import app.exception.UnauthorizedException;
import webserver.http.HttpRequest;
import webserver.http.HttpSession;
import webserver.util.CookieExtractor;

public class SecurityChecker {

    private final HttpSession httpSession;

    public SecurityChecker(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public String getValidSessionId(HttpRequest httpRequest) {
        String sid = CookieExtractor.getAttributeFrom(httpRequest, "sid");
        if (httpSession.getSession(sid) == null) {
            throw new UnauthorizedException("No valid session.");
        }
        return sid;
    }
}

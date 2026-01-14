package app.handler;

import app.business.LoginResult;
import app.business.UserBusiness;
import app.exception.BadRequestException;
import java.util.List;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.QueryParameter;
import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;
import webserver.http.field.HttpFieldValue;
import webserver.http.header.HttpResponseHeader;
import webserver.util.QueryParameterValidator;

public class LoginHandler extends ApplicationHandler {

    private final HttpSession httpSession;
    private final UserBusiness userBusiness;

    public LoginHandler(HttpSession httpSession, UserBusiness userBusiness) {
        super(HttpMethod.POST, "/login");
        this.httpSession = httpSession;
        this.userBusiness = userBusiness;
    }

    @Override
    public HttpResponse handleResponse(HttpRequest httpRequest) {
        if (httpRequest.body() == null) {
            throw new BadRequestException("Request body is missing.");
        }

        QueryParameter queryParameter = QueryParameterValidator.validate(
            httpRequest.body(),
            List.of("userId", "password")
        );

        LoginResult result = userBusiness.login(
            queryParameter.getValue("userId"),
            queryParameter.getValue("password")
        );

        if (result == LoginResult.SUCCESS) {
            return loginSuccessResponse(queryParameter.getValue("userId"));
        }

        return loginFailureResponse(result);
    }

    public HttpResponse loginSuccessResponse(String userId) {
        String sessionId = httpSession.getNewSession();
        httpSession.setAttribute(sessionId, "userId", userId);

        // 로그인 성공시 "/main"으로 리다이렉트 및 세션 쿠키 설정
        return new HttpResponse(
            HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.FOUND)
                .field(HttpField.builder()
                    .key(HttpFieldKey.LOCATION)
                    .value("/main")
                    .build())
                .field(HttpField.builder()
                    .key(HttpFieldKey.SET_COOKIE)
                    .value(HttpFieldValue.builder()
                        .value("")
                        .parameter("sid", sessionId)
                        .parameter("Path", "/")
                        .build())
                    .build())
                .build()
            , null);
    }

    public HttpResponse loginFailureResponse(LoginResult loginResult) {

        // 로그인 실패 시 다시 로그인 페이지로 리다이렉트
        return new HttpResponse(
            HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.FOUND)
                .field(HttpField.builder()
                    .key(HttpFieldKey.LOCATION)
                    .value("/login?retry_cause=" + loginResult.getValue())
                    .build())
                .build()
            , null);
    }
}

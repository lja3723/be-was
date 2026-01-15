package app.handler;

import app.business.LoginResult;
import app.business.UserBusiness;
import app.exception.BadRequestException;
import app.handler.response.JsonResponse;
import java.util.List;
import java.util.Map;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;
import webserver.http.QueryParameter;
import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;
import webserver.http.field.HttpFieldValue;
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
            String userId = queryParameter.getValue("userId");
            String userName = userBusiness.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found after successful login."))
                .name();

            return loginSuccessResponse(userId, userName);
        }

        return loginFailureResponse(result);
    }

    public HttpResponse loginSuccessResponse(String userId, String userName) {
        // 로그인 성공시 성공 응답 제공 및 세션 쿠키 설정
        String sessionId = httpSession.getNewSession();
        httpSession.setAttribute(sessionId, "userId", userId);
        httpSession.setAttribute(sessionId, "userName", userName);

        HttpResponse response = JsonResponse.ok(Map.of(
            "status", "success"
        ));

        // Set-Cookie 헤더 추가
        response.header().common().fields().add(HttpField.builder()
            .key(HttpFieldKey.SET_COOKIE)
            .value(HttpFieldValue.builder()
                .value("")
                .parameter("sid", sessionId)
                .parameter("Path", "/")
                .build())
            .build());

        return response;
    }

    public HttpResponse loginFailureResponse(LoginResult loginResult) {

        return JsonResponse.unauthorized(Map.of(
            "status", "failure",
            "errorCode", loginResult.getValue()
        ));
    }
}

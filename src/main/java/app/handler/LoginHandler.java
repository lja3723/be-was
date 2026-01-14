package app.handler;

import app.business.UserBusiness;
import app.exception.BadRequestException;
import java.util.List;
import webserver.http.ContentType;
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

        boolean loginSuccess = userBusiness.login(
            queryParameter.getValue("userId"),
            queryParameter.getValue("password")
        );

        if (loginSuccess) {
            return loginSuccessResponse(queryParameter.getValue("userId"));
        }

        return loginFailureResponse();
    }

    public HttpResponse loginSuccessResponse(String userId) {
        String sessionId = httpSession.getNewSession();
        httpSession.setAttribute(sessionId, "userId", userId);

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
                        .parameter("sid", sessionId)
                        .parameter("Path", "/")
                        .build())
                    .build())
                .build()
            , null);
    }

    public HttpResponse loginFailureResponse() {
        // TODO: 정적 HTML 로더 개발 후 alert를 삽입한 현재 페이지로 리다이렉트 되도록 리팩터링
        byte[] body = "<html><body><h1>로그인 실패</h1><p>잘못된 아이디나 비밀번호로 로그인을 시도했습니다.(이 페이지는 추후 리팩터링 예정)</p></body></html>"
            .getBytes();

        return new HttpResponse(
            HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.TEXT_HTML)
                .body(body)
                .build(),
            body);
    }
}

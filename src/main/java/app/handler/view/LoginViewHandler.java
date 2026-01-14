package app.handler.view;

import app.business.LoginResult;
import java.util.Map;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;
import webserver.http.header.HttpResponseHeader;
import webserver.util.CookieExtractor;
import webserver.util.QueryParameterValidator;

public class LoginViewHandler extends ViewHandler {

    private final HttpSession httpSession;

    public LoginViewHandler(HttpSession httpSession) {
        super("/login");
        this.httpSession = httpSession;
    }

    @Override
    protected HttpResponse preHandle(HttpRequest httpRequest) {
        String sid = CookieExtractor.getAttributeFrom(httpRequest, "sid");

        // 이미 로그인된 상태라면 로그인 된 메인 페이지인 "/main"으로 리다이렉트
        if (sid != null && httpSession.getSession(sid) != null) {
            return new HttpResponse(
                HttpResponseHeader.builder()
                    .version(HttpVersion.HTTP_1_1)
                    .status(HttpStatus.FOUND)
                    .field(HttpField.builder()
                        .key(HttpFieldKey.LOCATION)
                        .value("/main")
                        .build())
                    .build()
                , null);
        }
        return null;
    }

    @Override
    protected Map<String, Object> getTemplateValues(HttpRequest httpRequest) {
        String rawQuery = httpRequest.header().uri().getQuery();
        String retryCause = QueryParameterValidator.parseQuery(rawQuery).getValue("retry_cause");

        // 로그인 실패가 아닌 경우 기본 로그인 페이지 반환
        if (retryCause == null) {
            return Map.of();
        }

        // TODO: 추후 Style 지정에서의 중복 제거 필요
        return switch (LoginResult.fromValue(retryCause)) {
            case PASSWORD_MISMATCH -> Map.of("loginError", """
                <div class="error-msg-container" style="display: flex; flex-direction: column; align-items: center; gap: 8px; margin-bottom: 32px; padding: 16px; border-radius: 8px; background-color: #fff0f0; border: 1px solid #ffc1c1;">
                  <p style="color: #e74c3c; font-size: 14px; font-weight: 600; line-height: 1.5; text-align: center;">
                    비밀번호가 일치하지 않습니다.
                  </p>
                  <p style="color: #4b5966; font-size: 13px; font-weight: 400; text-align: center;">
                    아이디와 비밀번호를 다시 확인한 후 시도해주세요.
                  </p>
                </div>
                """);
            case USER_NOT_FOUND -> Map.of("loginError", """
                <div class="error-msg-container" style="display: flex; flex-direction: column; align-items: center; gap: 12px; margin-bottom: 32px; padding: 16px; border-radius: 8px; background-color: #fff0f0; border: 1px solid #ffc1c1;">
                  <p style="color: #e74c3c; font-size: 14px; font-weight: 500; line-height: 1.5; text-align: center;">
                    존재하지 않는 아이디입니다.<br>회원가입하시겠습니까?
                  </p>
                  <a href="/registration" class="btn btn_contained btn_size_s" style="background-color: #e74c3c;">
                    회원 가입
                  </a>
                </div>
                """);
            default -> Map.of();
        };
    }
}

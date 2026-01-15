package app.handler.view;

import java.util.Map;
import webserver.http.HttpRequest;
import webserver.http.HttpSession;
import webserver.util.CookieExtractor;

public class RootViewHandler extends ViewHandler {

    private final HttpSession httpSession;

    public RootViewHandler(HttpSession httpSession) {
        super("/");
        this.httpSession = httpSession;
    }

    @Override
    protected Map<String, Object> getTemplateValues(HttpRequest httpRequest) {

        String sid = CookieExtractor.getAttributeFrom(httpRequest, "sid");
        String userName = httpSession.getAttribute(sid, "userName");

        // 로그인 된 상태에는 로그인된 헤더 블럭 렌더링
        if (userName != null) {
            return Map.of("headerMenuBlock", getLoggedInHeaderBlock(userName));
        }

        // 로그아웃 된 상태에는 로그아웃된 헤더 블럭 렌더링
        return Map.of("headerMenuBlock", getLoggedOutHeaderBlock());
    }

    private String getLoggedInHeaderBlock(String userName) {
        return """
            <li class="header__menu__item">
              <a class="btn btn_ghost btn_size_s" href="/mypage">안녕하세요, %s님!</a>
            </li>
            <li class="header__menu__item">
              <a class="btn btn_contained btn_size_s" href="/article">글쓰기</a>
            </li>
            <li class="header__menu__item">
              <form action="/logout" method="POST" style="display: inline;">
                <button type="submit" id="logout-btn" class="btn btn_ghost btn_size_s">
                  로그아웃
                </button>
              </form>
            </li>
            """.formatted(userName);
    }

    private String getLoggedOutHeaderBlock() {
        return """
            <li class="header__menu__item">
              <a class="btn btn_contained btn_size_s" href="/login">로그인</a>
            </li>
            <li class="header__menu__item">
              <a class="btn btn_ghost btn_size_s" href="/registration">
                회원 가입
              </a>
            </li>
            """;
    }
}

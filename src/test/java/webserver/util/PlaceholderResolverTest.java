package webserver.util;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PlaceholderResolverTest {

    @Test
    void resolve() {
        // given
        byte[] templateBytes = getTemplateBytes();
        Map<String, Object> values = getStringObjectMap();

        // when
        byte[] resultBytes = PlaceholderResolver.resolve(templateBytes, values);
        String result = new String(resultBytes, StandardCharsets.UTF_8);

        // then
        assertTrue(result.contains("<title>페이지의 타이틀입니다.</title>"));
        assertTrue(result.contains("<h1>환영합니다, 홍길동님!</h1>"));
        assertTrue(result.contains("<h2>여기는 치환되지 않을 것입니다.</h2>"));
        assertTrue(result.contains("<h2>장바구니 목록</h2>"));
        assertTrue(result.contains("<li>포테이토칩</li>"));

        assertFalse(result.contains("{{"));
        assertFalse(result.contains("}}"));
    }

    private static byte[] getTemplateBytes() {
        return """
            <!DOCTYPE html>
            <html lang="ko">
            <head>
                <meta charset="UTF-8">
                <title>{{ title }}</title>
            </head>
            <body>
                <header>
                    <h1>환영합니다, {{ user_name }}님!</h1>
                    <h2>여기는 치환되지 않을{{ none_replace_here }} 것입니다.</h2>
                </header>
                <main>
                    {{ main_content }}
                </main>
                <footer>
                    <p>All rights reserved.</p>
                </footer>
            </body>
            </html>
            """.getBytes(StandardCharsets.UTF_8);
    }

    private static Map<String, Object> getStringObjectMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("title", "페이지의 타이틀입니다.");
        values.put("user_name", "홍길동");

        values.put("main_content", """
                <section class="profile">
                    <h2>장바구니 목록</h2>
                    <ul>
                        <li>우유</li>
                        <li>포테이토칩</li>
                    </ul>
                </section>
                """);
        return values;
    }
}
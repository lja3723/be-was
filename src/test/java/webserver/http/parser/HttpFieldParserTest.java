package webserver.http.parser;

import static org.junit.jupiter.api.Assertions.*;

import _support.Pair;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;
import webserver.http.field.HttpFieldValue;

class HttpFieldParserTest {

    private static final Logger log = LoggerFactory.getLogger(HttpFieldParserTest.class);
    private Parser<String, HttpField> parser;

    private static final List<Pair<String, HttpField>> testCases = List.of(

        // Request Fields
        new Pair<>(
            "Host: localhost:8080",
            HttpField.builder()
                .key(HttpFieldKey.HOST)
                .value("localhost:8080")
                .build()
        ),
        new Pair<>(
            "Upgrade-InseCure-RequEsts: 1",
            HttpField.builder()
                .key(HttpFieldKey.UPGRADE_INSECURE_REQUESTS)
                .value("1")
                .build()
        ),
        new Pair<>(
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36",
            HttpField.builder()
                .key(HttpFieldKey.USER_AGENT)
                .value("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .build()
        ),
        new Pair<>(
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8",
            HttpField.builder()
                .key(HttpFieldKey.ACCEPT)
                .value(HttpFieldValue.builder()
                    .value("text/html")
                    .build())
                .value(HttpFieldValue.builder()
                    .value("application/xhtml+xml")
                    .build())
                .value(HttpFieldValue.builder()
                    .value("application/xml")
                    .parameter("q", "0.9")
                    .build())
                .value(HttpFieldValue.builder()
                    .value("image/avif")
                    .build())
                .value(HttpFieldValue.builder()
                    .value("image/webp")
                    .build())
                .value(HttpFieldValue.builder()
                    .value("image/apng")
                    .build())
                .value(HttpFieldValue.builder()
                    .value("*/*")
                    .parameter("q", "0.8")
                    .build())
                .build()
        ),
        new Pair<>(
            "Accept-Encoding: gzip, deflate, br",
            HttpField.builder()
                .key(HttpFieldKey.ACCEPT_ENCODING)
                .value(HttpFieldValue.builder()
                    .value("gzip")
                    .build())
                .value(HttpFieldValue.builder()
                    .value("deflate")
                    .build())
                .value(HttpFieldValue.builder()
                    .value("br")
                    .build())
                .build()
        ),
        new Pair<>(
            "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7",
            HttpField.builder()
                .key(HttpFieldKey.ACCEPT_LANGUAGE)
                .value(HttpFieldValue.builder()
                    .value("ko-KR")
                    .build())
                .value(HttpFieldValue.builder()
                    .value("ko")
                    .parameter("q", "0.9")
                    .build())
                .value(HttpFieldValue.builder()
                    .value("en-US")
                    .parameter("q", "0.8")
                    .build())
                .value(HttpFieldValue.builder()
                    .value("en")
                    .parameter("q", "0.7")
                    .build())
                .build()
        ),
        new Pair<>(
            "Via: 1.1 proxy1.example.com (Squid/5.7), 1.1 cache.local (Nginx reverse proxy)",
            HttpField.builder()
                .key(HttpFieldKey.VIA)
                .value("1.1 proxy1.example.com (Squid/5.7), 1.1 cache.local (Nginx reverse proxy)")
                .build()
        ),

        // Response Fields
        new Pair<>(
            "Date: Tue, 07 Jan 2026 06:30:12 GMT",
            HttpField.builder()
                .key(HttpFieldKey.DATE)
                .value("Tue, 07 Jan 2026 06:30:12 GMT")
                .build()
        ),
        new Pair<>(
            "Server: Apache/2.4.57 (Unix), OpenSSL/3.0.8 (FIPS enabled), Tomcat/9.0.82",
            HttpField.builder()
                .key(HttpFieldKey.SERVER)
                .value("Apache/2.4.57 (Unix), OpenSSL/3.0.8 (FIPS enabled), Tomcat/9.0.82")
                .build()
        ),
        new Pair<>(
            "Content-Type: text/html; charset=UTF-8",
            HttpField.builder()
                .key(HttpFieldKey.CONTENT_TYPE)
                .value(HttpFieldValue.builder()
                    .value("text/html")
                    .parameter("charset", "UTF-8")
                    .build())
                .build()
        ),
        new Pair<>(
            "Content-Length: 1024",
            HttpField.builder()
                .key(HttpFieldKey.CONTENT_LENGTH)
                .value("1024")
                .build()
        ),
        new Pair<>(
            "Connection: keep-alive",
            HttpField.builder()
                .key(HttpFieldKey.CONNECTION)
                .value("keep-alive")
                .build()
        ),
        new Pair<>(
            "Cache-Control: no-cache, no-store, must-revalidate",
            HttpField.builder()
                .key(HttpFieldKey.CACHE_CONTROL)
                .value(HttpFieldValue.builder()
                    .value("no-cache")
                    .build())
                .value(HttpFieldValue.builder()
                    .value("no-store")
                    .build())
                .value(HttpFieldValue.builder()
                    .value("must-revalidate")
                    .build())
                .build()
        ),
        new Pair<>(
            "Expires: 0",
            HttpField.builder()
                .key(HttpFieldKey.EXPIRES)
                .value("0")
                .build()
        ),

        // Cookie Field Test
        new Pair<>(
            "Cookie: sessionId=abc123; userId=789xyz; theme=light",
            HttpField.builder()
                .key(HttpFieldKey.COOKIE)
                .value(HttpFieldValue.builder()
                    .value("")
                    .parameter("sessionId", "abc123")
                    .parameter("userId", "789xyz")
                    .parameter("theme", "light")
                    .build())
                .build()
        ),

        // Unknown Field Test
        new Pair<>(
            "Unknown-Field:,, , ;a=b;c=d;e=f , ",
            HttpField.builder()
                .key(HttpFieldKey.UNKNOWN)
                .value("")
                .value("")
                .value("")
                .value(HttpFieldValue.builder()
                    .value("")
                    .parameter("a", "b")
                    .parameter("c", "d")
                    .parameter("e", "f")
                    .build())
                .value("")
                .build()
        )
    );


    @BeforeEach
    void setUp() {
        parser = new HttpFieldParser();
    }

    @Test
    void parse() {

        for (var testCase: testCases) {
            // given
            log.info("Test case: {}", testCase.first());
            String rawFieldLine = testCase.first();
            HttpField expected = testCase.second();

            // when
            HttpField actual = parser.parse(rawFieldLine);

            // then
            assertEquals(expected.key(), actual.key());
            assertFieldValuesSize(getListSize(expected.values()), actual);

            for (int i = 0; i < getListSize(expected.values()); i++) {
                HttpFieldValue expectedValue = expected.values().get(i);
                HttpFieldValue actualValue = actual.values().get(i);

                assertEquals(expectedValue.value(), actualValue.value());
                assertFieldValueParametersSize(getListSize(expectedValue.parameters()), actualValue);

                if (expectedValue.parameters() != null) {
                    for (int j = 0; j < getListSize(expectedValue.parameters()); j++) {
                        var expectedParam = expectedValue.parameters().get(j);
                        var actualParam = actualValue.parameters().get(j);

                        assertEquals(expectedParam.key(), actualParam.key());
                        assertEquals(expectedParam.value(), actualParam.value());
                    }
                }
            }
        }
    }

    private <T> int getListSize(List<T> list) {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    private void assertFieldValuesSize(int expectedSize, HttpField field) {
        assertNotNull(field.values());
        assertEquals(expectedSize, field.values().size());
    }

    private void assertFieldValueParametersSize(int expectedSize, HttpFieldValue fieldValue) {
        if (expectedSize == 0) {
            assertNull(fieldValue.parameters());
            return;
        }

        assertNotNull(fieldValue.parameters());
        assertEquals(expectedSize, fieldValue.parameters().size());
    }
}
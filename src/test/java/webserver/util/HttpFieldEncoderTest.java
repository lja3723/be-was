package webserver.util;

import static org.junit.jupiter.api.Assertions.*;

import _support.Pair;
import java.util.List;
import org.junit.jupiter.api.Test;
import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;
import webserver.http.field.HttpFieldValue;

class HttpFieldEncoderTest {

    private final List<Pair<HttpField, String>> testCase = List.of(
        new Pair<>(
        HttpField.builder()
            .key(HttpFieldKey.CONTENT_TYPE)
            .value(
                HttpFieldValue.builder()
                    .value("text/html")
                    .parameter("charset", "UTF-8")
                    .build()
            )
            .build(),
            "Content-Type: text/html; charset=UTF-8"),
        new Pair<>(
            HttpField.builder()
                .key(HttpFieldKey.SET_COOKIE)
                .value(HttpFieldValue.builder()
                    .value("")
                    .parameter("sessionId", "abc123")
                    .parameter("someFlag", "false")
                    .parameter("Path", "/")
                    .parameter("HttpOnly", null)
                    .build())
                .build(),
            "Set-Cookie: sessionId=abc123; someFlag=false; Path=/; HttpOnly"),
        new Pair<>(
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
                .build(),
        "Accept: text/html, application/xhtml+xml, application/xml; q=0.9, image/avif, image/webp, image/apng, */*; q=0.8"
    ));

    @Test
    void encode() {

        // when & then
        for (Pair<HttpField, String> pair : testCase) {
            String encoded = HttpFieldEncoder.encode(pair.first());
            assertEquals(pair.second(), encoded);
        }
    }
}
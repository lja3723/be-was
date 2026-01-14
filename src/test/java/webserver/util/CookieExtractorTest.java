package webserver.util;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpVersion;
import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;
import webserver.http.field.HttpFieldValue;
import webserver.http.header.HttpRequestHeader;

class CookieExtractorTest {

    private static final Logger log = LoggerFactory.getLogger(CookieExtractorTest.class);

    private final HttpRequest httpRequest = new HttpRequest(
        HttpRequestHeader.builder()
            .uri(URI.create("/"))
            .method(HttpMethod.GET)
            .version(HttpVersion.HTTP_1_1)
            .field(HttpField.builder().key(HttpFieldKey.COOKIE)
                .value(HttpFieldValue.builder()
                    .value("")
                    .parameter("sid", "12345")
                    .parameter("theme", "dark")
                    .build())
                .build())
            .build(),
        null
    );

    @Test
    void getAttributeFrom() {
        log.debug("httpRequest: {}", httpRequest);
        log.debug("Cookie Field: {}", HttpFieldEncoder.encode(httpRequest.header().common().fields().get(0)));

        String sid = CookieExtractor.getAttributeFrom(httpRequest, "sid");
        String theme = CookieExtractor.getAttributeFrom(httpRequest, "theme");
        String nonExistent = CookieExtractor.getAttributeFrom(httpRequest, "nonExistent");

        assertEquals("12345", sid);
        assertEquals("dark", theme);
        assertNull(nonExistent);
    }
}
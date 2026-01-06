package http.parser;

import static org.junit.jupiter.api.Assertions.*;

import http.HttpMethod;
import http.HttpVersion;
import http.header.HttpRequestHeaderHead;
import org.junit.jupiter.api.Test;

class HttpRequestHeaderHeadParserTest {

    @Test
    void parse() {
        // given
        Parser<HttpRequestHeaderHead, String> parser = new HttpRequestHeaderHeadParser();
        String rawRequestLine = "GET /index.html HTTP/1.1";

        // when
        HttpRequestHeaderHead result = parser.parse(rawRequestLine);

        // then
        assertEquals(HttpMethod.GET, result.method());
        assertEquals("/index.html", result.path());
        assertEquals(HttpVersion.HTTP_1_1, result.version());
    }
}
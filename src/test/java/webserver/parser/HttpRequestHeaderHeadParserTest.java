package webserver.parser;

import static org.junit.jupiter.api.Assertions.*;

import webserver.http.HttpMethod;
import webserver.http.HttpVersion;
import webserver.http.header.HttpRequestHeaderHead;
import org.junit.jupiter.api.Test;
import webserver.http.parser.HttpRequestHeaderHeadParser;
import webserver.http.parser.Parser;

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
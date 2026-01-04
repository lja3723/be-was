package webserver.http.request.parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import webserver.http.field.HttpField;
import webserver.http.parser.HttpFieldParser;
import webserver.http.parser.Parser;

class HttpFieldParserTest {

    @Test
    void parse() {
        // given
        Parser<HttpField, String> parser = new HttpFieldParser();
        String rawFieldLine = "Content-Type: text/html; charset=UTF-8";

        // when
        HttpField result = parser.parse(rawFieldLine);

        // then
        assertEquals("Content-Type", result.key().getValue());
        assertEquals("text/html; charset=UTF-8", result.value());
    }
}
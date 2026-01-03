package webserver.header.request.parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import webserver.header.parser.HttpFieldParser;
import webserver.header.parser.HttpFieldParserImpl;

class HttpFieldParserImplTest {

    @Test
    void parse() {
        // given
        HttpFieldParser parser = new HttpFieldParserImpl();
        String rawFieldLine = "Content-Type: text/html; charset=UTF-8";

        // when
        var result = parser.parse(rawFieldLine);

        // then
        assertEquals("Content-Type", result.key().getValue());
        assertEquals("text/html; charset=UTF-8", result.value());
    }
}
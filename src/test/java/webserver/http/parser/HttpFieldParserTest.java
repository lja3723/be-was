package webserver.http.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;
import webserver.http.field.HttpFieldValue;
import webserver.http.field.HttpFieldValueParameter;

class HttpFieldParserTest {

    private Parser<String, HttpField> parser;

    @BeforeEach
    void setUp() {
        parser = new HttpFieldParser();
    }

    @Test
    void parse() {
        // given
        String rawFieldLine = "Content-Type: text/html; charset=UTF-8";

        // when
        HttpField result = parser.parse(rawFieldLine);

        // then
        assertEquals(HttpFieldKey.CONTENT_TYPE, result.key());

        assertNotNull(result.values());
        assertEquals(1, result.values().size());

        HttpFieldValue fieldLine = result.values().get(0);
        assertEquals("text/html", fieldLine.value());

        List<HttpFieldValueParameter> parameters = fieldLine.parameters();
        assertNotNull(parameters);
        assertEquals(1, parameters.size());

        HttpFieldValueParameter parameter = parameters.get(0);
        assertEquals("charset", parameter.key());
        assertEquals("UTF-8", parameter.value());
    }
}
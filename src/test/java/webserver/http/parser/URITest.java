package webserver.http.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;

class URITest {

    @Test
    void fullUriTest() throws Exception {
        URI uri = new URI("http://localhost:8080/sample/test?id=helloworld&pwd=1234?3992#section1");

        assertEquals("http", uri.getScheme());
        assertEquals("localhost", uri.getHost());
        assertEquals(8080, uri.getPort());
        assertEquals("/sample/test", uri.getPath());
        assertEquals("id=helloworld&pwd=1234?3992", uri.getQuery());
        assertEquals("section1", uri.getFragment());
    }

    @Test
    void simpleUriTest() throws Exception {
        String[] testCases = {
            "/", "/sample/test", "/sample/test/", "/sample//test/"
        };

        for (var testCase: testCases) {
            URI uri = new URI(testCase);
            assertNull(uri.getScheme());
            assertNull(uri.getHost());
            assertEquals(-1, uri.getPort());
            assertEquals(testCase, uri.getPath());
            assertNull(uri.getQuery());
            assertNull(uri.getFragment());
        }
    }

    @Test
    void notSingleFragmentTest() {
        assertThrows(URISyntaxException.class, () -> new URI("/sample/test#section1#section2"));
    }

    @Test
    void percentEncodedUriTest() throws Exception {
        URI uri = new URI("/sample/%ED%95%9C%EA%B8%80/test?name=%ED%95%9C%EA%B8%80");

        assertEquals("/sample/한글/test", uri.getPath());
        assertEquals("name=한글", uri.getQuery());
    }

    @Test
    void onlyQueryTest() throws Exception {
        URI uri = new URI("?id=%ED%95%9C%EA%B8%80&pwd=1234");

        assertEquals("", uri.getPath());
        assertEquals("id=한글&pwd=1234", uri.getQuery());
    }
}
package webserver.util;

import static org.junit.jupiter.api.Assertions.*;

import webserver.http.ContentType;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.field.HttpRequestUrl;
import webserver.http.header.HttpRequestHeader;
import webserver.http.header.HttpResponseHeader;
import webserver.http.parser.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import _support.mock_objects.MockDataOutputStream;
import _support.mock_objects.MockOutputStream;
import _support.mock_objects.webserver.http.parser.MockHttpRequestUrlParser;

class OutputStreamHttpResponseWriterTest {

    private String body;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    private MockDataOutputStream mockDos;

    @BeforeEach
    void setUp() {
        Parser<HttpRequestUrl, String> mockParser = new MockHttpRequestUrlParser();
        httpRequest = new HttpRequest(
            HttpRequestHeader.builder(mockParser)
                .method(HttpMethod.GET)
                .version(HttpVersion.HTTP_1_1)
                .url("/")
                .build(),
            "");

        this.body = "<h1>hello</h1>";

        this.httpResponse = new HttpResponse(
            HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.OK)
                .contentType(ContentType.TEXT_HTML)
                .body(body.getBytes())
                .build(),
            body.getBytes());

        this.mockDos = new MockDataOutputStream(new MockOutputStream());
    }

    @Test
    void flush() {
        // given & when
        OutputStreamHttpResponseWriter.flush(
            mockDos,
            httpRequest,
            httpResponse);

        // then
        // 메서드 호출 횟수는 각각 1번이어야 함
        assertEquals(1, mockDos.getWrite3ArgCallCount());
        assertEquals(1, mockDos.getFlushCallCount());

        int headerLength = this.httpResponse.header().encode().length();
        int bodyLength = this.body.length();

        // 총 작성된 바이트 수는 헤더 길이 + 바디 길이와 같아야 함
        assertEquals(headerLength + bodyLength, mockDos.getWrittenBytes());
    }
}


package webserver.util;

import static org.junit.jupiter.api.Assertions.*;

import webserver.http.ContentType;
import webserver.http.HttpMethod;
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
import _support.mock_objects.http.parser.MockHttpRequestUrlParser;

class ResponseOutputStreamWriterTest {

    private String body;
    private HttpResponseHeader responseHeader;
    private MockDataOutputStream mockDos;

    // Test 대상 객체
    private ResponseOutputStreamWriter responseOutputStreamWriter;

    @BeforeEach
    void setUp() {
        Parser<HttpRequestUrl, String> mockParser = new MockHttpRequestUrlParser();
        HttpRequestHeader httpRequestHeader = HttpRequestHeader.builder(mockParser)
            .method(HttpMethod.GET)
            .version(HttpVersion.HTTP_1_1)
            .url("/")
            .build();

        this.body = "<h1>hello</h1>";

        this.responseHeader = HttpResponseHeader.builder()
            .version(HttpVersion.HTTP_1_1)
            .status(HttpStatus.OK)
            .contentType(ContentType.TEXT_HTML)
            .body(body.getBytes())
            .build();

        this.mockDos = new MockDataOutputStream(new MockOutputStream());

        this.responseOutputStreamWriter = new ResponseOutputStreamWriter(
            mockDos,
            httpRequestHeader,
            responseHeader,
            body.getBytes()
        );
    }

    @Test
    void flushResponse() {
        // given & when
        this.responseOutputStreamWriter.flushResponse();

        // then
        // 메서드 호출 횟수는 각각 1번이어야 함
        assertEquals(1, mockDos.getWrite3ArgCallCount());
        assertEquals(1, mockDos.getFlushCallCount());

        int headerLength = this.responseHeader.encode().length();
        int bodyLength = this.body.length();

        // 총 작성된 바이트 수는 헤더 길이 + 바디 길이와 같아야 함
        assertEquals(headerLength + bodyLength, mockDos.getWrittenBytes());

    }
}


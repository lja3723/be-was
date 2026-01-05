package webserver.handler.response.util;

import static org.junit.jupiter.api.Assertions.*;

import http.ContentType;
import http.HttpMethod;
import http.HttpStatus;
import http.HttpVersion;
import http.field.HttpRequestUrl;
import http.header.HttpRequestHeader;
import http.header.HttpResponseHeader;
import http.parser.Parser;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import responsehandler.util.ResponseOutputStreamWriter;

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

/**
 * 메서드 호출 횟수 및 작성된 바이트 수를 추적하는 Mock DataOutputStream
 */
class MockDataOutputStream extends DataOutputStream {

    private int flushCallCount;
    private int write3ArgCallCount;

    public MockDataOutputStream(OutputStream out) {
        super(out);
        this.flushCallCount = 0;
        this.write3ArgCallCount = 0;
    }

    @Override
    public void write(int b) throws IOException {
        super.write(b);
    }

    @Override
    public synchronized void write(byte[] b, int off, int len) throws IOException {
        super.write(b, off, len);
        write3ArgCallCount++;
    }

    @Override
    public void flush() {
        flushCallCount++;
    }

    public int getWrittenBytes() {
        // 부모 클래스의 written 필드에 접근하여 작성된 바이트 수 반환
        return super.written;
    }

    public int getFlushCallCount() {
        return flushCallCount;
    }

    public int getWrite3ArgCallCount() {
        return write3ArgCallCount;
    }
}

/**
 * MockDataOutputStream에 전달할 단순 OutputStream 구현체
 */
class MockOutputStream extends OutputStream {

    @Override
    public void write(int b) throws IOException {
    }
}

/**
 * HttpRequestUrl을 단순히 rawData로 초기화하는 Mock Parser 구현체
 */
class MockHttpRequestUrlParser implements Parser<HttpRequestUrl, String> {

    @Override
    public HttpRequestUrl parse(String rawData) {
        return new HttpRequestUrl(rawData, rawData, ContentType.TEXT_HTML);
    }
}
package webserver.util;

import webserver.http.header.HttpRequestHeader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import app.exception.InternalServerErrorException;
import webserver.http.header.HttpResponseHeader;

/**
 * HTTP 응답을 작성하고 출력 스트림에 플러시하는 유틸리티 클래스
 * <p>Socket을 통해 열려 있는 {@link OutputStream}으로 {@link HttpResponseHeader} 및
 * byte[] body 배열을 직렬화 후 전달한 후 flush를 수행하여 HTTP Response를 클라이언트에 전달함</p>
 */
public class ResponseOutputStreamWriter {

    private final OutputStream outputStream;
    private final DataOutputStream dataOutputStream;
    private final HttpRequestHeader httpRequestHeader;
    private final HttpResponseHeader responseHeader;
    private final byte[] body;

    public ResponseOutputStreamWriter(OutputStream outputStream, HttpRequestHeader httpRequestHeader, HttpResponseHeader responseHeader, byte[] body) {
        this.outputStream = outputStream;
        this.dataOutputStream = new DataOutputStream(this.outputStream);
        this.httpRequestHeader = httpRequestHeader;
        this.responseHeader = responseHeader;
        this.body = body;
    }

    /**
     * 인코딩된 HTTP Response Header와 body를 OutputStream에 작성하고 flush
     * @throws InternalServerErrorException 응답 작성 또는 플러시에 실패한 경우 발생
     */
    public void flushResponse() throws InternalServerErrorException {
        try {
            dataOutputStream.writeBytes(responseHeader.encode());
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            throw new InternalServerErrorException(outputStream, httpRequestHeader, "Failed to flush response", e);
        }
    }
}

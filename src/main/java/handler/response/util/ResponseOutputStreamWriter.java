package handler.response.util;

import java.io.DataOutputStream;
import java.io.OutputStream;
import exception.InternalServerErrorException;
import http.header.HttpResponseHeader;

/**
 * HTTP 응답을 작성하고 출력 스트림에 플러시하는 유틸리티 클래스
 * <p>Socket을 통해 열려 있는 {@link OutputStream}으로 {@link HttpResponseHeader} 및
 * byte[] body 배열을 직렬화 후 전달한 후 flush를 수행하여 HTTP Response를 클라이언트에 전달함</p>
 */
public class ResponseOutputStreamWriter {

    private final DataOutputStream dataOutputStream;
    private final HttpResponseHeader responseHeader;
    private final byte[] body;

    public ResponseOutputStreamWriter(OutputStream outputStream, HttpResponseHeader responseHeader, byte[] body) {
        this.dataOutputStream = new DataOutputStream(outputStream);
        this.responseHeader = responseHeader;
        this.body = body;
    }

    /**
     * {@link HttpResponseHeader}를 HTTP Response Protocol을 준수하는 문자열로 인코딩함
     * @return 인코딩된 HTTP 응답 헤더 문자열
     */
    public String encodeHeader() {
        final String CRLF = "\r\n";
        StringBuilder builder = new StringBuilder();

        builder.append(responseHeader.common().version().getValue()).append(" ")
            .append(responseHeader.status().getCode()).append(" ")
            .append(responseHeader.status().getReasonPhrase()).append(CRLF);
        for (var field : responseHeader.common().fields()) {
            builder.append(field.key().getValue()).append(": ")
                .append(field.value()).append(CRLF);
        }
        builder.append(CRLF);
        return builder.toString();
    }

    /**
     * 인코딩된 HTTP Response Header와 body를 OutputStream에 작성하고 flush
     * @throws InternalServerErrorException 응답 작성 또는 플러시에 실패한 경우 발생
     */
    public void flushResponse() throws InternalServerErrorException {
        try {
            dataOutputStream.writeBytes(encodeHeader());
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to flush response", e);
        }
    }
}

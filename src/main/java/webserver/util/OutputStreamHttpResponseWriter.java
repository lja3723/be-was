package webserver.util;

import webserver.http.HttpResponse;
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
public class OutputStreamHttpResponseWriter {

    /**
     * 인코딩된 HTTP Response Header와 body를 OutputStream에 작성하고 flush
     * @throws InternalServerErrorException 응답 작성 또는 플러시에 실패한 경우 발생
     */
     public static void flush(OutputStream outputStream, HttpResponse httpResponse)
            throws InternalServerErrorException {
        try {
            DataOutputStream dos = new DataOutputStream(outputStream);
            dos.writeBytes(HttpResponseHeaderEncoder.encode(httpResponse.header()));
            dos.write(httpResponse.body(), 0, httpResponse.body().length);
            dos.flush();
        } catch (IOException e) {
            throw new InternalServerErrorException("Failed to flush response", e);
        }
    }
}

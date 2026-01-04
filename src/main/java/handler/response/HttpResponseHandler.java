package handler.response;

import java.io.OutputStream;
import http.header.HttpRequestHeader;

/**
 * HTTP Protocol을 준수하는 HTTP Response를 Output stream으로 전송할 수 있다.
 * <p>구현체는 클라이언트의 HTTP request 정보를 담은 {@link HttpRequestHeader}을 기반으로
 * 적절한 적절한 HTTP Response를 생성 후 Socket의 Output으로 전송 가능해야 함</p>
 */
//TODO: 이름을 HttpResponseHandler로 변경할 필요가 있음
public abstract class HttpResponseHandler {

    protected final HttpRequestHeader httpRequestHeader;
    protected final OutputStream outputStream;

    /**
     * 클라이언트의 HTTP request 정보, 클라이언트와 연결된 Socket의 OutputStream을 주입받는다.
     * @param httpRequestHeader
     * @param outputStream
     */
    public HttpResponseHandler(HttpRequestHeader httpRequestHeader, OutputStream outputStream) {
        this.httpRequestHeader = httpRequestHeader;
        this.outputStream = outputStream;
    }

    public abstract void handleResponse();
}

package webserver.handler.response;

import java.io.OutputStream;
import webserver.httpheader.request.header.HttpRequestHeader;
import webserver.httpheader.response.header.HttpResponseHeaderFactory;

/**
 * HTTP Protocol을 준수하는 HTTP Response를 Output stream으로 전송할 수 있다.
 * <p>구현체는 클라이언트의 HTTP request 정보를 담은 {@link HttpRequestHeader}을 기반으로
 * 적절한 적절한 HTTP Response를 생성 후 Socket의 Output으로 전송 가능해야 함</p>
 */
//TODO: 이름을 HttpResponseHandler로 변경할 필요가 있음
public interface ResponseHandler {

    /**
     * {@code HttpResponseHeader}를 생성하는 Factory의 구현체를 주입받는다.
     * @param httpResponseHeaderFactory {@code HttpResponseHeader}를 생성하는 Factory의 구현체
     */
    void setHttpResponseHeaderFactory (HttpResponseHeaderFactory httpResponseHeaderFactory);

    /**
     * 클라이언트의 HTTP request 정보의 참조를 전달받는다.
     * @param httpRequestHeader
     */
    void setHttpRequestHeader(HttpRequestHeader httpRequestHeader);

    /**
     * 클라이언트와 연결된 Socket의 OutputStream을 주입받는다.
     * @param outputStream
     */
    void setOutputStream(OutputStream outputStream);
    void handleResponse();
}

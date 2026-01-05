package handler.response;

import handler.response.util.ResponseOutputStreamWriter;
import http.ContentType;
import http.header.HttpResponseHeader;
import java.io.OutputStream;
import http.header.HttpRequestHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP Protocol을 준수하는 HTTP Response를 Output stream으로 전송할 수 있다.
 * <p>구현체는 클라이언트의 HTTP request 정보를 담은 {@link HttpRequestHeader}을 기반으로
 * 적절한 적절한 HTTP Response를 생성 후 Socket의 Output으로 전송 가능해야 함</p>
 */
public abstract class HttpResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponseHandler.class);

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

    /**
     * RequestHeader 기반 HTTP Response를 생성 후 OutputStream으로 전송
     * <p>자식 클래스는 해당 메서드를 상속할 수 없으며, {@link #createResponseHeader(ContentType, byte[])} 를 구현하여
     * HTTP Response Header를 생성해야 함</p>
     */
    public final void handleResponse() {
        logger.debug("request url: {}", httpRequestHeader.url());

        // HTTP Response의 Header 생성
        byte[] body = getBody(httpRequestHeader);
        HttpResponseHeader responseHeader = createResponseHeader(
            httpRequestHeader.url().contentType(),
            body);

        // HTTP Response를 OutputStream으로 전송
        ResponseOutputStreamWriter responseWriter = new ResponseOutputStreamWriter(
            outputStream,
            httpRequestHeader,
            responseHeader,
            body);

        responseWriter.flushResponse();
        // response200Header(dos, body.length);
        // responseBody(dos, body);
    }

    /**
     * HTTP Response Body를 생성하는 추상 메서드
     * <p>자식 클래스는 해당 메서드를 구현하여 적절한 HTTP Response Body를 생성해야 함</p>
     * @param httpRequestHeader 클라이언트의 HTTP Request Header
     * @return
     */
    public abstract byte[] getBody(HttpRequestHeader httpRequestHeader);

    /**
     * HTTP Response Header를 생성하는 추상 메서드
     * <p>자식 클래스는 해당 메서드를 구현하여 적절한 HTTP Response Header를 생성해야 함</p>
     * @param bodyContentType Response Body의 Content-Type
     * @param body Response Body의 byte 배열
     * @return 생성된 HTTP Response Header
     */
    public abstract HttpResponseHeader createResponseHeader(ContentType bodyContentType, byte[] body);

    // TODO: 추후 리팩터링 끝나면 제거 예정

    //    // Unused
    //    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
    //        try {
    //            dos.writeBytes("HTTP/1.1 200 OK \r\n");
    //            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
    //            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
    //            dos.writeBytes("\r\n");
    //        } catch (IOException e) {
    //            logger.error(e.getMessage());
    //            e.printStackTrace();
    //        }
    //    }
    //
    //    // Unused
    //    private void responseBody(DataOutputStream dos, byte[] body) {
    //        try {
    //            dos.write(body, 0, body.length);
    //            dos.flush();
    //        } catch (IOException e) {
    //            logger.error(e.getMessage());
    //            e.printStackTrace();
    //        }
    //    }
}

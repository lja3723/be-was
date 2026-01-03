package webserver.handler;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.ResourceNotFoundException;
import webserver.handler.response.ResponseHandler;
import webserver.handler.response.SuccessResponseHandler;
import webserver.header.request.header.HttpRequestHeader;
import webserver.header.request.header.HttpRequestHeaderDecoder;
import webserver.header.request.parser.HttpFieldParser;
import webserver.header.request.parser.HttpRequestHeadParserFactory;
import webserver.header.response.header.HttpResponseHeaderFactory;

/**
 * {@link Socket}을 통해 연결된 클라이언트와의 통신을 핸들링한다.
 *
 * <p>{@link Socket}으로부터 클라이언트와의 {@link InputStream}과 {@link OutputStream}을 획득한 후
 * 서버가 클라이언트의 HTTP Request에 대한 적절한 Response를 생성하는 거시적인 동작을 담당한다.</p>
 */
public class ClientRequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientRequestHandler.class);

    private final Socket connection;
    private final HttpRequestHeaderDecoder httpRequestHeaderDecoder;
    private final HttpFieldParser httpFieldParser;
    private final HttpRequestHeadParserFactory httpRequestHeadParserFactory;
    private final HttpResponseHeaderFactory httpResponseHeaderFactory;

    /**
     * 서버의 Response 생성을 위해 필요한 의존성을 주입받는다.
     * @param connectionSocket 서버와 클라이언트간에 연결된 통신 회선
     * @param httpRequestHeaderDecoder HTTP Request header factory의 구현체
     * @param httpFieldParser HTTP header field parser의 구현체
     * @param httpRequestHeadParserFactory HTTP header field parser factory의 구현체
     * @param httpResponseHeaderFactory HTTP response header factory의 구현체
     */
    public ClientRequestHandler(Socket connectionSocket,
                          HttpRequestHeaderDecoder httpRequestHeaderDecoder,
                          HttpFieldParser httpFieldParser,
                          HttpRequestHeadParserFactory httpRequestHeadParserFactory,
                          HttpResponseHeaderFactory httpResponseHeaderFactory) {
        this.connection = connectionSocket;
        this.httpRequestHeaderDecoder = httpRequestHeaderDecoder;
        this.httpFieldParser = httpFieldParser;
        this.httpRequestHeadParserFactory = httpRequestHeadParserFactory;
        this.httpResponseHeaderFactory = httpResponseHeaderFactory;
    }

    /**
     * ExecutorService에 의해 호출되어 클라이언트와의 HTTP 통신을 처리
     */
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequestHeader httpRequestHeader = httpRequestHeaderDecoder.create(
                in, httpRequestHeadParserFactory, httpFieldParser);

            logHttpRequestHeader(httpRequestHeader);
            handleResponse(new SuccessResponseHandler(), httpRequestHeader, out);

        } catch (ResourceNotFoundException e) {
            logger.error("404 Not Found: {}", e.getMessage());
            //TODO: 404 응답 처리
        }
        catch (Exception e) {
            logger.error("500 Internal Server Error: {}", e.getMessage());
            e.printStackTrace();
            //TODO: 500 응답 처리
        }
    }

    /**
     * 파싱된 HTTP request header 내용을 logging
     * @param header
     */
    private void logHttpRequestHeader(HttpRequestHeader header) {
        logger.debug("----- HTTP Request Header -----");
        logger.debug("HTTP Method: {}, Path: {}, HTTP Version: {}", header.method(), header.path(), header.common().version());
        header.common().fields().forEach(field ->
            logger.debug("Key -- {} / Value -- {}", field.key(), field.value())
        );
    }

    /**
     * HTTP request header를 기반으로 OutputStream에 HttpResponse를 전송하는 작업을 {@link ResponseHandler}에게 위임
     * @param responseHandler
     * @param httpRequestHeader
     * @param out
     */
    private void handleResponse(ResponseHandler responseHandler, HttpRequestHeader httpRequestHeader, OutputStream out) {
        responseHandler.setHttpResponseHeaderFactory(httpResponseHeaderFactory);
        responseHandler.setHttpRequestHeader(httpRequestHeader);
        responseHandler.setOutputStream(out);
        responseHandler.handleResponse();
    }

}

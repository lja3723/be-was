package webserver;

import http.field.HttpRequestUrl;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import responsehandler.SuccessHttpResponseHandler;
import http.field.HttpField;
import http.header.HttpRequestHeader;
import http.header.HttpRequestHeaderHead;
import http.parser.Parser;

/**
 * {@link Socket}을 통해 연결된 클라이언트와의 통신을 핸들링한다.
 *
 * <p>{@link Socket}으로부터 클라이언트와의 {@link InputStream}과 {@link OutputStream}을 획득한 후
 * 서버가 클라이언트의 HTTP Request에 대한 적절한 Response를 생성하는 거시적인 동작을 담당한다.</p>
 * <p>클래스 이름은 AI에게 추천 받았습니다.</p>
 */
public class HttpRequestProcessor implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestProcessor.class);

    private final Socket connection;
    private final Parser<HttpField, String> httpFieldParser;
    private final Parser<HttpRequestHeaderHead, String> httpRequestHeaderHeadParser;
    private final Parser<HttpRequestUrl, String> httpRequestUrlParser;

    /**
     * 서버의 Response 생성을 위해 필요한 의존성을 주입받는다.
     * @param connection 클라이언트와의 소켓 연결
     * @param httpFieldParser HTTP header field parser의 구현체
     * @param httpRequestHeaderHeadParser HTTP header head field parser 구현체
     * @param httpRequestUrlParser HTTP request URL parser 구현체
     */
    public HttpRequestProcessor(Socket connection,
                                Parser<HttpField, String> httpFieldParser,
                                Parser<HttpRequestHeaderHead, String> httpRequestHeaderHeadParser,
                                Parser<HttpRequestUrl, String> httpRequestUrlParser) {
        this.connection = connection;
        this.httpFieldParser = httpFieldParser;
        this.httpRequestHeaderHeadParser = httpRequestHeaderHeadParser;
        this.httpRequestUrlParser = httpRequestUrlParser;
    }

    /**
     * ExecutorService에 의해 호출되어, 클라이언트와의 HTTP 통신을 처리
     */
    @Override
    public void run() {
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            handleIOStreams(in, out);

        } catch (IOException e) {
            // InputStream 또는 OutputStream 획득 실패인 경우를 잡아냄
            // 로깅 메시지는 AI 도움을 받음
            logger.error(
                "Failed to initialize I/O streams for client connection. " +
                    "Client IP: {}, Client Port: {}, Reason: {}",
                connection.getInetAddress(),
                connection.getPort(),
                e.getMessage(),
                e
            );
        }
    }

    /**
     * InputStream으로부터 HTTP request를 파싱하고, OutputStream에 HTTP response를 작성
     * @param in 클라이언트로부터의 InputStream
     * @param out 클라이언트로의 OutputStream
     */
    public void handleIOStreams(InputStream in, OutputStream out) {
        try {
            // InputStream으로부터 HTTP request header 파싱
            HttpRequestHeader httpRequestHeader = HttpRequestHeader.decodeInputStream(
                in,
                httpRequestHeaderHeadParser,
                httpFieldParser,
                httpRequestUrlParser);
            logHttpRequestHeader(httpRequestHeader);
            routeHttpRequest(httpRequestHeader, out);

        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * HTTP request를 적절한 핸들러로 라우팅
     */
    // TODO: http request 내용에 따른 다양한 success response handler 라우팅 로직 추가 필요
    public void routeHttpRequest(HttpRequestHeader httpRequestHeader, OutputStream out) {

        // HTTP request header를 기반으로 OutputStream에 HttpResponse를 전송하는 작업을 위임함
        // 현재는 모든 요청에 대해 200 응답의 SuccessHttpResponseHandler를 사용하도록 구현되어 있음
        new SuccessHttpResponseHandler().handleResponse(httpRequestHeader, out);
    }

    public void handleException(Exception e) {
        logger.error("error occur: {}, {}", e.getMessage(), e.getCause(), e);
        // TODO: 서버의 Exception을 확장성 있게 잡아내어 핸들러로 넘겨주는 방식으로 수정 필요, 예외에 따른 적절한 HTTP 응답 처리 로직 구현 필요
    }

    /**
     * 파싱된 HTTP request header 내용을 logging
     * @param header
     */
    private void logHttpRequestHeader(HttpRequestHeader header) {
        logger.debug("----- HTTP Request Header -----");
        logger.debug("HTTP Method: {}, Path: {}, HTTP Version: {}", header.method(), header.url(),
            header.common().version());
        header.common().fields().forEach(field ->
            logger.debug("Key -- {} / Value -- {}", field.key(), field.value())
        );
    }
}

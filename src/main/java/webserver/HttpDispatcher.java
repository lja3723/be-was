package webserver;

import dependency.WebApplicationServerDependency;
import webserver.handler.exception.ExceptionHandler;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.util.HttpFieldEncoder;
import webserver.util.InputStreamHttpRequestDecoder;
import webserver.util.OutputStreamHttpResponseWriter;

/**
 * {@link Socket}을 통해 연결된 클라이언트와의 통신을 핸들링한다.
 *
 * <p>{@link Socket}으로부터 클라이언트와의 {@link InputStream}과 {@link OutputStream}을 획득한 후
 * 서버가 클라이언트의 HTTP Request에 대한 적절한 Response를 생성하는 거시적인 동작을 담당한다.</p>
 */
public class HttpDispatcher implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HttpDispatcher.class);

    private final Socket connection;
    private final WebApplicationServerDependency dependency;

    /**
     * 서버의 Response 생성을 위해 필요한 의존성을 주입받는다.
     * @param connection 클라이언트와의 소켓 연결
     * @param dependency 웹 애플리케이션 서버의 의존성 제공자
     */
    public HttpDispatcher(Socket connection, WebApplicationServerDependency dependency) {
        this.connection = connection;
        this.dependency = dependency;
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
        // InputStream으로부터 HTTP request 파싱
        HttpRequest httpRequest = InputStreamHttpRequestDecoder.decode(
            in,
            dependency.getHttpRequestHeaderHeadParser(),
            dependency.getHttpFieldParser());

        logHttpRequestHeader(httpRequest);

        try {
            HttpResponse httpResponse = dependency.getHttpRequestRouter().route(httpRequest)
                .handleResponse(httpRequest);
            OutputStreamHttpResponseWriter.flush(out, httpResponse);

        } catch (Throwable e) {
            HttpResponse httpResponse = invokeHandler(dependency.getExceptionHandlerRouter().route(e), httpRequest, e);
            OutputStreamHttpResponseWriter.flush(out, httpResponse);
        }
    }

    /**
     * ExceptionHandler를 호출하여 HttpResponse 생성
     * @param handler ExceptionHandler 객체
     * @param request HttpRequest 객체
     * @param exception 처리할 예외 객체
     * @param <E> 처리할 예외 타입
     * @return 생성된 HttpResponse 객체
     */
    @SuppressWarnings("unchecked")
    private <E extends Throwable> HttpResponse invokeHandler(
        ExceptionHandler<E> handler, HttpRequest request, Throwable exception) {
        // 내부적으로 캡슐화하여 타입 캐스팅 후 실행
        return handler.handleException(request, (E)exception);
    }

    /**
     * 파싱된 HTTP request 내용을 logging
     * @param httpRequest 파생할 객체
     */
    private void logHttpRequestHeader(HttpRequest httpRequest) {
        logger.debug(">>>>>>>> HTTP Request Debugging >>>>>>>>");
        logger.debug("HTTP Method: {}, Path: {}, HTTP Version: {}", httpRequest.header().method(), httpRequest.header().uri(),
            httpRequest.header().common().version());
        httpRequest.header().common().fields().forEach(field ->
            logger.debug("<Field> {}", HttpFieldEncoder.encode(field))
        );
        logger.debug("<body> {}", httpRequest.body());
        logger.debug("<<<<<<<< HTTP Request End <<<<<<<<\n");
    }
}

package webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ServerSocket을 생성하고 클라이언트의 요청을 처리하는 Web Application Server 클래스
 */
public class WebApplicationServer {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServer.class);

    private final int port;
    private final WebApplicationServerDependency dependency;
    private final ExecutorService executor;

    /**
     * 생성자
     * @param port 서버가 바인딩될 포트 번호
     * @param threadPoolSize Thread Pool의 크기
     * @param dependency 서버가 필요로 하는 의존성들을 제공하는 객체
     */
    public WebApplicationServer(int port, int threadPoolSize, WebApplicationServerDependency dependency) {
        this.port = port;
        this.dependency = dependency;
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    /**
     * 서버를 시작하고 클라이언트의 연결을 수락
     */
    public void startServer() {
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                    connection.getInetAddress(),
                    connection.getPort());

                // Thread 생성 대신 작업 제출
                executor.execute(new HttpRequestProcessor(
                    connection,
                    dependency.getHttpFieldParser(),
                    dependency.getHttpRequestHeaderHeadParser(),
                    dependency.getHttpRequestUrlParser()));
            }
        } catch (IOException e) {
            // ServerSocket 생성 또는 accept 실패시 잡아냄
            // 로깅 메시지는 AI 도움을 받음
            logger.error(
                "Fatal error in Web Application Server. " +
                    "Failed to bind or accept connections on port {}. Reason: {}",
                port,
                e.getMessage(),
                e // 마지막 파라미터로 exception 객체 전달시 logger가 stack trace를 출력하게 됨
            );
        }
        finally {
            executor.shutdown();
        }
    }
}
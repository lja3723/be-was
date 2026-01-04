package webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import handler.ClientRequestHandler;

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

    public void run() {

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                // Thread 생성 대신 작업 제출
                executor.execute(new ClientRequestHandler(
                    connection,
                    dependency.getHttpFieldParser(),
                    dependency.getHttpRequestHeaderHeadParser(),
                    dependency.getHttpRequestUrlParser()));
            }
        } catch (IOException e) {
            logger.error("Error in Web Application Server: ", e);
        }
        finally {
            executor.shutdown();
        }
    }
}
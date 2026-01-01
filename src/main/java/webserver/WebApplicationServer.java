package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.HttpClientRequestHandler;

public class WebApplicationServer {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServer.class);

    private final int port;
    private final WebApplicationServerDependency dependency;
    private final ExecutorService executor;

    public WebApplicationServer(int port, int threadPoolSize, WebApplicationServerDependency dependency) {
        this.port = port;
        this.dependency = dependency;
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void run() throws Exception {

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                // Thread 생성 대신 작업 제출
                executor.execute(new HttpClientRequestHandler(
                    connection,
                    dependency.getHttpRequestHeaderFactory(),
                    dependency.getHttpFieldParser(),
                    dependency.getHttpRequestHeadParserFactory()));
            }
        } finally {
            executor.shutdown();
        }
    }
}
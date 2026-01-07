import webserver.WebApplicationServer;
import webserver.WebApplicationServerProductionDependency;

/**
 * Main Application Entry 클래스
 */
public class MainApplication {

    /**
     * 기본 포트 번호 및 스레드 풀 크기
     */
    public static final int DEFAULT_PORT = 8080;
    public static final int DEFAULT_THREAD_POOL_SIZE = 50;

    public static void main(String[] args) {
        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        WebApplicationServer webApplicationServer = new WebApplicationServer(
            port, DEFAULT_THREAD_POOL_SIZE, new WebApplicationServerProductionDependency());

        webApplicationServer.startServer();
    }
}

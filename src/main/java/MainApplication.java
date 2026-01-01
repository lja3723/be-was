import webserver.WebApplicationServer;
import webserver.WebApplicationServerProductionDependency;

public class MainApplication {

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

        WebApplicationServer webApplicationServer = new WebApplicationServer(port, DEFAULT_THREAD_POOL_SIZE, new WebApplicationServerProductionDependency());
        try {
            webApplicationServer.run();
        } catch (Exception e) {
            //TODO: 강력한 로깅 및 예외 처리 로직 구현 필요
            e.printStackTrace();
        }
    }
}

package app.responsehandler;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

/**
 * HTTP Request 기반으로 HTTP Response를 생성한다.
 */
public abstract class HttpRequestHandler {

    /**
     * HttpRequest를 기반으로 HttpResponse를 생성함
     * @param httpRequest 클라이언트의 HTTP Request
     * @return 생성된 HTTP Response
     */
    public abstract HttpResponse handleResponse(HttpRequest httpRequest);

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

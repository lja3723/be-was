package webserver.router;

import webserver.http.HttpRequest;
import app.responsehandler.HttpResponseHandler;
import app.responsehandler.SuccessHttpResponseHandler;

/**
 * HttpRequestHeader에 따른 HttpResponseHandler를 라우팅하는 Router
 */
public class HttpRequestRouter implements Router<HttpRequest, HttpResponseHandler> {

    @Override
    public HttpResponseHandler route(HttpRequest httpRequest) {

        // TODO: 핸들러 로직 구현
        // HTTP request header를 기반으로 OutputStream에 HttpResponse를 전송하는 작업을 위임함
        // 현재는 모든 요청에 대해 200 응답의 SuccessHttpResponseHandler를 사용하도록 구현되어 있음
        return new SuccessHttpResponseHandler();
    }
}

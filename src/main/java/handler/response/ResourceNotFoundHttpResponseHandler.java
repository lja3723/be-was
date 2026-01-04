package handler.response;

import java.io.OutputStream;
import http.header.HttpRequestHeader;

/**
 * HTTP Status가 404, 리소스가 없는 경우의 HTTP Response를 핸들링하는 ResponseHandler
 */
public class ResourceNotFoundHttpResponseHandler extends SuccessHttpResponseHandler {

    public ResourceNotFoundHttpResponseHandler(HttpRequestHeader httpRequestHeader, OutputStream outputStream) {
        super(httpRequestHeader, outputStream);
    }

    @Override
    public void handleResponse() {
        //TODO: 핸들러 로직 구현
    }
}

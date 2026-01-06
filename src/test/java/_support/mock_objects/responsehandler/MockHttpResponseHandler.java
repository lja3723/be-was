package _support.mock_objects.responsehandler;

import webserver.http.ContentType;
import webserver.http.header.HttpRequestHeader;
import webserver.http.header.HttpResponseHeader;
import java.io.OutputStream;
import app.responsehandler.HttpResponseHandler;

public class MockHttpResponseHandler extends HttpResponseHandler {

    @Override
    public byte[] getBody(HttpRequestHeader httpRequestHeader, OutputStream outputStream) {
        return new byte[0];
    }

    @Override
    public HttpResponseHeader createResponseHeader(ContentType bodyContentType, byte[] body) {
        return null;
    }
}

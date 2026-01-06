package test_support.mock_objects.responsehandler;

import http.ContentType;
import http.header.HttpRequestHeader;
import http.header.HttpResponseHeader;
import java.io.OutputStream;
import responsehandler.HttpResponseHandler;

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

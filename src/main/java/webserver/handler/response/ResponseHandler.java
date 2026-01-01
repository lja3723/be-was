package webserver.handler.response;

import java.io.OutputStream;
import webserver.httpheader.request.header.HttpRequestHeader;
import webserver.httpheader.response.header.HttpResponseHeaderFactory;

public interface ResponseHandler {

    void setHttpResponseHeaderFactory (HttpResponseHeaderFactory httpResponseHeaderFactory);
    void setHttpRequestHeader(HttpRequestHeader httpRequestHeader);
    void setOutputStream(OutputStream outputStream);
    void handleResponse();
}

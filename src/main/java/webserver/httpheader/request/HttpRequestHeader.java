package webserver.httpheader.request;

import webserver.httpheader.HttpHeader;
import webserver.httpheader.HttpMethod;

public class HttpRequestHeader extends HttpHeader {

    public HttpMethod method;
    public String path;
}

package webserver.httpheader.response.header;

import webserver.httpheader.HttpHeader;
import webserver.httpheader.response.HttpStatus;

//TODO: HttpResponseHeader 구현하기
public class HttpResponseHeader extends HttpHeader {

    public HttpStatus status;

    public byte[] toBytes() {
        return null;
    }
}

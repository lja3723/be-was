package webserver.httpheader.response.header;

import webserver.httpheader.HttpHeaderFactory;

//TODO: HttpResponseHeaderFactory 구현하기
public interface HttpResponseHeaderFactory extends HttpHeaderFactory {

    HttpResponseHeaderBuilder builder();
}


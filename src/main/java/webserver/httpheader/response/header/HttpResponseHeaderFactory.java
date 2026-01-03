package webserver.httpheader.response.header;

import webserver.httpheader.HttpHeaderFactory;

// TODO: 불필요 클래스, HttpResponseHeader의 static method로 대체 가능. 추후 제거 및 간략화 필요
public interface HttpResponseHeaderFactory extends HttpHeaderFactory {

    HttpResponseHeaderBuilder builder();
}


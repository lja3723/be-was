package webserver.http;

import webserver.http.header.HttpResponseHeader;

/**
 * HTTP Response를 표현하는 Data Class
 */
public record HttpResponse(HttpResponseHeader header, byte[] body) {

}

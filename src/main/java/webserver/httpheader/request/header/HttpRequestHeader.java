package webserver.httpheader.request.header;

import webserver.httpheader.HttpHeader;
import webserver.httpheader.HttpMethod;

/**
 * HTTP Request Header를 표현하는 Data Class
 */
public record HttpRequestHeader(HttpHeader common, HttpMethod method, String path) {

}

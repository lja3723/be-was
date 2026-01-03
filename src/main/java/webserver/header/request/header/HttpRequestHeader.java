package webserver.header.request.header;

import webserver.header.HttpHeader;
import webserver.header.HttpMethod;

/**
 * HTTP Request Header를 표현하는 Data Class
 */
public record HttpRequestHeader(HttpHeader common, HttpMethod method, String path) {

}

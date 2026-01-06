package webserver.http;

import webserver.http.header.HttpRequestHeader;

/**
 * HTTP Request를 표현하는 Data Class
 */
public record HttpRequest(HttpRequestHeader header, String body) {

}

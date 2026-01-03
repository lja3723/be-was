package webserver.httpheader.response.header;

import webserver.httpheader.HttpHeader;
import webserver.httpheader.response.HttpStatus;

/**
 * HTTP Response Header를 표현하는 Data Class
 */
// TODO: Builder를 static inner class로 변경 고려
public record HttpResponseHeader(HttpHeader common, HttpStatus status) {

}

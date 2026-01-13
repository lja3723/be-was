package webserver.handler.exception;

import app.exception.HttpMethodNotAllowedException;
import webserver.http.ContentType;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.header.HttpResponseHeader;

public class HttpMethodNotAllowedExceptionHandler extends ExceptionHandler<HttpMethodNotAllowedException> {

    @Override
    public HttpResponse handleException(HttpRequest httpRequest, HttpMethodNotAllowedException e) {

        byte[] body = ("<html><body><h1>405 Method Not Allowed</h1><p>" +
            e.getMessage() + "</p></body></html>")
                .getBytes();

        return new HttpResponse(
            HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .contentType(ContentType.TEXT_HTML)
                .body(body)
                .build(),
            body);
    }
}

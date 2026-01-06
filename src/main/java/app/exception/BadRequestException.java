package app.exception;

import webserver.http.HttpRequest;

public class BadRequestException extends RuntimeException {

    private final HttpRequest httpRequest;

    public BadRequestException(HttpRequest httpRequest, String message, Throwable cause) {
        super(message, cause);
        this.httpRequest = httpRequest;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }
}
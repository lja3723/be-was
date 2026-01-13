package webserver.handler.exception;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

/**
 * 예외 상황에 대한 HTTP Response를 핸들링하는 추상 클래스
 */
public abstract class ExceptionHandler<T extends Throwable> {

    public abstract HttpResponse handleException(HttpRequest request, T exception);
}

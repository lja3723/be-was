package webserver.handler;

import app.exception.InternalServerErrorException;
import app.exception.ResourceNotFoundException;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.field.HttpRequestUrl;
import java.io.IOException;
import java.io.InputStream;
import webserver.http.HttpVersion;
import webserver.http.HttpStatus;
import webserver.http.header.HttpResponseHeader;

/**
 * HTTP Status가 200번대인 HTTP Response를 핸들링하는 ResponseHandler
 */
public class StaticResourceHandler extends HttpRequestHandler {

    @Override
    public HttpResponse handleResponse(HttpRequest httpRequest) {

        HttpRequestUrl httpRequestUrl = httpRequest.header().url();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(httpRequestUrl.resourcePath())) {
            // 요청에 맞는 리소스가 존재하지 않음
            if (is == null) {
                throw new ResourceNotFoundException("Resource not found: " + httpRequestUrl);
            }
            byte[] body = is.readAllBytes();

            return new HttpResponse(
                HttpResponseHeader.builder()
                    .version(HttpVersion.HTTP_1_1)
                    .status(HttpStatus.OK)
                    .contentType(httpRequest.header().url().contentType())
                    .body(body)
                    .build(),
                body);

        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage(), e);
        }
    }
}

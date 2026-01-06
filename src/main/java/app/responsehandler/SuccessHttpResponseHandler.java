package app.responsehandler;

import app.exception.InternalServerErrorException;
import app.exception.ResourceNotFoundException;
import webserver.http.ContentType;
import webserver.http.field.HttpRequestUrl;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import webserver.http.HttpVersion;
import webserver.http.header.HttpRequestHeader;
import webserver.http.HttpStatus;
import webserver.http.header.HttpResponseHeader;

/**
 * HTTP Status가 200번대인 HTTP Response를 핸들링하는 ResponseHandler
 */
public class SuccessHttpResponseHandler extends HttpResponseHandler {

    @Override
    public byte[] getBody(HttpRequestHeader httpRequestHeader, OutputStream outputStream) {
        HttpRequestUrl httpRequestUrl = httpRequestHeader.url();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(httpRequestUrl.resourcePath())) {
            // 요청에 맞는 리소스가 존재하지 않음
            if (is == null) {
                throw new ResourceNotFoundException(outputStream, httpRequestHeader, "Resource not found: " + httpRequestUrl);
            }
            return is.readAllBytes();

        } catch (IOException e) {
            throw new InternalServerErrorException(outputStream, httpRequestHeader, e.getMessage(), e);
        }
    }

    @Override
    public HttpResponseHeader createResponseHeader(ContentType bodyContentType, byte[] body) {
        return HttpResponseHeader.builder()
            .version(HttpVersion.HTTP_1_1)
            .status(HttpStatus.OK)
            .contentType(bodyContentType)
            .body(body)
            .build();
    }
}

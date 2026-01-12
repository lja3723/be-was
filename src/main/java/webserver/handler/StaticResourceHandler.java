package webserver.handler;

import app.exception.InternalServerErrorException;
import app.exception.ResourceNotFoundException;
import webserver.http.ContentType;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import java.io.IOException;
import java.io.InputStream;
import webserver.http.HttpVersion;
import webserver.http.HttpStatus;
import webserver.http.header.HttpResponseHeader;
import webserver.util.FileExtensionExtractor;

/**
 * HTTP Status가 200번대인 static resource HTTP request를 핸들링하는 RequestHandler
 */
public class StaticResourceHandler extends HttpRequestHandler {

    @Override
    public HttpResponse handleResponse(HttpRequest httpRequest) {

        String path = "static" + httpRequest.header().uri().getPath();
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        if (FileExtensionExtractor.get(path) == null) {
            path += "/index.html";
        }
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            // 요청에 맞는 리소스가 존재하지 않음
            if (is == null) {
                throw new ResourceNotFoundException("Resource not found: " + path);
            }
            byte[] body = is.readAllBytes();

            return new HttpResponse(
                HttpResponseHeader.builder()
                    .version(HttpVersion.HTTP_1_1)
                    .status(HttpStatus.OK)
                    .contentType(ContentType.fromFileExtension(
                        FileExtensionExtractor.get(path)))
                    .body(body)
                    .build(),
                body);

        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage(), e);
        }
    }
}

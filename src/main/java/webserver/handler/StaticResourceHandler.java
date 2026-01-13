package webserver.handler;

import app.exception.ResourceNotFoundException;
import webserver.http.ContentType;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpVersion;
import webserver.http.HttpStatus;
import webserver.http.header.HttpResponseHeader;
import webserver.util.FileExtensionExtractor;
import webserver.util.StaticResourceLoader;

/**
 * HTTP Status가 200번대인 static resource HTTP request를 핸들링하는 RequestHandler
 */
public class StaticResourceHandler extends HttpRequestHandler {

    @Override
    public HttpResponse handleResponse(HttpRequest httpRequest) {
        String path = httpRequest.header().uri().getPath();
        byte[] body = StaticResourceLoader.loadResource(path);

        if (body == null) {
            throw new ResourceNotFoundException("Resource not found: " + path);
        }

        return new HttpResponse(
            HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.OK)
                .contentType(ContentType.fromFileExtension(
                    FileExtensionExtractor.get(StaticResourceLoader.getStaticResourcePath(path))))
                .body(body)
                .build(),
            body);
    }
}

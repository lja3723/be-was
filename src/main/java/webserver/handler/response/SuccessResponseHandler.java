package webserver.handler.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.InternalServerErrorException;
import webserver.exception.ResourceNotFoundException;
import webserver.handler.response.util.HttpResponseWriter;
import webserver.handler.response.util.ResourcePath;
import webserver.httpheader.HttpVersion;
import webserver.httpheader.request.header.HttpRequestHeader;
import webserver.httpheader.response.HttpStatus;
import webserver.httpheader.response.header.HttpResponseHeader;
import webserver.httpheader.response.header.HttpResponseHeaderFactory;

public class SuccessResponseHandler implements ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(SuccessResponseHandler.class);

    protected HttpResponseHeaderFactory httpResponseHeaderFactory;
    protected HttpRequestHeader httpRequestHeader;
    protected OutputStream outputStream;

    @Override
    public void setHttpResponseHeaderFactory(HttpResponseHeaderFactory httpResponseHeaderFactory) {
        this.httpResponseHeaderFactory = httpResponseHeaderFactory;
    }

    @Override
    public void setHttpRequestHeader(HttpRequestHeader httpRequestHeader) {
        this.httpRequestHeader = httpRequestHeader;
    }

    @Override
    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void handleResponse() {
        //TODO: 응답 리팩터링
        //TODO: 정적 파일 응답 처리

        ResourcePath resourcePath = new ResourcePath(httpRequestHeader.path);
        logger.debug("request path: {}, resource Path: {}", httpRequestHeader.path, resourcePath);

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath.getResourcePath()))  {
            if (is == null) {
                throw new ResourceNotFoundException("Resource not found: " + resourcePath);
            }

            byte[] body = is.readAllBytes();
            HttpResponseHeader responseHeader = httpResponseHeaderFactory.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.OK)
                .contentType(resourcePath.getContentType())
                .body(body)
                .build();

            //TODO: 로딩이 잘 안되는 것 디버깅으로 찾아내기
            HttpResponseWriter responseWriter = new HttpResponseWriter(outputStream, responseHeader, body);
            responseWriter.flushResponse();

            // response200Header(dos, body.length);
            // responseBody(dos, body);
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage(), e);
        }
    }

    //TODO: 응답 리팩터링
    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

}

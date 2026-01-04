package webserver.handler.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.InternalServerError;
import webserver.exception.ResourceNotFoundException;
import webserver.httpheader.HttpVersion;
import webserver.httpheader.request.header.HttpRequestHeader;
import webserver.httpheader.response.ContentType;
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

        String resourcePath = getResourcePath(httpRequestHeader.path);
        logger.debug("request path: {}, resource Path: {}", httpRequestHeader.path, resourcePath);

        InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new ResourceNotFoundException("Resource not found: " + resourcePath);
        }

        try {
            byte[] body = is.readAllBytes();

            HttpResponseHeader responseHeader = httpResponseHeaderFactory.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.OK)
                .contentType(getContentType(resourcePath))
                .body(body)
                .build();

            //TODO: 로딩이 잘 안되는 것 디버깅으로 찾아내기
            flushResponse(outputStream, responseHeader, body);

            // response200Header(dos, body.length);
            // responseBody(dos, body);
        } catch (IOException e) {
            throw new InternalServerError(e.getMessage(), e);
        }
    }

    protected ContentType getContentType(String resourcePath) {
        String[] roots = resourcePath.split("/");
        String[] parts = roots[roots.length - 1].split("\\.");
        String extension = parts[parts.length - 1].toLowerCase();
        return ContentType.fromFileExtension(extension);
    }

    protected String getResourcePath(String requestPath) {
        if (requestPath.equals("/")) {
            requestPath = "/index.html";
        }
        return "static" + requestPath;
    }

    protected void flushResponse(OutputStream os, HttpResponseHeader responseHeader, byte[] body) {
        DataOutputStream dos = new DataOutputStream(os);
        logger.debug("Response Header: {}", responseHeader.toString());
        logger.debug("Response Body: {}", body);

        try {
            dos.writeBytes(responseHeader.toString());
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            throw new InternalServerError(e.getMessage(), e);
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

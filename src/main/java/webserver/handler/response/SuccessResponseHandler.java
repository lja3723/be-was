package webserver.handler.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.InternalServerErrorException;
import webserver.exception.ResourceNotFoundException;
import webserver.handler.util.ResponseOutputStreamWriter;
import webserver.handler.util.ResourcePath;
import webserver.http.HttpVersion;
import webserver.http.header.HttpRequestHeader;
import webserver.http.HttpStatus;
import webserver.http.header.HttpResponseHeader;

/**
 * HTTP Status가 200번대인 HTTP Response를 핸들링하는 ResponseHandler
 */
public class SuccessResponseHandler implements ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(SuccessResponseHandler.class);

    protected HttpRequestHeader httpRequestHeader;
    protected OutputStream outputStream;

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

        // Http request의 path를 기반으로 resource path 연역
        ResourcePath resourcePath = new ResourcePath(httpRequestHeader.path());
        logger.debug("request path: {}, resource Path: {}", httpRequestHeader.path(), resourcePath);

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath.getResourcePath()))  {
            // 요청에 맞는 리소스가 존재하지 않음
            if (is == null) {
                throw new ResourceNotFoundException("Resource not found: " + resourcePath);
            }

            // HTTP Response의 Header 생성
            byte[] body = is.readAllBytes();
            HttpResponseHeader responseHeader = HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.OK)
                .contentType(resourcePath.getContentType())
                .body(body)
                .build();

            // HTTP Response를 OutputStream으로 전송
            //TODO: 로딩이 잘 안되는 것 디버깅으로 찾아내기
            ResponseOutputStreamWriter responseWriter = new ResponseOutputStreamWriter(outputStream, responseHeader, body);
            responseWriter.flushResponse();

            // response200Header(dos, body.length);
            // responseBody(dos, body);
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage(), e);
        }
    }

    //TODO: 응답 리팩터링
    // Unused
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

    // Unused
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

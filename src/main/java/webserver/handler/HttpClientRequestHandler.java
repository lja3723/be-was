package webserver.handler;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.ResourceNotFoundException;
import webserver.handler.response.ResponseHandler;
import webserver.handler.response.SuccessResponseHandler;
import webserver.httpheader.request.header.HttpRequestHeader;
import webserver.httpheader.request.header.HttpRequestHeaderFactory;
import webserver.httpheader.request.parser.HttpFieldParser;
import webserver.httpheader.request.parser.HttpRequestHeadParserFactory;
import webserver.httpheader.response.header.HttpResponseHeaderFactory;

public class HttpClientRequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientRequestHandler.class);

    private final Socket connection;
    private final HttpRequestHeaderFactory httpRequestHeaderFactory;
    private final HttpFieldParser httpFieldParser;
    private final HttpRequestHeadParserFactory httpRequestHeadParserFactory;
    private final HttpResponseHeaderFactory httpResponseHeaderFactory;

    public HttpClientRequestHandler(Socket connectionSocket,
                          HttpRequestHeaderFactory httpRequestHeaderFactory,
                          HttpFieldParser httpFieldParser,
                          HttpRequestHeadParserFactory httpRequestHeadParserFactory,
                          HttpResponseHeaderFactory httpResponseHeaderFactory) {
        this.connection = connectionSocket;
        this.httpRequestHeaderFactory = httpRequestHeaderFactory;
        this.httpFieldParser = httpFieldParser;
        this.httpRequestHeadParserFactory = httpRequestHeadParserFactory;
        this.httpResponseHeaderFactory = httpResponseHeaderFactory;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequestHeader httpRequestHeader = httpRequestHeaderFactory.create(
                in, httpRequestHeadParserFactory, httpFieldParser);

            logHttpRequestHeader(httpRequestHeader);
            handleResponse(new SuccessResponseHandler(), httpRequestHeader, out);

        } catch (ResourceNotFoundException e) {
            logger.error("404 Not Found: {}", e.getMessage());
            //TODO: 404 응답 처리
        }
        catch (Exception e) {
            logger.error("500 Internal Server Error: {}", e.getMessage());
            e.printStackTrace();
            //TODO: 500 응답 처리
        }
    }

    private void logHttpRequestHeader(HttpRequestHeader header) {
        logger.debug("----- HTTP Request Header -----");
        logger.debug("HTTP Method: {}, Path: {}, HTTP Version: {}", header.method, header.path, header.version);
        header.fields.forEach(field ->
            logger.debug("Key -- {} / Value -- {}", field.key, field.value)
        );
    }

    private void handleResponse(ResponseHandler responseHandler, HttpRequestHeader httpRequestHeader, OutputStream out) {
        responseHandler.setHttpResponseHeaderFactory(httpResponseHeaderFactory);
        responseHandler.setHttpRequestHeader(httpRequestHeader);
        responseHandler.setOutputStream(out);
        responseHandler.handleResponse();
    }

}

package webserver.handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httpheader.request.HttpRequestHeader;
import webserver.httpheader.request.HttpRequestHeaderFactory;
import webserver.httpheader.request.parser.HttpFieldParser;
import webserver.httpheader.request.parser.HttpRequestHeadParserFactory;

public class HttpClientRequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientRequestHandler.class);

    private final Socket connection;
    private final HttpRequestHeaderFactory httpRequestHeaderFactory;
    private final HttpFieldParser httpFieldParser;
    private final HttpRequestHeadParserFactory httpRequestHeadParserFactory;

    public HttpClientRequestHandler(Socket connectionSocket,
                          HttpRequestHeaderFactory httpRequestHeaderFactory,
                          HttpFieldParser httpFieldParser,
                          HttpRequestHeadParserFactory httpRequestHeadParserFactory) {
        this.connection = connectionSocket;
        this.httpRequestHeaderFactory = httpRequestHeaderFactory;
        this.httpFieldParser = httpFieldParser;
        this.httpRequestHeadParserFactory = httpRequestHeadParserFactory;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(),
            connection.getPort());

        try {
            InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream();

            HttpRequestHeader httpRequestHeader = httpRequestHeaderFactory.create(
                in, httpRequestHeadParserFactory, httpFieldParser);

            logHttpRequestHeader(httpRequestHeader);

            //TODO: 응답 리팩터링
            //TODO: 정적 파일 응답 처리
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = "<h1>Hello World</h1>".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void logHttpRequestHeader(HttpRequestHeader header) {
        logger.debug("----- HTTP Request Header -----");
        logger.debug("HTTP Method: {}, Path: {}, HTTP Version: {}", header.method, header.path, header.version);
        header.fields.forEach(field ->
            logger.debug("Key -- {} / Value -- {}", field.key, field.value)
        );
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
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

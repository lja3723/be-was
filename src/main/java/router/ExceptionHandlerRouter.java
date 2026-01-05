package router;

import http.ContentType;
import http.HttpStatus;
import http.HttpVersion;
import http.header.HttpRequestHeader;
import http.header.HttpResponseHeader;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import responsehandler.HttpResponseHandler;

/**
 * Exception에 따른 HttpResponseHandler를 라우팅하는 Router
 */
public class ExceptionHandlerRouter implements Router<Exception, HttpResponseHandler> {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerRouter.class);

    @Override
    public HttpResponseHandler route(Exception e) {

        logger.error("error occur: {}, {}", e.getMessage(), e.getCause(), e);
        // TODO: 핸들러 로직 구현
        // TODO: 서버의 Exception을 확장성 있게 잡아내어 핸들러로 넘겨주는 방식으로 수정 필요, 예외에 따른 적절한 HTTP 응답 처리 로직 구현 필요
        return new HttpResponseHandler() {
            @Override
            public byte[] getBody(HttpRequestHeader httpRequestHeader, OutputStream outputStream) {
                return "<html><body><h1>500 Internal Server Error</h1><p>서버 내부 오류가 발생했습니다.</p></body></html>"
                    .getBytes();
            }

            @Override
            public HttpResponseHeader createResponseHeader(ContentType bodyContentType,
                byte[] body) {
                return HttpResponseHeader.builder()
                    .version(HttpVersion.HTTP_1_1)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(ContentType.TEXT_HTML)
                    .body(body)
                    .build();
            }
        };
    }
}

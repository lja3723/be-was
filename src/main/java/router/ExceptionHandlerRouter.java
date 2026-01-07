package router;

import exception.InternalServerErrorException;
import exception.ResourceNotFoundException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import responsehandler.HttpResponseHandler;
import responsehandler.InternalServerErrorHttpResponseHandler;
import responsehandler.ResourceNotFoundHttpResponseHandler;

/**
 * Exception에 따른 HttpResponseHandler를 라우팅하는 Router
 */
public class ExceptionHandlerRouter implements Router<Exception, HttpResponseHandler> {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerRouter.class);
    private final Map<Class<? extends Exception>, HttpResponseHandler> exceptionHandlerMap;

    public ExceptionHandlerRouter() {

        // Exception 클래스별 HttpResponseHandler 매핑 초기화
        // TODO: 추후 더 나은 방법이 있는지 고민하기
        this.exceptionHandlerMap = Map.of(
            ResourceNotFoundException.class, new ResourceNotFoundHttpResponseHandler(),
            InterruptedException.class, new InternalServerErrorHttpResponseHandler()
        );
    }

    // TODO: 정적 리소스에 대한 라우팅과 Restful API에 대한 라우팅을 분리하는 법 고민하기
    @Override
    public HttpResponseHandler route(Exception e) {
        HttpResponseHandler handler = exceptionHandlerMap.get(e.getClass());

        if (handler == null) {
            logger.error("Internal Server Error occured: {}, {}", e.getMessage(), e.getCause(), e);
            return exceptionHandlerMap.get(InternalServerErrorException.class);
        }

        logger.info("Exception handled: {}, {}", e.getMessage(), e.getCause(), e);
        return handler;

    }
}

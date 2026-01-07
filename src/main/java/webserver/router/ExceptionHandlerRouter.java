package webserver.router;

import app.exception.BadRequestException;
import app.exception.InternalServerErrorException;
import app.exception.ResourceNotFoundException;
import app.responsehandler.BadRequestHttpRequestHandler;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import app.responsehandler.HttpRequestHandler;
import app.responsehandler.InternalServerErrorHttpRequestHandler;
import app.responsehandler.ResourceNotFoundHttpRequestHandler;

/**
 * Exception에 따른 HttpResponseHandler를 라우팅하는 Router
 */
public class ExceptionHandlerRouter implements Router<Throwable, HttpRequestHandler> {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerRouter.class);
    private final Map<Class<? extends Throwable>, HttpRequestHandler> exceptionHandlerMap;

    public ExceptionHandlerRouter() {

        // Exception 클래스별 HttpResponseHandler 매핑 초기화
        // TODO: 추후 더 나은 방법이 있는지 고민하기
        this.exceptionHandlerMap = Map.of(
            BadRequestException.class, new BadRequestHttpRequestHandler(),
            ResourceNotFoundException.class, new ResourceNotFoundHttpRequestHandler(),
            InternalServerErrorException.class, new InternalServerErrorHttpRequestHandler()
        );
    }

    // TODO: 정적 리소스에 대한 라우팅과 Restful API에 대한 라우팅을 분리하는 법 고민하기
    @Override
    public HttpRequestHandler route(Throwable e) {
        HttpRequestHandler handler = exceptionHandlerMap.get(e.getClass());

        if (handler == null) {
            logger.error("Internal Server Error occured: {}, {}", e.getMessage(), e.getCause(), e);
            return exceptionHandlerMap.get(InternalServerErrorException.class);
        }

        logger.info("Exception handled: {}, {}", e.getMessage(), e.getCause(), e);
        return handler;

    }
}

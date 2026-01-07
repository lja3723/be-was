package webserver.router;

import app.exception.BadRequestException;
import app.exception.InternalServerErrorException;
import app.exception.ResourceNotFoundException;
import app.responsehandler.BadRequestHttpResponseHandler;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import app.responsehandler.HttpResponseHandler;
import app.responsehandler.InternalServerErrorHttpResponseHandler;
import app.responsehandler.ResourceNotFoundHttpResponseHandler;

/**
 * Exception에 따른 HttpResponseHandler를 라우팅하는 Router
 */
public class ExceptionHandlerRouter implements Router<Throwable, HttpResponseHandler> {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerRouter.class);
    private final Map<Class<? extends Throwable>, HttpResponseHandler> exceptionHandlerMap;

    public ExceptionHandlerRouter() {

        // Exception 클래스별 HttpResponseHandler 매핑 초기화
        // TODO: 추후 더 나은 방법이 있는지 고민하기
        this.exceptionHandlerMap = Map.of(
            BadRequestException.class, new BadRequestHttpResponseHandler(),
            ResourceNotFoundException.class, new ResourceNotFoundHttpResponseHandler(),
            InternalServerErrorException.class, new InternalServerErrorHttpResponseHandler()
        );
    }

    // TODO: 정적 리소스에 대한 라우팅과 Restful API에 대한 라우팅을 분리하는 법 고민하기
    @Override
    public HttpResponseHandler route(Throwable e) {
        HttpResponseHandler handler = exceptionHandlerMap.get(e.getClass());

        if (handler == null) {
            logger.error("Internal Server Error occured: {}, {}", e.getMessage(), e.getCause(), e);
            return exceptionHandlerMap.get(InternalServerErrorException.class);
        }

        logger.info("Exception handled: {}, {}", e.getMessage(), e.getCause(), e);
        return handler;

    }
}

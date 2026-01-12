package webserver.router;

import app.exception.InternalServerErrorException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.exception.ExceptionHandler;

/**
 * Exception에 따른 HttpResponseHandler를 라우팅하는 Router
 */
public class ExceptionHandlerRouter implements Router<Throwable, ExceptionHandler> {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerRouter.class);
    private final Map<Class<? extends Throwable>, ExceptionHandler> exceptionHandlerMap;

    public ExceptionHandlerRouter(Map<Class<? extends Throwable>, ExceptionHandler> exceptionHandlerMap) {
        this.exceptionHandlerMap = exceptionHandlerMap;
    }

    @Override
    public ExceptionHandler route(Throwable e) {
        ExceptionHandler handler = exceptionHandlerMap.get(e.getClass());

        if (handler == null) {
            logger.error("Internal Server Error occured: {}, {}", e.getMessage(), e.getCause(), e);
            return exceptionHandlerMap.get(InternalServerErrorException.class);
        }

        logger.info("Exception handled: {}, {}", e.getMessage(), e.getCause(), e);
        return handler;

    }
}

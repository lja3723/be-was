package dependency;

import app.business.SecurityChecker;
import app.business.impl.UserBusinessImpl;
import app.db.adapter.DatabaseAdapter;
import app.db.adapter.UserDatabaseAdapter;
import app.exception.BadRequestException;
import app.exception.ForbiddenException;
import app.exception.HttpMethodNotAllowedException;
import app.exception.InternalServerErrorException;
import app.exception.ResourceNotFoundException;
import app.exception.UnauthorizedException;
import app.handler.ApplicationHandler;
import app.handler.LoginHandler;
import app.handler.LogoutHandler;
import app.handler.RegistrationHandler;
import app.handler.exception.BadRequestHttpRequestHandler;
import app.handler.exception.ForbiddenExceptionHandler;
import app.handler.exception.ResourceNotFoundHttpRequestHandler;
import app.handler.exception.UnauthorizedExceptionHandler;
import app.handler.view.ArticleViewHandler;
import app.handler.view.CommentViewHandler;
import app.handler.view.LoginViewHandler;
import app.handler.view.MyPageViewHandler;
import app.handler.view.RegistrationViewHandler;
import app.handler.view.RootViewHandler;
import app.model.User;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import webserver.handler.StaticResourceHandler;
import webserver.handler.exception.ExceptionHandler;
import webserver.handler.exception.HttpMethodNotAllowedExceptionHandler;
import webserver.handler.exception.InternalServerErrorExceptionHandler;
import webserver.http.HttpEndpoint;
import webserver.http.HttpRequest;
import webserver.http.HttpSession;
import webserver.http.field.HttpField;
import webserver.http.header.HttpRequestHeaderHead;
import webserver.http.parser.HttpFieldParser;
import webserver.http.parser.HttpRequestHeaderHeadParser;
import webserver.http.parser.Parser;
import webserver.handler.HttpRequestHandler;
import webserver.router.ExceptionHandlerRouter;
import webserver.router.HttpRequestRouter;
import webserver.router.Router;

/**
 * Web Application Server의 Production Environment에서 사용되는 의존성 구현체
 */
public class WebApplicationServerProductionDependency implements WebApplicationServerDependency {

    private final Parser<String, HttpField> httpFieldParser =
        new HttpFieldParser();
    private final Parser<String, HttpRequestHeaderHead> httpRequestHeaderHeadParser =
        new HttpRequestHeaderHeadParser();
    private final Router<Throwable, ExceptionHandler<? extends Throwable>> exceptionHandlerRouter =
        new ExceptionHandlerRouter(exceptionHandlerMap());
    private final Router<HttpRequest, HttpRequestHandler> httpRequestRouter =
        new HttpRequestRouter(getApplicationHandlerMap(), getStaticResourceHandler());

    private static final HttpRequestHandler staticResourceHandler = new StaticResourceHandler();
    private static final HttpSession httpSession = new HttpSession();
    private static final SecurityChecker securityChecker = new SecurityChecker(httpSession);

    // TODO: 테스트용 DB주입 과정은 나중에 제거 필요
    public WebApplicationServerProductionDependency() {
        userDatabaseAdapter.add(new User("qwer", "asdf", "55"));
    }

    // Exception 클래스별 HttpResponseHandler 매핑 초기화
    // TODO: 추후 애너테이션 & 리플렉션으로 자동 등록하는 방식으로 변경 고려
    private static Map<Class<? extends Throwable>, ExceptionHandler<? extends Throwable>> exceptionHandlerMap() {
        return Map.of(
            BadRequestException.class, new BadRequestHttpRequestHandler(),
            ResourceNotFoundException.class, new ResourceNotFoundHttpRequestHandler(),
            InternalServerErrorException.class, new InternalServerErrorExceptionHandler(),
            HttpMethodNotAllowedException.class, new HttpMethodNotAllowedExceptionHandler(),
            UnauthorizedException.class, new UnauthorizedExceptionHandler(),
            ForbiddenException.class, new ForbiddenExceptionHandler()
        );
    }

    private static final DatabaseAdapter<String, User> userDatabaseAdapter = new UserDatabaseAdapter();
    private static final UserBusinessImpl userBusiness = new UserBusinessImpl(userDatabaseAdapter);

    // ApplicationHandler 매핑 초기화
    // TODO: 추후 애너테이션 & 리플렉션으로 자동 등록하는 방식으로 변경 고려
    // TODO: 추후 Path Variable 지원이 필요할 경우 리팩터링 필요
    private static Map<HttpEndpoint, ApplicationHandler> getApplicationHandlerMap() {
        List<ApplicationHandler> applicationHandlers = List.of(
            new ArticleViewHandler(httpSession, securityChecker),
            new CommentViewHandler(httpSession, securityChecker),
            new LoginViewHandler(securityChecker),
            new MyPageViewHandler(httpSession, securityChecker),
            new RegistrationViewHandler(securityChecker),
            new RootViewHandler(httpSession),

            new RegistrationHandler(userBusiness),
            new LoginHandler(httpSession, userBusiness),
            new LogoutHandler(httpSession, securityChecker)
        );

        return applicationHandlers.stream().collect(
            Collectors.toMap(
                ApplicationHandler::getEndpoint,
                handler -> handler
            )
        );
    }

    @Override
    public Parser<String, HttpField> getHttpFieldParser() {
        return httpFieldParser;
    }

    @Override
    public Parser<String, HttpRequestHeaderHead> getHttpRequestHeaderHeadParser() {
        return httpRequestHeaderHeadParser;
    }

    @Override
    public Router<Throwable, ExceptionHandler<? extends Throwable>> getExceptionHandlerRouter() {
        return exceptionHandlerRouter;
    }

    @Override
    public Router<HttpRequest, HttpRequestHandler> getHttpRequestRouter() {
        return httpRequestRouter;
    }

    @Override
    public HttpRequestHandler getStaticResourceHandler() {
        return staticResourceHandler;
    }
}

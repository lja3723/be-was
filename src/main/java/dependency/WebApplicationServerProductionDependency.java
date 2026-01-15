package dependency;

import app.business.SecurityChecker;
import app.business.impl.UserBusinessImpl;
import app.db.adapter.ArticleDatabaseAdapter;
import app.db.adapter.CommentDatabaseAdapter;
import app.db.adapter.DatabaseAdapter;
import app.db.adapter.ImageDatabaseAdapter;
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
import app.model.Article;
import app.model.Comment;
import app.model.Image;
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
    private static final DatabaseAdapter<String, Image> imageDatabaseAdapter = new ImageDatabaseAdapter();
    private static final DatabaseAdapter<Integer, Article> articleDatabaseAdapter = new ArticleDatabaseAdapter();
    private static final DatabaseAdapter<Integer, Comment> commentDatabaseAdapter = new CommentDatabaseAdapter();

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

    // TODO: 테스트용 DB주입 과정은 나중에 제거 필요
    public WebApplicationServerProductionDependency() {
        userDatabaseAdapter.add(new User("qwer", "asdf", "55"));

        Comment c1 = new Comment(1, "account", 1, "군인 또는 군무원이 아닌 국민은 대한민국의 영역안에서는 중대한 군사상 기밀·초병·초소·유독음식물공급·포로·군용물에 관한 죄중 법률이 정한 경우와 비상계엄이 선포된 경우를 제외하고는 군사법원의 재판을 받지 아니한다.");
        Comment c2 = new Comment(2, "account", 1, "대통령의 임기연장 또는 중임변경을 위한 헌법개정은 그 헌법개정 제안 당시의 대통령에 대하여는 효력이 없다. 민주평화통일자문회의의 조직·직무범위 기타 필요한 사항은 법률로 정한다.");
        Comment c3 = new Comment(3, "account", 1, "민주평화통일자문회의의 조직·직무범위 기타 필요한 사항은 법률로 정한다.");
        Comment c4 = new Comment(4, "account", 1, "Hidden comment 1");
        Comment c5 = new Comment(5, "account", 1, "Hidden comment 2");
        Comment c6 = new Comment(6, "account", 1, "Hidden comment 3");

        commentDatabaseAdapter.add(c1);
        commentDatabaseAdapter.add(c2);
        commentDatabaseAdapter.add(c3);
        commentDatabaseAdapter.add(c4);
        commentDatabaseAdapter.add(c5);
        commentDatabaseAdapter.add(c6);

        articleDatabaseAdapter.add(new Article(1, "qwer", "",
            "우리는 시스템 아키텍처에 대한 일관성 있는 접근이 필요하며, 필요한 모든 측면은 이미 개별적으로 인식되고 있다고 생각합니다. 즉, 응답이 잘 되고, 탄력적이며 유연하고 메시지 기반으로 동작하는 시스템 입니다. 우리는 이것을 리액티브 시스템(Reactive Systems)라고 부릅니다. 리액티브 시스템으로 구축된 시스템은 보다 유연하고, 느슨한 결합을 갖고, 확장성이 있습니다. 이로 인해 개발이 더 쉬워지고 변경 사항을 적용하기 쉬워집니다. 이 시스템은 장애에 대해 더 강한 내성을 지니며, 비록 장애가 발생하더라도, 재난이 일어나기보다는 간결한 방식으로 해결합니다. 리액티브 시스템은 높은 응답성을 가지며 사용자에게 효과적인 상호적 피드백을 제공합니다.",
            List.of(),
            List.of(c1, c2, c3, c4, c5, c6),
            0));
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

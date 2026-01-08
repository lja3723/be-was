package app.handler;

import app.business.UserBusiness;
import app.exception.BadRequestException;
import java.util.List;
import java.util.Optional;
import webserver.http.ContentType;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.QueryParameter;
import webserver.http.header.HttpResponseHeader;

public class RegistrationHandler extends ApplicationHandler {

    private final UserBusiness userBusiness;

    public RegistrationHandler(UserBusiness userBusiness) {
        super(HttpMethod.GET, "/user/create");
        this.userBusiness = userBusiness;
    }

    @Override
    public HttpResponse handleResponse(HttpRequest httpRequest) {
        QueryParameter queryParameter = new QueryParameter(httpRequest.header().uri().getQuery());

        List<String> expectedQueries = List.of(
            "userId",
            "password",
            "name",
            "email"
        );
        List<String> missingQueries = expectedQueries.stream()
            .filter(queryKey -> queryParameter.getValue(queryKey).isEmpty())
            .toList();
        if (!missingQueries.isEmpty()) {
            String missingQueriesString = String.join(", ", missingQueries);
            throw new BadRequestException("Missing required query parameters: " + missingQueriesString);
        }

        List<String> queryValues = expectedQueries.stream()
            .map(queryParameter::getValue)
            .peek(opt -> {
                if (opt.isEmpty()) {
                    throw new BadRequestException("Missing required query parameters.");
                }
            })
            .map(Optional::get)
            .toList();

        userBusiness.register(
            queryValues.get(0),
            queryValues.get(1),
            queryValues.get(2),
            queryValues.get(3)
        );

        byte[] body = "<html><body><h1>Registration Successful</h1></body></html>".getBytes();

        return new HttpResponse(
            HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.OK)
                .contentType(ContentType.TEXT_HTML)
                .body(body)
                .build(),
            body
        );
    }
}

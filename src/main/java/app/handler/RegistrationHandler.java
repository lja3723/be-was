package app.handler;

import app.business.UserBusiness;
import app.exception.BadRequestException;
import java.net.URI;
import java.net.URISyntaxException;
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
        super(HttpMethod.POST, "/user/create");
        this.userBusiness = userBusiness;
    }

    @Override
     public HttpResponse handleResponse(HttpRequest httpRequest) {
        if (httpRequest.body() == null) {
            throw new BadRequestException("Request body is missing.");
        }

        List<String> queryValues = validateRequiredQueries(
            parseQuery(httpRequest.body()),
            List.of("userId", "password", "name", "email")
        );

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

    private QueryParameter parseQuery(String queryString) {
        try {
            URI uri = new URI("?" + queryString);
            return new QueryParameter(uri.getQuery());
        } catch (URISyntaxException e) {
            throw new BadRequestException("Invalid query string: " + queryString);
        }
    }

    public List<String> validateRequiredQueries(QueryParameter queryParameter, List<String> requiredQueries) {
        List<String> missingQueries = requiredQueries.stream()
            .filter(queryKey -> queryParameter.getValue(queryKey).isEmpty())
            .toList();
        if (!missingQueries.isEmpty()) {
            String missingQueriesString = String.join(", ", missingQueries);
            throw new BadRequestException("Missing required query parameters: " + missingQueriesString);
        }

        return requiredQueries.stream()
            .map(queryParameter::getValue)
            .peek(opt -> {
                if (opt.isEmpty()) {
                    throw new BadRequestException("Missing required query parameters.");
                }
            })
            .map(Optional::get)
            .toList();
    }
}

package app.handler;

import app.business.UserBusiness;
import app.exception.BadRequestException;
import java.util.List;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.QueryParameter;
import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;
import webserver.http.header.HttpResponseHeader;
import webserver.util.QueryParameterValidator;

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

        QueryParameter queryParameter = QueryParameterValidator.validate(
            httpRequest.body(),
            List.of("userId", "password", "name", "email")
        );

        userBusiness.register(
            queryParameter.getValue("userId"),
            queryParameter.getValue("password"),
            queryParameter.getValue("name"),
            queryParameter.getValue("email")
        );

        return new HttpResponse(
            HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.FOUND)
                .field(HttpField.builder()
                    .key(HttpFieldKey.LOCATION)
                    .value("/")
                    .build())
                .build()
            , null);
    }
}

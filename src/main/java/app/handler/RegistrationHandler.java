package app.handler;

import app.business.UserBusiness;
import app.exception.BadRequestException;
import app.handler.response.RedirectResponse;
import java.util.List;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.QueryParameter;
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
            List.of("userId", "password", "name")
        );

        userBusiness.register(
            queryParameter.getValue("userId"),
            queryParameter.getValue("password"),
            queryParameter.getValue("name")
        );

        return RedirectResponse.to("/");
    }
}

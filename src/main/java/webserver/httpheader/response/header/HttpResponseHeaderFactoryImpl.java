package webserver.httpheader.response.header;


public class HttpResponseHeaderFactoryImpl implements HttpResponseHeaderFactory {

    @Override
    public HttpResponseHeaderBuilder builder() {
        return new HttpResponseHeaderBuilderImpl();
    }
}

package webserver.httpheader.response.header;

// TODO: 불필요 클래스
public class HttpResponseHeaderFactoryImpl implements HttpResponseHeaderFactory {

    @Override
    public HttpResponseHeaderBuilder builder() {
        return new HttpResponseHeaderBuilderImpl();
    }
}

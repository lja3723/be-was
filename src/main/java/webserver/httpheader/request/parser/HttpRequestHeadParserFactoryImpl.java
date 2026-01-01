package webserver.httpheader.request.parser;

public class HttpRequestHeadParserFactoryImpl implements HttpRequestHeadParserFactory {

    @Override
    public HttpRequestHeadParser create(String rawRequestLine) {
        return new HttpRequestHeadParserImpl(rawRequestLine);
    }

}

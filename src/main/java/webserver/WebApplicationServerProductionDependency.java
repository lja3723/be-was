package webserver;

import webserver.httpheader.request.HttpRequestHeaderFactory;
import webserver.httpheader.request.HttpRequestHeaderFactoryImpl;
import webserver.httpheader.request.parser.HttpFieldParser;
import webserver.httpheader.request.parser.HttpFieldParserImpl;
import webserver.httpheader.request.parser.HttpRequestHeadParserFactory;
import webserver.httpheader.request.parser.HttpRequestHeadParserFactoryImpl;

public class WebApplicationServerProductionDependency implements WebApplicationServerDependency {

    private final HttpFieldParser httpFieldParser = new HttpFieldParserImpl();
    private final HttpRequestHeadParserFactory httpRequestHeadParserFactory = new HttpRequestHeadParserFactoryImpl();
    private final HttpRequestHeaderFactory httpRequestHeaderFactory = new HttpRequestHeaderFactoryImpl();

    @Override
    public HttpFieldParser getHttpFieldParser() {
        return httpFieldParser;
    }

    @Override
    public HttpRequestHeadParserFactory getHttpRequestHeadParserFactory() {
        return httpRequestHeadParserFactory;
    }

    @Override
    public HttpRequestHeaderFactory getHttpRequestHeaderFactory() {
        return httpRequestHeaderFactory;
    }
}

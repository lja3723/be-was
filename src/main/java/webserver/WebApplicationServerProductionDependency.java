package webserver;

import webserver.httpheader.request.header.HttpRequestHeaderFactory;
import webserver.httpheader.request.header.HttpRequestHeaderFactoryImpl;
import webserver.httpheader.request.parser.HttpFieldParser;
import webserver.httpheader.request.parser.HttpFieldParserImpl;
import webserver.httpheader.request.parser.HttpRequestHeadParserFactory;
import webserver.httpheader.request.parser.HttpRequestHeadParserFactoryImpl;
import webserver.httpheader.response.header.HttpResponseHeaderFactory;
import webserver.httpheader.response.header.HttpResponseHeaderFactoryImpl;

public class WebApplicationServerProductionDependency implements WebApplicationServerDependency {

    private final HttpFieldParser httpFieldParser = new HttpFieldParserImpl();
    private final HttpRequestHeadParserFactory httpRequestHeadParserFactory = new HttpRequestHeadParserFactoryImpl();
    private final HttpRequestHeaderFactory httpRequestHeaderFactory = new HttpRequestHeaderFactoryImpl();
    private final HttpResponseHeaderFactory httpResponseHeaderFactory = new HttpResponseHeaderFactoryImpl();

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

    @Override
    public HttpResponseHeaderFactory getHttpResponseHeaderFactory() {
        return httpResponseHeaderFactory;
    }
}

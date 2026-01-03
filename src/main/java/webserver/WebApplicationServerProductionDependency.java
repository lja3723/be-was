package webserver;

import webserver.httpheader.request.header.HttpRequestHeaderDecoder;
import webserver.httpheader.request.header.HttpRequestHeaderDecoderImpl;
import webserver.httpheader.request.parser.HttpFieldParser;
import webserver.httpheader.request.parser.HttpFieldParserImpl;
import webserver.httpheader.request.parser.HttpRequestHeadParserFactory;
import webserver.httpheader.request.parser.HttpRequestHeadParserFactoryImpl;
import webserver.httpheader.response.header.HttpResponseHeaderFactory;
import webserver.httpheader.response.header.HttpResponseHeaderFactoryImpl;

/**
 * Web Application Server의 Production Environment에서 사용되는 의존성 구현체
 */
public class WebApplicationServerProductionDependency implements WebApplicationServerDependency {

    private final HttpFieldParser httpFieldParser = new HttpFieldParserImpl();
    private final HttpRequestHeadParserFactory httpRequestHeadParserFactory = new HttpRequestHeadParserFactoryImpl();
    private final HttpRequestHeaderDecoder httpRequestHeaderDecoder = new HttpRequestHeaderDecoderImpl();
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
    public HttpRequestHeaderDecoder getHttpRequestHeaderDecoder() {
        return httpRequestHeaderDecoder;
    }

    @Override
    public HttpResponseHeaderFactory getHttpResponseHeaderFactory() {
        return httpResponseHeaderFactory;
    }
}

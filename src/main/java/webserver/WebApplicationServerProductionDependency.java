package webserver;

import webserver.header.request.header.HttpRequestHeaderDecoder;
import webserver.header.request.header.HttpRequestHeaderDecoderImpl;
import webserver.header.parser.HttpFieldParser;
import webserver.header.parser.HttpFieldParserImpl;
import webserver.header.parser.HttpRequestHeadParserFactory;
import webserver.header.parser.HttpRequestHeadParserFactoryImpl;

/**
 * Web Application Server의 Production Environment에서 사용되는 의존성 구현체
 */
public class WebApplicationServerProductionDependency implements WebApplicationServerDependency {

    private final HttpFieldParser httpFieldParser = new HttpFieldParserImpl();
    private final HttpRequestHeadParserFactory httpRequestHeadParserFactory = new HttpRequestHeadParserFactoryImpl();
    private final HttpRequestHeaderDecoder httpRequestHeaderDecoder = new HttpRequestHeaderDecoderImpl();

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
}

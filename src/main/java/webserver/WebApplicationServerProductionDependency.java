package webserver;

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

    @Override
    public HttpFieldParser getHttpFieldParser() {
        return httpFieldParser;
    }

    @Override
    public HttpRequestHeadParserFactory getHttpRequestHeadParserFactory() {
        return httpRequestHeadParserFactory;
    }

}

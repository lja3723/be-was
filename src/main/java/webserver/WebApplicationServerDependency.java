package webserver;

import webserver.httpheader.request.HttpRequestHeaderFactory;
import webserver.httpheader.request.parser.HttpFieldParser;
import webserver.httpheader.request.parser.HttpRequestHeadParserFactory;

public interface WebApplicationServerDependency {

    HttpFieldParser getHttpFieldParser();
    HttpRequestHeadParserFactory getHttpRequestHeadParserFactory();
    HttpRequestHeaderFactory getHttpRequestHeaderFactory();
}

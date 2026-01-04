package webserver;

import webserver.httpheader.request.header.HttpRequestHeaderFactory;
import webserver.httpheader.request.parser.HttpFieldParser;
import webserver.httpheader.request.parser.HttpRequestHeadParserFactory;
import webserver.httpheader.response.header.HttpResponseHeaderFactory;

public interface WebApplicationServerDependency {

    HttpFieldParser getHttpFieldParser();
    HttpRequestHeadParserFactory getHttpRequestHeadParserFactory();
    HttpRequestHeaderFactory getHttpRequestHeaderFactory();
    HttpResponseHeaderFactory getHttpResponseHeaderFactory();
}

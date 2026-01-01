package webserver.httpheader.request.parser;

import webserver.httpheader.HttpMethod;
import webserver.httpheader.HttpVersion;

public interface HttpRequestHeadParser {

    HttpMethod getMethod();
    String getPath();
    HttpVersion getVersion();
}

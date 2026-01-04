package webserver.httpheader.request.parser;

public interface HttpRequestHeadParserFactory {

    HttpRequestHeadParser create(String rawRequestLine);
}

package webserver.httpheader.request.parser;

import webserver.httpheader.field.HttpField;

public interface HttpFieldParser {

    HttpField parse(String rawFieldLine);
}

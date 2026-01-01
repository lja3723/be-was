package webserver.httpheader.response.header;

import webserver.httpheader.HttpVersion;
import webserver.httpheader.field.HttpField;
import webserver.httpheader.response.HttpStatus;

public interface HttpResponseHeaderBuilder{

    void version(HttpVersion version);
    void status(HttpStatus status);
    void field(HttpField field);
    void body(byte[] body);
    HttpResponseHeader build();
}

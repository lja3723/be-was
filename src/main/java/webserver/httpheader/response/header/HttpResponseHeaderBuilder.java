package webserver.httpheader.response.header;

import webserver.httpheader.HttpVersion;
import webserver.httpheader.field.HttpField;
import webserver.httpheader.response.ContentType;
import webserver.httpheader.response.HttpStatus;

public interface HttpResponseHeaderBuilder{

    HttpResponseHeaderBuilder version(HttpVersion version);
    HttpResponseHeaderBuilder status(HttpStatus status);
    HttpResponseHeaderBuilder field(HttpField field);
    HttpResponseHeaderBuilder contentType(ContentType contentType);
    HttpResponseHeaderBuilder body(byte[] body);
    HttpResponseHeader build();
}

package webserver.header.response.header;

import webserver.header.HttpVersion;
import webserver.header.field.HttpField;
import webserver.header.response.ContentType;
import webserver.header.response.HttpStatus;

/**
 * HTTP Response Header를 빌드하는 Builder 인터페이스
 */
// TODO: Builder를 HttpResponseHeader 내부 static inner class로 변경 고려
public interface HttpResponseHeaderBuilder{

    HttpResponseHeaderBuilder version(HttpVersion version);
    HttpResponseHeaderBuilder status(HttpStatus status);
    HttpResponseHeaderBuilder field(HttpField field);
    HttpResponseHeaderBuilder contentType(ContentType contentType);
    HttpResponseHeaderBuilder body(byte[] body);
    HttpResponseHeader build();
}

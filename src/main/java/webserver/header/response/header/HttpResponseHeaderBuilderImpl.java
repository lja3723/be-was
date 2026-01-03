package webserver.header.response.header;

import java.util.ArrayList;
import java.util.List;
import webserver.header.HttpHeader;
import webserver.header.HttpVersion;
import webserver.header.field.HttpField;
import webserver.header.field.HttpFieldKey;
import webserver.header.response.ContentType;
import webserver.header.response.HttpStatus;

/**
 * HTTP Response Header를 빌드하는 Builder 구현체
 */
public class HttpResponseHeaderBuilderImpl implements HttpResponseHeaderBuilder {

    private HttpVersion version;
    private HttpStatus status;
    private final List<HttpField> fields;

    public HttpResponseHeaderBuilderImpl() {
        this.fields = new ArrayList<>();
    }

    @Override
    public HttpResponseHeaderBuilder version(HttpVersion version) {
        this.version = version;
        return this;
    }

    @Override
    public HttpResponseHeaderBuilder status(HttpStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public HttpResponseHeaderBuilder field(HttpField field) {
        this.fields.add(field);
        return this;
    }

    @Override
    public HttpResponseHeaderBuilder contentType(ContentType contentType) {
        String fieldValue = contentType.getValue();

        // TODO: 추후 UTF-8 이외의 charset 도 처리할 수 있도록 수정 필요
        // TODO: 좀 더 추상화가 필요
        String category = fieldValue.split("/")[0];
        if (category.equals("text") || category.equals("application")) {
            fieldValue += "; charset=UTF-8";
        }
        this.fields.add(new HttpField(HttpFieldKey.CONTENT_TYPE, fieldValue));
        return this;
    }

    @Override
    public HttpResponseHeaderBuilder body(byte[] body) {
        this.fields.add(new HttpField(HttpFieldKey.CONTENT_LENGTH, String.valueOf(body.length)));
        return this;
    }

    public HttpResponseHeader build() {
        return new HttpResponseHeader(
            new HttpHeader(version, fields),
            status
        );
    }
}

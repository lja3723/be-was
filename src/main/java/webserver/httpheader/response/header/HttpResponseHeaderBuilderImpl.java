package webserver.httpheader.response.header;

import java.util.ArrayList;
import webserver.httpheader.HttpVersion;
import webserver.httpheader.field.HttpField;
import webserver.httpheader.field.HttpFieldKey;
import webserver.httpheader.response.ContentType;
import webserver.httpheader.response.HttpStatus;

public class HttpResponseHeaderBuilderImpl implements HttpResponseHeaderBuilder {

    private final HttpResponseHeader header;

    public HttpResponseHeaderBuilderImpl() {
        this.header = new HttpResponseHeader();
        header.fields = new ArrayList<>();
    }

    @Override
    public HttpResponseHeaderBuilder version(HttpVersion version) {
        header.version = version;
        return this;
    }

    @Override
    public HttpResponseHeaderBuilder status(HttpStatus status) {
        header.status = status;
        return this;
    }

    @Override
    public HttpResponseHeaderBuilder field(HttpField field) {
        header.fields.add(field);
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
        header.fields.add(new HttpField(HttpFieldKey.CONTENT_TYPE, fieldValue));
        return this;
    }

    @Override
    public HttpResponseHeaderBuilder body(byte[] body) {
        header.fields.add(new HttpField(HttpFieldKey.CONTENT_LENGTH, String.valueOf(body.length)));
        return this;
    }

    public HttpResponseHeader build() {
        return header;
    }
}

package webserver.handler.response.util;

import java.io.DataOutputStream;
import java.io.OutputStream;
import webserver.exception.InternalServerError;
import webserver.httpheader.response.header.HttpResponseHeader;

public class HttpResponseWriter {

    private final DataOutputStream dataOutputStream;
    private final HttpResponseHeader responseHeader;
    private final byte[] body;

    public HttpResponseWriter(OutputStream outputStream, HttpResponseHeader responseHeader, byte[] body) {
        this.dataOutputStream = new DataOutputStream(outputStream);
        this.responseHeader = responseHeader;
        this.body = body;
    }

    public String encodeHeader() {
        final String CRLF = "\r\n";
        StringBuilder builder = new StringBuilder();

        builder.append(responseHeader.version.getValue()).append(" ")
            .append(responseHeader.status.getCode()).append(" ")
            .append(responseHeader.status.getReasonPhrase()).append(CRLF);
        for (var field : responseHeader.fields) {
            builder.append(field.key.getValue()).append(": ")
                .append(field.value).append(CRLF);
        }
        builder.append(CRLF);
        return builder.toString();
    }

    public void flushResponse() throws InternalServerError {
        try {
            dataOutputStream.writeBytes(encodeHeader());
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (Exception e) {
            throw new InternalServerError("Failed to flush response", e);
        }

    }
}

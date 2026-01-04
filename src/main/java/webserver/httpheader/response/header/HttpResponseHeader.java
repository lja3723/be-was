package webserver.httpheader.response.header;

import webserver.httpheader.HttpHeader;
import webserver.httpheader.response.HttpStatus;

//TODO: HttpResponseHeader 구현하기
public class HttpResponseHeader extends HttpHeader {

    private static final String CRLF = "\r\n";

    public HttpStatus status;

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(version.getValue()).append(" ")
            .append(status.getCode()).append(" ")
            .append(status.getReasonPhrase()).append(CRLF);
        for (var field : fields) {
            builder.append(field.key.getValue()).append(": ")
                .append(field.value).append(CRLF);
        }
        builder.append(CRLF);
        return builder.toString();
    }
}

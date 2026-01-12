package webserver.util;

import webserver.http.header.HttpResponseHeader;

/**
 * {@link HttpResponseHeader}를 HTTP Response Protocol을 준수하는 문자열로 인코딩함
 * @return 인코딩된 HTTP 응답 헤더 문자열
 */
public class HttpResponseHeaderEncoder {

    public static final String CRLF = "\r\n";

    public static String encode(HttpResponseHeader header) {
        StringBuilder builder = new StringBuilder();

        // First Line
        builder.append(header.common().version().getValue()).append(" ")
            .append(header.status().getCode()).append(" ")
            .append(header.status().getReasonPhrase()).append(CRLF);

        // Header Fields
        header.common().fields().forEach(field ->
            builder.append(HttpFieldEncoder.encode(field)).append(CRLF)
        );
        return builder.append(CRLF).toString();
    }
}

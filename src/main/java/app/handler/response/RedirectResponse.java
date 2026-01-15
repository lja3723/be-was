package app.handler.response;

import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;
import webserver.http.header.HttpResponseHeader;

/**
 * 리다이렉트 응답을 생성하는 유틸리티 클래스
 */
public class RedirectResponse {

    public static HttpResponse to(String location) {
        return new HttpResponse(
            HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.FOUND)
                .field(HttpField.builder()
                    .key(HttpFieldKey.LOCATION)
                    .value(location)
                    .build())
                .build()
            , null);
    }
}

package webserver.util;

import webserver.http.HttpRequest;
import webserver.http.field.HttpFieldKey;
import webserver.http.field.HttpFieldValue;
import webserver.http.field.HttpFieldValueParameter;

/**
 * HTTP 요청에서 쿠키 속성을 추출하는 유틸리티 클래스
 */
public class CookieExtractor {

    /**
     * 주어진 HTTP 요청에서 특정 쿠키 속성의 값을 추출합니다.
     *
     * @param httpRequest  HTTP 요청 객체
     * @param attributeKey 추출할 쿠키 속성의 키
     * @return 해당 쿠키 속성의 값, 존재하지 않으면 null
     */
    public static String getAttributeFrom(HttpRequest httpRequest, String attributeKey) {

        return httpRequest.header().common().fields().stream()
            .filter(field -> field.key() == HttpFieldKey.COOKIE)
            .map(field -> field.values().get(0))
            .map(HttpFieldValue::parameters)
            .findFirst()
            .flatMap(httpFieldValueParameters -> httpFieldValueParameters.stream()
                .filter(p -> p.key().equals(attributeKey))
                .map(HttpFieldValueParameter::value)
                .findFirst())
            .orElse(null);
    }
}

package webserver.http.field;

import java.util.HashMap;

/**
 * HTTP request/response Header의 Field Key를 표현
 */
public enum HttpFieldKey {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    HOST("Host"),
    USER_AGENT("User-Agent"),
    ACCEPT("Accept"),
    CONNECTION("Connection"),
    CACHE_CONTROL("Cache-Control"),
    SEC_CH_UA("sec-ch-ua"),
    SEC_CH_UA_MOBILE("sec-ch-ua-mobile"),
    SEC_CH_UA_PLATFORM("sec-ch-ua-platform"),
    SEC_FETCH_SITE("Sec-Fetch-Site"),
    SEC_FETCH_MODE("Sec-Fetch-Mode"),
    SEC_FETCH_USER("Sec-Fetch-User"),
    SEC_FETCH_DEST("Sec-Fetch-Dest"),
    AUTHORIZATION("Authorization"),
    COOKIE("Cookie"),
    SET_COOKIE("Set-Cookie"),
    LOCATION("Location"),
    SERVER("Server"),
    DATE("Date"),
    LAST_MODIFIED("Last-Modified"),
    ETAG("ETag"),
    TRANSFER_ENCODING("Transfer-Encoding"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    UPGRADE_INSECURE_REQUESTS("Upgrade-Insecure-Requests"),
    ORIGIN("Origin"),
    REFERER("Referer"),
    VIA("Via"),
    EXPIRES("Expires"),
    WARNING("Warning"),
    UNKNOWN("Unknown");

    /**
     * HTTP Header Field Key에 실제로 들어가는 문자열 값
     */
    private final String value;

    HttpFieldKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * String -> HttpFieldKey 매핑을 위한 HashMap
     */
    // TODO: String -> HttpFieldKey 빠른 매핑을 위한 방법이 이렇게 static이 적절한지 고민 필요
    private static HashMap<String, HttpFieldKey> valueMap = null;

    /**
     * 최초 매핑 사용 시 HashMap 초기화
     */
    synchronized private static void initKeyMap() {
        if (valueMap != null) {
            return;
        }

        valueMap = new HashMap<>();
        for (HttpFieldKey fieldKey : HttpFieldKey.values()) {
            valueMap.put(fieldKey.getValue().toLowerCase(), fieldKey);
        }
    }

    /**
     * 문자열로부터 HttpFieldKey 객체를 반환
     * @param key HTTP Header Field Key 문자열
     * @return 대응하는 HttpFieldKey 객체. 없을 경우 UNKNOWN 반환
     */
    public static HttpFieldKey fromString(String key) {
        if (valueMap == null) {
            initKeyMap();
        }

        return valueMap.getOrDefault(key.toLowerCase(), UNKNOWN);
    }

}

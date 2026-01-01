package webserver.httpheader.field;

import java.util.HashMap;

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
    WARNING("Warning"),
    UNKNOWN("Unknown");

    private final String value;

    HttpFieldKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static HashMap<String, HttpFieldKey> valueMap = null;

    synchronized private static void initKeyMap() {
        if (valueMap != null) {
            return;
        }

        valueMap = new HashMap<>();
        for (HttpFieldKey fieldKey : HttpFieldKey.values()) {
            valueMap.put(fieldKey.getValue().toLowerCase(), fieldKey);
        }
    }

    public static HttpFieldKey fromString(String key) {
        if (valueMap == null) {
            initKeyMap();
        }

        return valueMap.getOrDefault(key.toLowerCase(), UNKNOWN);
    }

}

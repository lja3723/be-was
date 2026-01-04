package webserver.http;

/**
 * HTTP Method를 표현하는 Enum 클래스
 */
public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS"),
    PATCH("PATCH"),
    TRACE("TRACE"),
    CONNECT("CONNECT");
    private final String value;
    HttpMethod(String value) {
        this.value = value;
    }
    public String value() {
        return value;
    }

    // TODO: 더 빠른 탐색을 위해 Map으로 변경 고려
    public static HttpMethod fromString(String method) {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            if (httpMethod.value.equalsIgnoreCase(method)) {
                return httpMethod;
            }
        }
        throw new IllegalArgumentException("Unknown HTTP method: " + method);
    }
}

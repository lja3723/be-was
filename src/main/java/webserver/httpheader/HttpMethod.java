package webserver.httpheader;

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

    public static HttpMethod fromString(String method) {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            if (httpMethod.value.equalsIgnoreCase(method)) {
                return httpMethod;
            }
        }
        throw new IllegalArgumentException("Unknown HTTP method: " + method);
    }
}

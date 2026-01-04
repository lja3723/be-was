package webserver.httpheader;

public enum HttpVersion {
    HTTP_0_9("HTTP/0.9"),
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1"),
    HTTP_2_0("HTTP/2.0"),
    HTTP_3_0("HTTP/3.0"),
    UNKNOWN("UNKNOWN");

    private final String value;

    HttpVersion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static HttpVersion fromString(String version) {
        for (HttpVersion httpVersion : HttpVersion.values()) {
            if (httpVersion.getValue().equalsIgnoreCase(version)) {
                return httpVersion;
            }
        }
        throw new IllegalArgumentException("Unknown HTTP version: " + version);
    }
}

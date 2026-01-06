package webserver.http;

/**
 * HTTP Response의 Content-Type을 표현하는 Enum 클래스
 */
public enum ContentType {
    TEXT_PLAIN("text/plain", "txt"),
    TEXT_HTML("text/html", "html"),
    TEXT_CSS("text/css", "css"),
    APPLICATION_JAVASCRIPT("application/javascript", "js"),
    APPLICATION_JSON("application/json", "json"),
    IMAGE_PNG("image/png", "png"),
    IMAGE_JPG("image/jpeg", "jpg"),
    IMAGE_GIF("image/gif", "gif"),
    IMAGE_SVG_XML("image/svg+xml", "svg");

    private final String value;
    private final String fileExtension;

    ContentType(String value, String fileExtension) {
        this.value = value;
        this.fileExtension = fileExtension;
    }

    public String getValue() {
        return value;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public static ContentType fromFileExtension(String extension) {
        //TODO: 더 빠른 탐색으로 리팩터링 필요
        for (ContentType contentType : values()) {
            if (contentType.getFileExtension().equalsIgnoreCase(extension)) {
                return contentType;
            }
        }
        return TEXT_PLAIN; // 일치하는 확장자가 없는 경우 기본값 반환
    }
}

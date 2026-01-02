package webserver.handler.response.util;

import webserver.httpheader.response.ContentType;

/**
 * 리소스 경로를 나타내는 클래스
 * 1. HTTP의 request path를 리소스 경로로 매핑하는 기능
 * 예: "/" -> "index.html", "/about" -> "about.html"
 * 2. 리소스 경로에서 파일의 확장자가 무엇인지 파싱하는 기능
 * 예: "index.html" -> "html", "style.css" -> "css"
 * 3. http request path에서 리소스 경로를 얻는 기능
 */
public class ResourcePath {

    public String path;

    public ResourcePath(String httpRequestPath) {
        this.path = httpRequestPath;
    }

    public String getResourcePath() {
        if (path.equals("/")) {
            return "static/index.html";
        }
        return "static" + path;
    }

    public String getFileName() {
        String[] split = path.split("/");

        return split[split.length - 1];
    }

    public ContentType getContentType() {
        String[] parts = getFileName().split("\\.");
        String extension = parts[parts.length - 1].toLowerCase();
        return ContentType.fromFileExtension(extension);
    }

}

package handler.util;

import http.ContentType;

/**
 * HTTP request의 Path로 파생시킬 수 있는 Resource Path 및 기타 정보를 제공하는 유틸리티 클래스
 * <p>1. HTTP의 request path를 리소스 경로로 매핑하는 기능
 * <ol>   예: {@code "/" -> "static/index.html", "/about" -> "static/about/index.html"} </ol></p>
 * <p>2. 리소스 경로에서 파일의 확장자가 무엇인지 파싱하는 기능
 * <ol>   예: {@code "index.html" -> "html", "style.css" -> "css", "/about -> html"}</ol><p/>
 */
// TODO: 추후 record로 변경, HttpRequestHead가 사용, 파라미터 & 쿼리 요청도 받을 수 있도록 확장 필요
public class ResourcePath {

    public String path;

    /**
     * 생성자
     * @param httpRequestPath HTTP Protocol을 준수하는 request의 Path 경로
     */
    public ResourcePath(String httpRequestPath) {
        this.path = httpRequestPath;
    }

    /**
     * HTTP request path를 리소스 경로로 매핑
     * @return 매핑된 리소스 경로 문자열
     */
    public String getResourcePath() {
        if (path.equals("/")) {
            return "static/index.html";
        }
        return "static" + path;
    }

    /**
     * 리소스 경로에서 파일 이름을 추출
     * @return 파일 이름 문자열
     */
    public String getFileName() {
        String[] split = path.split("/");

        return split[split.length - 1];
    }

    /**
     * 리소스 경로에서 파일 확장자를 기반으로 Content-Type을 결정
     * @return 해당 파일의 Content-Type 열거형 값
     */
    public ContentType getContentType() {
        String[] parts = getFileName().split("\\.");
        String extension = parts[parts.length - 1].toLowerCase();
        return ContentType.fromFileExtension(extension);
    }

}

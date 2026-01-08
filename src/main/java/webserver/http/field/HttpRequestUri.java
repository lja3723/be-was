package webserver.http.field;

import webserver.http.ContentType;

/**
 * HTTP request의 Path로 파생시킬 수 있는 Resource Path 및 기타 정보를 제공하는 유틸리티 클래스
 * <p>1. HTTP의 request path를 리소스 경로로 매핑하는 기능
 * <ol>   예: {@code "/" -> "static/index.html", "/about" -> "static/about/index.html"} </ol></p>
 * <p>2. 리소스 경로에서 파일의 확장자가 무엇인지 파싱하는 기능
 * <ol>   예: {@code "index.html" -> "html", "style.css" -> "css", "/about -> html"}</ol><p/>
 */
// TODO: 추후 resourcePath를 더 체계적으로 표현 필요, 파라미터 & 쿼리 요청도 받을 수 있도록 확장 필요
public record HttpRequestUri(String resourcePath, String fileName, ContentType contentType) {

}

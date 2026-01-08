package webserver.http.parser;

import webserver.http.ContentType;
import webserver.http.field.HttpRequestUri;

/**
 * HTTP request의 URL을 파싱하는 파서 클래스
 */
// TODO: 추후 쿼리 파라미터 등도 처리할 수 있도록 확장 필요
public class HttpRequestUriParser implements Parser<String, HttpRequestUri> {

    @Override
    public HttpRequestUri parse(String rawUrl) {
        String filename = getFileName(rawUrl);
        return new HttpRequestUri(
            getResourcePath(rawUrl),
            filename,
            getContentType(filename)
        );
    }

    /**
     * HTTP request path를 리소스 경로로 매핑
     * @return 매핑된 리소스 경로 문자열
     */
    public String getResourcePath(String rawUri) {
        if (rawUri.equals("/")) {
            return "static/index.html";
        }
        return "static" + rawUri;
    }

    /**
     * 리소스 경로에서 파일 이름을 추출
     * @return 파일 이름 문자열
     */
    public String getFileName(String rawUri) {
        // TODO: 추후 하드코딩 대신 더 나은 방법으로 개선 필요
        if (rawUri.equals("/")) {
            return "index.html";
        }

        String[] split = rawUri.split("/");

        return split[split.length - 1];
    }

    /**
     * 리소스 경로에서 파일 확장자를 기반으로 Content-Type을 결정
     * @return 해당 파일의 Content-Type 열거형 값
     */
    public ContentType getContentType(String filename) {
        String[] parts = filename.split("\\.");
        String extension = parts[parts.length - 1].toLowerCase();
        return ContentType.fromFileExtension(extension);
    }
}

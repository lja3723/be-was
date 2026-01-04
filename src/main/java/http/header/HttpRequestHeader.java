package http.header;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import http.HttpMethod;
import http.HttpVersion;
import http.field.HttpField;
import http.header.HttpHeader.HttpHeaderBuilder;
import http.parser.Parser;

/**
 * HTTP Request Header를 표현하는 Data Class
 */
public record HttpRequestHeader(HttpHeader common, HttpMethod method, String path) {

    /**
     * HttpRequestHeader 객체를 빌드하기 위한 빌더 클래스
     */
    public static class HttpRequestHeaderBuilder {
        private final HttpHeaderBuilder commonBuilder;
        private HttpMethod method;
        private String path;

        public HttpRequestHeaderBuilder() {
            this.commonBuilder = HttpHeader.builder();
        }

        public HttpRequestHeaderBuilder version(HttpVersion version) {
            commonBuilder.version(version);
            return this;
        }

        public HttpRequestHeaderBuilder field(HttpField field) {
            commonBuilder.field(field);
            return this;
        }

        public HttpRequestHeaderBuilder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public HttpRequestHeaderBuilder path(String path) {
            this.path = path;
            return this;
        }

        public HttpRequestHeader build() {
            return new HttpRequestHeader(
                commonBuilder.build(),
                method,
                path
            );
        }
    }

    /**
     * HttpRequestHeaderBuilder 인스턴스를 반환하는 정적 팩토리 메서드
     */
    public static HttpRequestHeaderBuilder builder() {
        return new HttpRequestHeaderBuilder();
    }

    /**
     * InputStream을 파싱하여 HttpRequestHeader 객체를 생성
     * @param inputStream 클라이언트 Request의 InputStream
     * @param httpRequestHeaderHeadParser
     * @param httpFieldParser
     * @return 파싱된 HttpRequestHeader 객체
     */
    public static HttpRequestHeader decodeInputStream(
                  InputStream inputStream,
                  Parser<HttpRequestHeaderHead, String> httpRequestHeaderHeadParser,
                  Parser<HttpField, String> httpFieldParser) {
        try {
            // InputStream을 행 단위로 읽기 준비
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = Optional.ofNullable(reader.readLine())
                .orElseThrow(() -> new RuntimeException("Empty request"));

            // Request의 첫 line(Head 부분) 파싱
            HttpRequestHeaderHead httpRequestHead = httpRequestHeaderHeadParser.parse(line);
            HttpRequestHeaderBuilder builder = HttpRequestHeader.builder()
                .version(httpRequestHead.version())
                .method(httpRequestHead.method())
                .path(httpRequestHead.path());

            // 나머지 필드 파싱
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                builder.field(httpFieldParser.parse(line));
            }

            return builder.build();

        } catch (IOException e) {
            throw new RuntimeException("Error reading request", e);
        }
    }
}

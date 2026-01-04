package http.header;


import http.HttpMethod;
import http.HttpVersion;

/**
 * HTTP Request Header의 Head 부분을 표현하는 Data Class
 */
// TODO: 빌더 만들기, 추후 Path 부분을 별도의 record로 분리 예정
public record HttpRequestHeaderHead(HttpMethod method, String path, HttpVersion version) {

}

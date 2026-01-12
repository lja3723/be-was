package webserver.http;

/**
 * HTTP endpoint 정보를 담는 레코드 클래스
 *
 * @param method HTTP 메서드 (예: GET, POST 등)
 * @param url    요청 URL
 */
public record HttpEndpoint(HttpMethod method, String url) {

}

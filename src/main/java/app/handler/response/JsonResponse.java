package app.handler.response;

import app.exception.InternalServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import webserver.http.ContentType;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.header.HttpResponseHeader;

/**
 * JSON 형식의 HTTP 응답을 생성하는 유틸리티 클래스 (AI 도움 받아 생성)
 */
public class JsonResponse {

    // ObjectMapper는 스레드 안전, 상수로 관리하여 재사용
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 성공 응답 (200 OK)
     */
    public static HttpResponse ok(Object body) {
        return createResponse(HttpStatus.OK, body);
    }

    /**
     * 잘못된 요청 (400 Bad Request)
     */
    public static HttpResponse badRequest(Object body) {
        return createResponse(HttpStatus.BAD_REQUEST, body);
    }

    /**
     * 권한 없음 (401 Unauthorized)
     */
    public static HttpResponse unauthorized(Object body) {
        return createResponse(HttpStatus.UNAUTHORIZED, body);
    }

    private static HttpResponse createResponse(HttpStatus status, Object body) {
        try {
            // 자바 객체를 JSON 문자열로 변환
            String jsonBody = objectMapper.writeValueAsString(body);
            byte[] bodyBytes = jsonBody.getBytes(StandardCharsets.UTF_8);

            return new HttpResponse(
                HttpResponseHeader.builder()
                    .version(HttpVersion.HTTP_1_1)
                    .status(status)
                    .contentType(ContentType.APPLICATION_JSON)
                    .body(bodyBytes)
                    .build(),
                bodyBytes // 응답 본문에 바이트 배열 전달
            );
        } catch (JsonProcessingException e) {
            // 직렬화 실패 시 500 Internal Server Error 처리 (추측: 시스템 예외 처리 로직 필요)
            throw new InternalServerErrorException("JSON serialization failed", e);
        }
    }
}

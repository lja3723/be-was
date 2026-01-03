package webserver.httpheader.field;

/**
 * HTTP request/response Header의 Field를 표현
 * @param key
 * @param value
 */
// TODO: 추후 key에 대응하는 값을 문자열이 아닌 객체로 저장하도록 변경
public record HttpField(HttpFieldKey key, String value /*List<HttpFieldValue> values*/) {

}
package http.field;

/**
 * <p>HTTP Header Field Value의 Parameter를 표현하는 record</p>
 * 추후 {@link HttpFieldValue}의 parameters로 사용 예정
 */
public record HttpFieldValueParameter(String key, String value) {

}

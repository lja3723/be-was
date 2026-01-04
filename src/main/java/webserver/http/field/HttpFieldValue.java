package webserver.http.field;

import java.util.List;

/**
 * <p>HTTP Header Field의 Value와 그에 대응하는 Parameter들을 표현</p>
 * 추후 {@link HttpField}의 value를 String이 아닌 List<HttpFieldValue>로 변경할 때 사용 예정
 */
public class HttpFieldValue {
    public String value;
    public List<HttpFieldValueParameter> parameters;
}

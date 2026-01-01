package webserver.httpheader.field;

import java.util.List;

public class HttpField {
    public HttpFieldKey key;
    //TODO: 추후 key에 대응하는 값을 문자열이 아닌 객체로 저장하도록 변경
    //public List<HttpFieldValue> values;
    public String value;
}

package webserver.httpheader;

import java.util.List;
import webserver.httpheader.field.HttpField;

/*
 * Dataclass
 * HTTP 헤더의 버전과 필드들을 담고 있는 클래스
 */
public abstract class HttpHeader {

    public HttpVersion version;
    public List<HttpField> fields;
}

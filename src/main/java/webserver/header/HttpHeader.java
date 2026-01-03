package webserver.header;

import java.util.List;
import webserver.header.field.HttpField;

/**
 * <p>HTTP 헤더의 버전과 필드들을 담고 있는 Data Class</p>
 * Response와 Request 헤더가 공통으로 가지는 속성들을 포함
 */
public record HttpHeader(HttpVersion version, List<HttpField> fields) {

}

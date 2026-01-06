package webserver.http.header;

import java.util.ArrayList;
import java.util.List;
import webserver.http.HttpVersion;
import webserver.http.field.HttpField;

/**
 * <p>HTTP 헤더의 버전과 필드들을 담고 있는 Data Class</p>
 * Response와 Request 헤더가 공통으로 가지는 속성들을 포함
 */
public record HttpHeader(HttpVersion version, List<HttpField> fields) {

    /**
     * HttpHeader 객체를 빌드하기 위한 빌더 클래스
     */
    public static class HttpHeaderBuilder {
        private HttpVersion version;
        private final List<HttpField> fields;

        public HttpHeaderBuilder() {
            this.fields = new ArrayList<>();
        }

        public HttpHeaderBuilder version(HttpVersion version) {
            this.version = version;
            return this;
        }

        public HttpHeaderBuilder field(HttpField field) {
            this.fields.add(field);
            return this;
        }

        public HttpHeader build() {
            return new HttpHeader(version, fields);
        }
    }

    /**
     * HttpHeaderBuilder 인스턴스를 반환하는 정적 팩토리 메서드
     * @return
     */
    public static HttpHeaderBuilder builder() {
        return new HttpHeaderBuilder();
    }
}

package webserver.httpheader.request.parser;

/**
 * HTTP Request의 Head 부분을 파싱하는 Parser를 생성하는 팩토리 인터페이스 구현체
 */
// TODO: 필요 없는 클래스, 추후 제거 및 객체 관계 간략화 필요
public class HttpRequestHeadParserFactoryImpl implements HttpRequestHeadParserFactory {

    @Override
    public HttpRequestHeadParser create(String rawRequestLine) {
        return new HttpRequestHeadParserImpl(rawRequestLine);
    }

}

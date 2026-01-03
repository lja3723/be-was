package webserver.httpheader.request.header;

import java.io.InputStream;
import webserver.httpheader.HttpHeaderFactory;
import webserver.httpheader.request.parser.HttpFieldParser;
import webserver.httpheader.request.parser.HttpRequestHeadParserFactory;

/**
 * 클라이언트 Request의 InputStream을 파싱하여 HttpRequestHeader 객체를 생성하는 팩토리 인터페이스
 */
//TODO: 이름을 Decoder로 변경 필요
public interface HttpRequestHeaderFactory extends HttpHeaderFactory {

    /**
     * InputStream을 파싱하여 HttpRequestHeader 객체를 생성
     * @param inputStream 클라이언트 Request의 InputStream
     * @param httpRequestHeadParserFactory
     * @param httpFieldParser
     * @return
     */
    HttpRequestHeader create(InputStream inputStream,
        HttpRequestHeadParserFactory httpRequestHeadParserFactory,
        HttpFieldParser httpFieldParser);
}

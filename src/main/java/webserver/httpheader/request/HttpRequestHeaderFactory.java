package webserver.httpheader.request;

import java.io.InputStream;
import webserver.httpheader.HttpHeaderFactory;
import webserver.httpheader.request.parser.HttpFieldParser;
import webserver.httpheader.request.parser.HttpRequestHeadParserFactory;

/*
 * HTTP request header stream을 parse하여 HttpRequestHeader 객체를 생성하는 팩토리 인터페이스
 */
public interface HttpRequestHeaderFactory extends HttpHeaderFactory {

    HttpRequestHeader create(InputStream inputStream,
        HttpRequestHeadParserFactory httpRequestHeadParserFactory,
        HttpFieldParser httpFieldParser);
}

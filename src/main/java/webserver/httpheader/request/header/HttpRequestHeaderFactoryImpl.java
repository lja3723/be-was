package webserver.httpheader.request.header;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Optional;
import webserver.httpheader.HttpHeader;
import webserver.httpheader.request.parser.HttpFieldParser;
import webserver.httpheader.request.parser.HttpRequestHeadParser;
import webserver.httpheader.request.parser.HttpRequestHeadParserFactory;

/**
 * 클라이언트 Request의 InputStream을 파싱하여 HttpRequestHeader 객체를 생성하는 팩토리 인터페이스 구현체
 */
public class HttpRequestHeaderFactoryImpl implements HttpRequestHeaderFactory{

    @Override
    public HttpRequestHeader create(InputStream inputStream,
        HttpRequestHeadParserFactory httpRequestHeadParserFactory, HttpFieldParser httpFieldParser) {

        try {
            // InputStream을 행 단위로 읽기 준비
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = Optional.ofNullable(reader.readLine())
                .orElseThrow(() -> new RuntimeException("Empty request"));

            // Request의 첫 line(Head 부분) 파싱
            HttpRequestHeadParser httpRequestHeadParser = httpRequestHeadParserFactory.create(line);
            HttpRequestHeader result = new HttpRequestHeader(
                new HttpHeader(
                    httpRequestHeadParser.getVersion(),
                    new ArrayList<>()),
                httpRequestHeadParser.getMethod(),
                httpRequestHeadParser.getPath()
            );

            // 나머지 필드 파싱
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                result.common().fields().add(httpFieldParser.parse(line));
            }

            return result;

        } catch (IOException e) {
            throw new RuntimeException("Error reading request", e);
        }
    }
}

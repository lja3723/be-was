package webserver.header;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Optional;
import webserver.header.parser.HttpFieldParser;
import webserver.header.parser.HttpRequestHeadParser;
import webserver.header.parser.HttpRequestHeadParserFactory;

/**
 * HTTP Request Header를 표현하는 Data Class
 */
public record HttpRequestHeader(HttpHeader common, HttpMethod method, String path) {

    /**
     * InputStream을 파싱하여 HttpRequestHeader 객체를 생성
     * @param inputStream 클라이언트 Request의 InputStream
     * @param httpRequestHeadParserFactory
     * @param httpFieldParser
     * @return 파싱된 HttpRequestHeader 객체
     */
    public static HttpRequestHeader decodeInputStream(InputStream inputStream,
                             HttpRequestHeadParserFactory httpRequestHeadParserFactory,
                             HttpFieldParser httpFieldParser) {
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

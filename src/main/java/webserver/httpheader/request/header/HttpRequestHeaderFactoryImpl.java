package webserver.httpheader.request.header;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import webserver.httpheader.request.parser.HttpFieldParser;
import webserver.httpheader.request.parser.HttpRequestHeadParser;
import webserver.httpheader.request.parser.HttpRequestHeadParserFactory;

// 구현 완료
public class HttpRequestHeaderFactoryImpl implements HttpRequestHeaderFactory{

    @Override
    public HttpRequestHeader create(InputStream inputStream,
        HttpRequestHeadParserFactory httpRequestHeadParserFactory, HttpFieldParser httpFieldParser) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line = reader.readLine();
            if (line == null) {
                throw new RuntimeException("Empty request");
            }
            HttpRequestHeadParser httpRequestHeadParser = httpRequestHeadParserFactory.create(line);

            HttpRequestHeader result = new HttpRequestHeader();
            result.method = httpRequestHeadParser.getMethod();
            result.path = httpRequestHeadParser.getPath();
            result.version = httpRequestHeadParser.getVersion();
            result.fields = new ArrayList<>();

            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                result.fields.add(httpFieldParser.parse(line));
            }

            return result;

        } catch (IOException e) {
            throw new RuntimeException("Error reading request", e);
        }
    }
}

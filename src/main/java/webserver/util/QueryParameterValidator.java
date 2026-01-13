package webserver.util;

import app.exception.BadRequestException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import webserver.http.QueryParameter;

/**
 * 쿼리 파라미터 유효성 검증기
 */
public class QueryParameterValidator {

    /**
     * 쿼리 스트링에서 필수 쿼리 파라미터들을 추출하고, 누락된 파라미터가 있으면 예외를 던진다.
     * @param rawQuery 원시 쿼리 스트링
     * @param requiredQueries 필수 쿼리 파라미터 키 목록
     * @return 필수 쿼리 파라미터 목록, requiredQueries 순서대로 반환됨
     */
    public static QueryParameter validate(String rawQuery, List<String> requiredQueries) {

        QueryParameter queryParameter = parseQuery(rawQuery);

        List<String> missingQueries = requiredQueries.stream()
            .filter(queryKey -> queryParameter.getValue(queryKey) == null)
            .toList();
        if (!missingQueries.isEmpty()) {
            String missingQueriesString = String.join(", ", missingQueries);
            throw new BadRequestException("Missing required query parameters: " + missingQueriesString);
        }

        return queryParameter;
    }

    public static QueryParameter parseQuery(String queryString) {
        try {
            URI uri = new URI("?" + queryString);
            return new QueryParameter(uri.getQuery());
        } catch (URISyntaxException e) {
            throw new BadRequestException("Invalid query string: " + queryString);
        }
    }
}

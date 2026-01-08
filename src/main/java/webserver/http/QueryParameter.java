package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class QueryParameter {

    private final Map<String, String> parameters;

    public QueryParameter(String queryString) {
        this.parameters = parseQueryString(queryString);
    }

    public Map<String, String> parseQueryString(String queryString) {
        String[] param = queryString.split("&");
        Map<String, String> result = new HashMap<>();
        for (String p : param) {
            String[] parts = p.split("=");
            if (parts.length == 2) {
                result.put(parts[0], parts[1]);
            } else if (parts.length == 1) {
                result.put(parts[0], "");
            }
        }
        return result;
    }

    public Optional<String> getParameter(String key) {
        return Optional.ofNullable(parameters.get(key));
    }
}

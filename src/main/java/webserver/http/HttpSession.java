package webserver.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Session ID로 세션을 구분하고, 각 세션마다 key-value 쌍으로 데이터를 저장하는 HttpSession 클래스
 */
public class HttpSession {

    private final Map<String, Map<String, String>> sessions;

    public HttpSession() {
        this.sessions = new HashMap<>();
    }

    /**
     * 주어진 세션 ID에 해당하는 세션 데이터를 반환.
     * 세션이 존재하지 않으면 새로 생성하여 반환.
     *
     * @param sessionId 세션 ID
     * @return 세션 데이터 맵
     */
    public Map<String, String> getSession(String sessionId) {
        return sessions.computeIfAbsent(sessionId, k -> new HashMap<>());
    }

    /**
     * 주어진 세션 ID와 키에 해당하는 값을 반환.
     *
     * @param sessionId 세션 ID
     * @param key       데이터 키
     * @return 데이터 값, 키가 존재하지 않으면 null 반환
     */
    public String getAttribute(String sessionId, String key) {
        Map<String, String> session = getSession(sessionId);
        return session.get(key);
    }

    /**
     * 주어진 세션 ID와 키에 값을 설정.
     *
     * @param sessionId 세션 ID
     * @param key       데이터 키
     * @param value     데이터 값
     */
    public void setAttribute(String sessionId, String key, String value) {
        Map<String, String> session = getSession(sessionId);
        session.put(key, value);
    }
}

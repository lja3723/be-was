package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Session ID로 세션을 구분하고, 각 세션마다 key-value 쌍으로 데이터를 저장하는 HttpSession 클래스
 */
public class HttpSession {

    private final Map<String, Map<String, String>> sessions;

    public HttpSession() {
        this.sessions = new HashMap<>();
    }

    /**
     * 새로운 세션을 생성하고 세션 ID를 반환.
     *
     * @return 새로 생성된 세션 ID
     */
    public synchronized String getNewSession() {
        String sessionId;
        do {
            sessionId = UUID.randomUUID().toString();
        } while (sessions.containsKey(sessionId));

        sessions.put(sessionId, new HashMap<>());
        return sessionId;
    }

    /**
     * 주어진 세션 ID에 해당하는 세션 데이터를 반환.
     * 세션이 존재하지 않으면 null
     *
     * @param sessionId 세션 ID
     * @return 세션 데이터 맵
     */
    public Map<String, String> getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    /**
     * 주어진 세션 ID와 키에 해당하는 값을 반환.
     *
     * @param sessionId 세션 ID
     * @param key       데이터 키
     * @return 데이터 값, 키가 존재하지 않으면 null 반환
     */
    public String getAttribute(String sessionId, String key) {
        return Optional.ofNullable(getSession(sessionId))
            .map(session -> session.get(key))
            .orElse(null);
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
        if (session != null) {
            session.put(key, value);
        }
    }
}

package app.db;

import app.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * userId를 키로 {@link User} 객체를 저장하는 단순한 In-Memory Database
 */
public class UserDatabase {
    private static Map<String, User> users = new HashMap<>();

    /**
     * 새로운 사용자를 데이터베이스에 추가
     *
     * @param user 추가할 {@link User} 객체
     */
    public static void addUser(User user) {
        users.put(user.userId(), user);
    }

    /**
     * userId로 {}
     * @param userId 검색할 사용자 ID
     * @return {@link User} 객체 또는 null
     */
    public static User findUserById(String userId) {
        return users.get(userId);
    }

    /**
     * 데이터베이스에 저장된 모든 사용자 반환
     *
     * @return 모든 {@link User} 객체의 컬렉션
     */
    public static Collection<User> findAll() {
        return users.values();
    }
}

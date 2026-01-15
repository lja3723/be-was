package app.db;

import app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import _support.UserConstants;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

// 테스트 대상 객체인 Database의 private static 필드 접근을 위하여 리플렉션 API를 활용하였습니다.
// 리플렉션 API의 구체적인 사용방법은 AI를 참고하였습니다.
class UserDatabaseTest {

    // Database 객체의 내부 private static 필드 핸들
    private Map<String, User> users;

    @BeforeEach
    void setUp() {
        initializeUsersField();
        users = getUsersFieldHandle();
        putDummyUsers();
    }

    private Field getUnlockedUsersField() {
        // private static field인 "users"의 Field 정보를 리플렉션으로 가져옴
        try {
            Class<UserDatabase> clazz = UserDatabase.class;
            Field result = clazz.getDeclaredField("users");
            result.setAccessible(true);
            return result;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeUsersField() {
        // private static field인 "users"에 새 객체 주입
        try {
            Field usersField = getUnlockedUsersField();
            usersField.set(null, new HashMap<String, User>());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // Field 객체의 get 메서드 호출값을 형변환시 경고를 제거하기 위함
    // instanceof는 제네릭 정보까지 확인이 불가능하여 사용하지 않았음
    @SuppressWarnings("unchecked")
    private Map<String, User> getUsersFieldHandle() {
        // private static field인 "users"에 대한 접근 핸들을 반환
        try {
            Field usersField = getUnlockedUsersField();
            return (Map<String, User>)usersField.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void putDummyUsers() {
        // users에 테스트용 더미 튜플을 추가
        // 준비된 User constant보다 1개 모자란 수로 초기화
        for (int i = 0; i < UserConstants.NUM_OF_USERS - 1; i++) {
            users.put(UserConstants.USERS_ID[i], UserConstants.getUserOf(i));
        }
    }

    @Test
    void addUser() {
        assertEquals(UserConstants.NUM_OF_USERS - 1, users.size());

        User toAdd = UserConstants.getUserOf(UserConstants.NUM_OF_USERS - 1);
        UserDatabase.addUser(toAdd);

        assertEquals(UserConstants.NUM_OF_USERS, users.size());
        for (int i = 0; i < UserConstants.NUM_OF_USERS; i++) {
            String userId = UserConstants.USERS_ID[i];
            assertEquals(UserConstants.USERS_ID[i], users.get(userId).getUserId());
            assertEquals(UserConstants.USERS_PASSWORD[i], users.get(userId).getPassword());
            assertEquals(UserConstants.USERS_NAME[i], users.get(userId).getName());
        }
    }

    @Test
    void findUserById() {
        for (int i = 0; i < UserConstants.NUM_OF_USERS - 1; i++) {
            String userId = UserConstants.USERS_ID[i];

            User user = UserDatabase.findUserById(userId);

            assertEquals(UserConstants.USERS_ID[i], user.getUserId());
            assertEquals(UserConstants.USERS_PASSWORD[i], user.getPassword());
            assertEquals(UserConstants.USERS_NAME[i], user.getName());
        }
    }

    @Test
    void findAll() {
        Collection<User> found = UserDatabase.findAll();

        assertEquals(UserConstants.NUM_OF_USERS - 1, found.size());

        for(var user: UserDatabase.findAll()) {
            assertTrue(users.containsValue(user));
        }
    }
}
package app.model;

/**
 * <p>사용자 정보를 나타내는 모델 클래스</p>
 */
// TODO: {@code record} class로 변경 고려
public class User {

    private final String userId;
    private final String password;
    private final String name;

    public User(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + "]";
    }
}

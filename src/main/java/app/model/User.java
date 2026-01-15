package app.model;

/**
 * <p>사용자 정보를 나타내는 모델 클래스</p>
 */
public record User(String userId, String password, String name) {

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + "]";
    }
}

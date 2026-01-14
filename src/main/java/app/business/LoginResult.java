package app.business;

/**
 * 로그인 결과를 나타내는 열거형
 */
public enum LoginResult {
    SUCCESS("success"),
    USER_NOT_FOUND("user_not_found"),
    PASSWORD_MISMATCH("password_mismatch");

    private final String value;

    LoginResult(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static LoginResult fromValue(String value) {
        for (LoginResult result : LoginResult.values()) {
            if (result.getValue().equals(value)) {
                return result;
            }
        }
        throw new IllegalArgumentException("Unknown LoginResult value: " + value);
    }
}

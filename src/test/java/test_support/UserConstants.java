package test_support;

import model.User;

// 해당 constant values는 AI로 생성되었음
public class UserConstants {

    public static final int NUM_OF_USERS = 3;

    public static final String[] USERS_ID = {
            "blueTiger92", "fastEagle17", "quietRiver63"
    };

    public static final String[] USERS_PASSWORD = {
            "SunnyDay_45", "MoonLight88", "GreenForest9"
    };

    public static final String[] USERS_NAME = {
            "Alice", "Bob", "Charlie"
    };

    public static final String[] USERS_EMAIL = {
            "alice@example.com", "bob@example.com", "charlie@example.com"
    };

    public static User getUserOf(int idx) {
        if (idx >= NUM_OF_USERS) {
            throw new ArrayIndexOutOfBoundsException(idx);
        }

        return new User(USERS_ID[idx], USERS_PASSWORD[idx], USERS_NAME[idx], USERS_EMAIL[idx]);
    }
}

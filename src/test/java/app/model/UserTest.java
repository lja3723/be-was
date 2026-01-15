package app.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import _support.UserConstants;

import static org.junit.jupiter.api.Assertions.*;
import static _support.UserConstants.*;

class UserTest {

    private User[] users;


    @BeforeEach
    void setUp() {
        users = new User[NUM_OF_USERS];

        for (int i = 0; i < NUM_OF_USERS; i++) {
            users[i] = UserConstants.getUserOf(i);
        }
    }

    @Test
    void getUserId() {
        for (int i = 0; i < NUM_OF_USERS; i++) {
            assertEquals(USERS_ID[i], users[i].userId());
        }
    }

    @Test
    void getPassword() {
        for (int i = 0; i < NUM_OF_USERS; i++) {
            assertEquals(USERS_PASSWORD[i], users[i].password());
        }
    }

    @Test
    void getName() {
        for (int i = 0; i < NUM_OF_USERS; i++) {
            assertEquals(USERS_NAME[i], users[i].name());
        }
    }

    @Test
    void testToString() {
        for (int i = 0; i < NUM_OF_USERS; i++) {
            String expected = "User [" +
                    "userId=" + USERS_ID[i] +
                    ", password=" + USERS_PASSWORD[i] +
                    ", name=" + USERS_NAME[i] + "]";

            assertEquals(expected, users[i].toString());
        }
    }
}
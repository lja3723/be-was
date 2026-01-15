package app.business.impl;

import app.business.LoginResult;
import app.business.UserBusiness;
import app.db.adapter.DatabaseAdapter;
import app.exception.BadRequestException;
import app.model.User;
import java.util.Optional;

public class UserBusinessImpl implements UserBusiness {

    private final DatabaseAdapter<User, String> userDatabaseAdapter;

    public UserBusinessImpl(DatabaseAdapter<User, String> userDatabaseAdapter) {
        this.userDatabaseAdapter = userDatabaseAdapter;
    }

    @Override
    public void register(String userId, String password, String name) {
        synchronized (userDatabaseAdapter) {
            if (userDatabaseAdapter.findById(userId).isPresent()) {
                throw new BadRequestException("User ID already exists: " + userId);
            }
            userDatabaseAdapter.add(new User(userId, password, name));
        }
    }

    @Override
    public LoginResult login(String userId, String password) {
        Optional<User> userOpt = userDatabaseAdapter.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return LoginResult.SUCCESS;
            }
            return LoginResult.PASSWORD_MISMATCH;
        }
        return LoginResult.USER_NOT_FOUND;
    }

    @Override
    public Optional<User> findById(String userId) {
        return userDatabaseAdapter.findById(userId);
    }
}

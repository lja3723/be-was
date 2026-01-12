package app.business.impl;

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
    public void register(String userId, String password, String name, String email) {
        synchronized (userDatabaseAdapter) {
            if (userDatabaseAdapter.findById(userId).isPresent()) {
                throw new BadRequestException("User ID already exists: " + userId);
            }
            userDatabaseAdapter.add(new User(userId, password, name, email));
        }
    }

    @Override
    public Optional<User> findById(String userId) {
        return userDatabaseAdapter.findById(userId);
    }
}

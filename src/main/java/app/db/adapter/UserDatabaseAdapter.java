package app.db.adapter;

import app.db.UserDatabase;
import app.model.User;
import java.util.Collection;
import java.util.Optional;

public class UserDatabaseAdapter implements DatabaseAdapter<User, String> {

    @Override
    public void add(User user) {
        synchronized(UserDatabase.class) {
            UserDatabase.addUser(user);
        }
    }

    @Override
    public Optional<User> findById(String key) {
        return Optional.ofNullable(UserDatabase.findUserById(key));
    }

    @Override
    public Collection<User> findAll() {
        return UserDatabase.findAll();
    }
}

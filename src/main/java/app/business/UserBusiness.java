package app.business;

import app.model.User;
import java.util.Optional;

public interface UserBusiness {

    void register(String userId, String password, String name, String email);
    Optional<User> findById(String userId);
}

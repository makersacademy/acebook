package models;

import java.util.UUID;

public interface UserModel {
    UUID userSignup(String first_name, String last_name, String email, String password);
}

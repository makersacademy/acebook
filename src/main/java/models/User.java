package models;

import lombok.Data;

import java.util.UUID;

@Data
public class User {
    UUID user_id;
    String first_name;
    String last_name;
    String password;
    String email;
}

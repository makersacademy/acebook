package models;

import lombok.Data;

import java.util.UUID;

@Data
public class AddUser {
     private UUID user_id;
     private String user_name;
     private String password;

    public AddUser(UUID user_id, String user_name, String password) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
    }
}

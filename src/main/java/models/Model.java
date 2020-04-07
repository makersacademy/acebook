package models;


import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface Model {
    UUID createPost(String title, String content, Timestamp post_date);
    List getAllPosts();
    UUID userSignup(String first_name, String last_name, String email, String password);

//    void likePosts(UUID id);
}



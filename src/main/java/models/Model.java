package models;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface Model {
    UUID createPost(String title, String content, Timestamp post_date);
    List getAllPosts();
}



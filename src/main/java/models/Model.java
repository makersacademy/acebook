package models;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface Model {

    void createPost(String content, Timestamp time);

    List getAllPosts();
}



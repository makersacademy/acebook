package models;


import java.util.List;

public interface Model {

    void createPost(String content, String time);

    List getAllPosts();
}



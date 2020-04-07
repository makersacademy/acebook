package models;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class Sql2oModel implements Model, UserModel {

    private Sql2o sql2o;

    public Sql2oModel(Sql2o sql2o) {
        this.sql2o = sql2o;

    }

    @Override
    public UUID createPost(String title, String content, Timestamp post_date) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID postUuid = UUID.randomUUID();

            conn.createQuery("insert into posts(post_id, title, content, post_date) VALUES (:post_id, :title, :content, :post_date)")
                    .addParameter("post_id", postUuid)
                    .addParameter("title", title)
                    .addParameter("content", content)
                    .addParameter("post_date", post_date)
                    .executeUpdate();
            conn.commit();
            return postUuid;
        }
    }

    @Override
    public List<Post> getAllPosts() {
        try (Connection conn = sql2o.open()) {
            List<Post> items = conn.createQuery("select * from posts")
                    .executeAndFetch(Post.class);

            return items;
        }

    }

    @Override
    public UUID userSignup(String first_name, String last_name, String email, String password) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID userUuid = UUID.randomUUID();

            conn.createQuery("insert into users(user_id, first_name, last_name, email, password) VALUES (:user_id, :first_name, :last_name, :email, :password)")
                    .addParameter("user_id", userUuid)
                    .addParameter("first_name", "example first_name")
                    .addParameter("last_name", "example last_name")
                    .addParameter("email", "example@gmail.com")
                    .addParameter("password", "example" )
                    .executeUpdate();
            conn.commit();
            return userUuid;
        }
    }

//    @Override
//    public void likePosts(UUID id) {
//
//
//    }

    }

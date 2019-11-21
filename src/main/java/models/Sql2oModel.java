package models;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class Sql2oModel implements Model, UserModel {

    private Sql2o sql2o;

    public Sql2oModel(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public UUID createPost(String title, String content) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID postUuid = UUID.randomUUID();
            conn.createQuery("insert into posts(post_id, title, content, time, likes) VALUES (:post_id, :title, :content, CURRENT_TIMESTAMP, 0)")
                    .addParameter("post_id", postUuid)
                    .addParameter("title", title)
                    .addParameter("content", content)
                    .executeUpdate();
            conn.commit();
            return postUuid;
        }
    }

    @Override
    public List<Post> getAllPosts() {
        try (Connection conn = sql2o.open()) {
            List<Post> posts = conn.createQuery("SELECT * FROM posts ORDER BY time DESC")
                    .executeAndFetch(Post.class);
            return posts;
        }
    }

    @Override
    public UUID createUser(String first_name, String last_name, String password, String email) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID userUuid = UUID.randomUUID();
            conn.createQuery("insert into users(id, first_name, last_name, email, password) VALUES (:id, :first_name, :last_name, :email, :password)")
                    .addParameter("id", userUuid)
                    .addParameter("first_name", first_name)
                    .addParameter("last_name", last_name)
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .executeUpdate();
            conn.commit();
            return userUuid;
        }
    }

    @Override
    public void addLike(String id) {
        try (Connection conn = sql2o.open()) {
            List<Integer> likecount = conn.createQuery("SELECT likes FROM posts WHERE post_id =:id")
                    .addParameter("id", id)
                    .executeAndFetch(Integer.class);
            Integer i;
            String likes;
            i = Integer.parseInt(String.valueOf(likecount.get(0)));
            i += 1;
//            likes = String.valueOf(i);
            conn.createQuery("UPDATE posts SET likes = :i WHERE post_id =:id")
                    .addParameter("i", i)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    @Override
    public String gettingComments(UUID post_id) {
        try (Connection conn = sql2o.open()) {
            List<String> comments = conn.createQuery("SELECT comment FROM comments WHERE post_id =:id")
                    .addParameter("id", post_id.toString())
                    .executeAndFetch(String.class);
            String commentsConvert;
            commentsConvert = String.valueOf(comments);
            return commentsConvert;
        }
    }

    @Override
    public void postComment(String comment, String post_id) {
        try (Connection conn = sql2o.beginTransaction()) {

            UUID commentUuid = UUID.randomUUID();
            conn.createQuery("insert into comments(comment_id, post_id, comment) VALUES (:comment_id, :post_id, :comment)")
                    .addParameter("comment_id", commentUuid)
                    .addParameter("post_id", post_id)
                    .addParameter("comment", comment)
                    .executeUpdate();
            conn.commit();
        }
    }

    @Override
    public List<Comment> getAllComments() {
        try (Connection conn = sql2o.open()) {
            List<Comment> comments = conn.createQuery("SELECT post_id, comment FROM comments")
                    .executeAndFetch(Comment.class);
            return comments;
        }
    }

    public Boolean verifyUser(String email, String password) {
        boolean correct_password = false;

        try (Connection conn = sql2o.open()) {
            List<User> user = conn.createQuery("select password from users where email = :email")
                    .addParameter("email", email)
                    .executeAndFetch(User.class);
            password = "[User(id=null, first_name=null, last_name=null, email=null, password="+password+")]";
            if(user.toString().equals(password)){
                correct_password = true;
            };
        }
        return correct_password;
    }
}


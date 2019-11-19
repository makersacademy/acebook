package models;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.UUID;

public class Sql2oModel implements Model {

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
    public void addLike(String id) {
        try (Connection conn = sql2o.open()) {
            List<Integer> likecount = conn.createQuery("SELECT likes FROM posts WHERE post_id =:id")
                    .addParameter("id", id)
                    .executeAndFetch(Integer.class);
            Integer i;
            String likes;
            System.out.println(likecount.get(0));
            i = Integer.parseInt(String.valueOf(likecount.get(0)));
            i += 1;
            likes = String.valueOf(i);
            conn.createQuery("UPDATE posts SET likes = :i WHERE post_id =:id")
                    .addParameter("i", i)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

}
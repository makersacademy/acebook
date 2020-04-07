package models;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class Sql2oModel implements Model {

    private Sql2o sql2o;

    public Sql2oModel(Sql2o sql2o) {
        this.sql2o = sql2o;

    }

    @Override
    public void createPost(String content, Timestamp time) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID postUuid = UUID.randomUUID();
            conn.createQuery("insert into post(post_id, content, time) VALUES (:post_id, :content, :time)")
                    .addParameter("post_id", postUuid)
                    .addParameter("content", content)
                    .addParameter("time", time)
                    .executeUpdate();
            conn.commit();
        }
    }

    @Override
    public List<Post> getAllPosts() {
        //TODO - implement this
        return null;
    }

}
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
        //TODO - implement this
        return null;
    }

    @Override
    public List<Post> getAllPosts() {
        //TODO - implement this
        return null;
    }

    @Override
    public void addUser(String user_name, String password) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID personUuid = UUID.randomUUID();
            conn.createQuery("insert into person(user_id, user_name, password) VALUES (:user_id, :user_name, :password)")
                    .addParameter("user_id", personUuid)
                    .addParameter("user_name", user_name)
                    .addParameter("password", password)
                    .executeUpdate();
            conn.commit();

        }
    }

}
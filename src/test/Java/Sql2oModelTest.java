import models.Model;
import models.Post;
import models.Sql2oModel;
import org.apache.log4j.BasicConfigurator;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class Sql2oModelTest {

    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/" + "acebook_test",
            null, null, new PostgresQuirks() {
        {
            // make sure we use default UUID converter.
            converters.put(UUID.class, new UUIDConverter());
        }
    });

    UUID id = UUID.fromString("49921d6e-e210-4f68-ad7a-afac266278cb");
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    @BeforeAll
    static void setUpClass() {
        BasicConfigurator.configure();
        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/acebook_test", null, null).load();
        flyway.migrate();

    }

    @BeforeEach
    void setUp() {
        Connection conn = sql2o.beginTransaction();
        conn.createQuery("insert into posts(post_id, title, content, time, likes) VALUES (:post_id, :title, :content, :time, 0)")
                .addParameter("post_id", id)
                .addParameter("title", "example title")
                .addParameter("content", "example content")
                .addParameter("time", timestamp)
                .executeUpdate();
        conn.commit();
    }

    @AfterEach
    void tearDown() {
        Connection conn = sql2o.beginTransaction();
        conn.createQuery("TRUNCATE TABLE posts")
                .executeUpdate();
        conn.commit();
    }

    @org.junit.jupiter.api.Test
    void createPost() {
        Connection conn = sql2o.beginTransaction();
        conn.createQuery("TRUNCATE TABLE posts")
                .executeUpdate();
        Model model = new Sql2oModel(sql2o);
        conn.createQuery("insert into posts(post_id, title, content, time, likes) VALUES (:post_id, 'Hello guys', 'good morning im having a swell day', :timestamp, 0)")
                .addParameter("post_id", id)
                .addParameter("timestamp", timestamp)
                .executeUpdate();
        conn.commit();
        List<Post> posts = new ArrayList<Post>();
        posts.add(new Post(id, "Hello guys", "good morning im having a swell day", timestamp, 0));
        assertEquals(model.getAllPosts(), posts);
    }

    @org.junit.jupiter.api.Test
    void getAllPosts() {
        Model model = new Sql2oModel(sql2o);
        List<Post> posts = new ArrayList<Post>();
        posts.add(new Post(id, "example title", "example content", timestamp, 0));
        assertEquals(model.getAllPosts(), posts);
    }
}
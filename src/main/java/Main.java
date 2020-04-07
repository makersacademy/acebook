import models.Model;
import models.Sql2oModel;
import org.flywaydb.core.Flyway;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;
import spark.ModelAndView;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

import static spark.Spark.get;

public class Main {

    public static void main(String[] args) {
        String dbName = "acebook";
        for(String a:args) {
            dbName = a;
        }
        System.out.println(dbName);
        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/"+dbName, null, null).load();
        flyway.migrate();

        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/" + dbName, null, null, new PostgresQuirks() {
            {
                // make sure we use default UUID converter.
                converters.put(UUID.class, new UUIDConverter());
            }
        });

        Model model = new Sql2oModel(sql2o);


        get("/", (req, res) -> "Hello World");


        get("/posts", (req, res) -> {
if(model.getAllPosts().size() == 0) {
    UUID id = model.createPost("Holiday", "Celebrate", new Timestamp(120,3,7,9,45,30,0));
}

        HashMap posts = new HashMap();
        posts.put("posts", model.getAllPosts());

            return new ModelAndView(posts, "templates/posts.vtl");
        }, new VelocityTemplateEngine());

//        post("/posts/new", (req, res) -> {
//            String title = req.queryParams("title");
//            String content = req.queryParams("content");
//            Timestamp post_date = req.queryParams(new Timestamp(120,3,7,9,45,30,0));
//            model.createPost(title, content, post_date);
//            res.redirect("/posts");
//            return null;
//        });
    }
    }



import models.Model;
import models.Sql2oModel;
import models.UserModel;

import org.apache.log4j.BasicConfigurator;
import org.flywaydb.core.Flyway;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.UUID;


import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        BasicConfigurator.configure();

        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/acebook", null, null).load();
        flyway.migrate();

        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/" + "acebook", null, null, new PostgresQuirks() {
            {
                // make sure we use default UUID converter.
                converters.put(UUID.class, new UUIDConverter());
            }
        });

        Model model = new Sql2oModel(sql2o);
        UserModel userModel = new Sql2oModel(sql2o);

        get("/posts", (req, res) -> {
            HashMap posts = new HashMap();
            posts.put("posts", model.getAllPosts());

            return new ModelAndView(posts, "templates/posts.vtl");
        }, new VelocityTemplateEngine());


        get("/newpost", (req, res) -> {
            return new ModelAndView(new HashMap<>(),"templates/newPost.vtl");
        }, new VelocityTemplateEngine());

        post("/newpost", (request, response) -> {
            String title;
            String content;
            title = request.queryParams("title");
            content = request.queryParams("content");
            response.redirect("/posts");
            UUID id = model.createPost(title, content);
            return null;
        });

        post("/likepost", (request, response) -> {
            String id;
            id = request.queryParams("id");
            model.addLike(id);
            response.redirect("/posts");
            return null;
        });

        get("/", (req, res) -> {
            HashMap users = new HashMap();
            return new ModelAndView(users, "templates/sign-in.vtl");
        }, new VelocityTemplateEngine());

        get("/sign-up", (req, res) -> {
            HashMap users = new HashMap();
            return new ModelAndView(users, "templates/sign-up.vtl");
        }, new VelocityTemplateEngine());

        post("/sign-up", (req,res) -> {
            String first_name = req.queryParams("first_name");
            String last_name = req.queryParams("last_name");
            String password = req.queryParams("password");
            String email = req.queryParams("email");

            userModel.createUser(first_name, last_name, password, email);

            res.redirect("/posts");

            return null;

        });
    };
}

package models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import lombok.Data;
@Data
public class Post {
    private UUID post_id;
    private String content;
    private Timestamp time;

    public Post(UUID post_id, String content, Timestamp time) {
        this.post_id = post_id;
        this.content = content;
        this.time = time;
    }

}

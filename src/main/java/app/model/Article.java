package app.model;

import java.util.List;

public record Article(
    Integer id,
    String authorUserId,
    String title,
    String content,
    List<Image> images,
    List<Comment> comments,
    Integer likes)
{

}

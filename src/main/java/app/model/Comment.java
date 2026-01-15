package app.model;

public record Comment(Integer id, String authorUserId, Integer articleId, String content) {

}

package app.db;

import app.model.Article;
import java.util.HashMap;
import java.util.Map;

public class ArticleDatabase {

    private static final Map<Integer, Article> articles = new HashMap<>();

    public static void addArticle(Article article) {
        articles.put(article.id(), article);
    }

    public static Article findArticleById(Integer id) {
        return articles.get(id);
    }

    public static Map<Integer, Article> findAll() {
        return articles;
    }

}

package app.handler.article;

import app.business.ArticleBusiness;
import app.handler.ApplicationHandler;
import app.handler.response.JsonResponse;
import app.model.Article;
import java.util.Map;
import java.util.Optional;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class ArticleHandler extends ApplicationHandler {

    private final ArticleBusiness articleBusiness;

    public ArticleHandler(ArticleBusiness articleBusiness) {
        super(HttpMethod.GET, "/api/article");
        this.articleBusiness = articleBusiness;
    }

    @Override
    public HttpResponse handleResponse(HttpRequest httpRequest) {
        Optional<Article> newest = articleBusiness.getNewestArticle();
        return newest.map(article -> JsonResponse.ok(Map.of(
            "status", "success",
            "data", article
        ))).orElse(JsonResponse.ok(Map.of(
            "status", "no_newest_found"
        )));
    }
}

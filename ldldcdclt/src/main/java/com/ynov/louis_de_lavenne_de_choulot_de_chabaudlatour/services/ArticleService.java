package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Optional<Article> getArticleById(Long id);

    List<Article> getAllArticles();

    Article saveArticle(Article Article);

    void deleteArticleByID(Long id);
}

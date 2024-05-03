package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.servicesImplem;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Article;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.repositories.ArticleRepo;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleImplem implements ArticleService {
    @Autowired
    ArticleRepo ArticleRepo;

    @Override
    public Article saveArticle(Article article) {
        return ArticleRepo.save(article);
    }

    @Override
    public Optional<Article> getArticleById (Long id){
        return ArticleRepo.findById(id);
    }

    @Override
    public List<Article> getAllArticles() {
        return ArticleRepo.findAll();
    }
    
    @Override
    public void deleteArticleByID(Long id) {
        Optional<Article> existingArticle = ArticleRepo.findById(id);
        if (existingArticle.isPresent()) {
            ArticleRepo.deleteById(id);
        } else {
            throw new RuntimeException("Article not found with id: " + id);
        }
    }
}

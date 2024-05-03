package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.repositories;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Article;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.User;

import jakarta.persistence.ManyToOne;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Long> {
    Article save (Article article);
    Optional<Article> findById(Long id);
    List<Article> findAll();
    void deleteById(Long id);
}

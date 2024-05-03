package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.controllers;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Article;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.User;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.security.JwtService;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services.ArticleService;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services.UserService;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ResponseHeader;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
@Tag(name = "APIs", description = "CRUD stock and articles")
public class ApiController {
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;

    @GetMapping("products")
    public ResponseEntity<?> getAllArticle(@RequestHeader ("Authorization") String token) {
        
        String mail = jwtService.extractUsername(token);
        Optional<User> user = userService.getUserByEmail(mail);
        if (user.isEmpty()) {
            return new ResponseEntity<>("User not found with mail in token '" +token+"'" , HttpStatus.NOT_FOUND);
        }
        List<Article> articles = user.get().getArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("products/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable("id") Long id) {
        Optional<Article> article = articleService.getArticleById(id);
        if (article.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>( article.get(), HttpStatus.OK );
    }

    @PostMapping("products")
    public ResponseEntity<?> createArticle (@RequestBody Article article, @RequestHeader ("Authorization") String token) {
        
        String mail = jwtService.extractUsername(token);
        Optional<User> user = userService.getUserByEmail(mail);
        if (user.isEmpty()) {
            return new ResponseEntity<>("User not found with mail in token '" +token+"'" , HttpStatus.NOT_FOUND);
        }

        
        articleService.saveArticle(article);
        user.get().getArticles().add(article);
        
        return new ResponseEntity<>(
                userService.saveUser(user.get()),
                HttpStatus.CREATED
        );
    }

    @PutMapping("products/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable("id") Long id, @RequestBody Article updatedArticle) {
        Optional<Article> existingArticle = articleService.getArticleById(id);
        if (existingArticle.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                articleService.saveArticle(updatedArticle),
                HttpStatus.OK
        );
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable("id") Long id) {
        Optional<Article> existingArticle = articleService.getArticleById(id);
        if (existingArticle.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        articleService.deleteArticleByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("stock/entry")
    public ResponseEntity<?> createStockEntry(@RequestBody Map<String, String> request) {
        String productId = request.get("productId");
        int quantity = Integer.parseInt(request.get("quantity"));
        Optional<Article> existingArticle = articleService.getArticleById(Long.parseLong(productId));
        if (existingArticle.isEmpty()) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        existingArticle.get().setQuantity(quantity+existingArticle.get().getQuantity());
        return new ResponseEntity<>(articleService.saveArticle(existingArticle.get()), HttpStatus.OK);
    }

    @PostMapping("stock/exit")
    public ResponseEntity<?> createStockExit(@RequestBody Map<String, String> request) {
        String productId = request.get("productId");
        int quantity = Integer.parseInt(request.get("quantity"));
        Optional<Article> existingArticle = articleService.getArticleById(Long.parseLong(productId));
        if (existingArticle.isEmpty()) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        int currentQuantity = existingArticle.get().getQuantity();
        if (currentQuantity < quantity) {
            return new ResponseEntity<>("Insufficient stock", HttpStatus.BAD_REQUEST);
        }
        existingArticle.get().setQuantity(currentQuantity - quantity);
        return new ResponseEntity<>(articleService.saveArticle(existingArticle.get()), HttpStatus.OK);
    }

    @GetMapping("stock/report/inventory")
    public ResponseEntity<Map<String, Integer>> getInventoryReport(@RequestHeader ("Authorization") String token) {
        
        String mail = jwtService.extractUsername(token);
        User user = userService.getUserByEmail(mail).get();
        List<Article> articles = user.getArticles();
        
        //map product name and quantity
        Map<String, Integer> inventoryReport = articles.stream().collect(Collectors.toMap(
                Article::getName,
                Article::getQuantity
        ));
        return new ResponseEntity<>(inventoryReport, HttpStatus.OK);
    }
}

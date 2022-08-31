package com.oyk.exam.jpaBoard.Dao;

import com.oyk.exam.jpaBoard.Domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    
}

package com.oyk.exam.jpaBoard.Controller;

import com.oyk.exam.jpaBoard.Dao.ArticleRepository;
import com.oyk.exam.jpaBoard.Domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usr/article")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping("/list")
    @ResponseBody
    public List<Article> showList() {
        return articleRepository.findAll();
    }

    @RequestMapping("/detail")
    @ResponseBody
    public Article showDetail(long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article.get();
    }

    @RequestMapping("/doModify")
    @ResponseBody
    public String doModify(long id, String body, String title) {

        if(title == null) {
            return "수정할 제목을 입력해주세요.";
        }

        if(body == null) {
            return "수정할 내용을 입력해주세요.";
        }

        Article article = articleRepository.findById(id).get();
        article.setBody(body);
        article.setTitle(title);
        article.setUpdateDate(LocalDateTime.now());

        articleRepository.save(article);

        return "%d번 게시물이 수정되었습니다.".formatted(id);
    }

    @RequestMapping("/doDelete")
    @ResponseBody
    public String doDelete(long id) {
        articleRepository.deleteById(id);

        return "%d번 게시물이 삭제되었습니다.".formatted(id);
    }
}

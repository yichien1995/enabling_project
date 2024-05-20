package tw.appworks.school.yichien.enabling.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.ArticleForm;
import tw.appworks.school.yichien.enabling.service.webpage.ArticleService;

import java.util.HashMap;
import java.util.Map;

public abstract class ArticleApiController {

    protected final ArticleService articleService;

    protected ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<?> saveArticle(@PathVariable String domain,
                                         @RequestParam(required = false, defaultValue = "1") String draft,
                                         @RequestParam(required = false) String id,
                                         @ModelAttribute ArticleForm articleForm) {
        int draftValue = Integer.parseInt(draft);

        if (id != null) {
            int idValue = Integer.parseInt(id);
            articleService.updateArticle(idValue, draftValue, 0, articleForm);
        } else {
            articleService.saveNewArticle(domain, draftValue, 0, articleForm);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("success", "Save article successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/preview")
    public ResponseEntity<?> savePreviewArticle(@PathVariable String domain,
                                                @RequestParam(required = false) String id,
                                                @ModelAttribute ArticleForm articleForm) {
        if (id == null) {
            articleService.savePreviewArticlePage(domain, 1, 1, articleForm);
        } else {
            int idValue = Integer.parseInt(id);
            articleService.previewExistArticle(idValue, domain, 1, 1, articleForm);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("success", "Save preview article successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteArticle(
            @PathVariable String id, @PathVariable String domain) {
        int idValue = Integer.parseInt(id);
        articleService.deleteArticle(idValue);
        return ResponseEntity.ok("Delete article id: " + id);
    }
}

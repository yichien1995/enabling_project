package tw.appworks.school.yichien.enabling.controller.admin.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.appworks.school.yichien.enabling.controller.ArticleApiController;
import tw.appworks.school.yichien.enabling.service.webpage.ArticleService;


@RestController
@RequestMapping("/api/1.0/admin/{domain}/webpage/article")
public class AdminArticleApiController extends ArticleApiController {
    public AdminArticleApiController(ArticleService articleService) {
        super(articleService);
    }

}

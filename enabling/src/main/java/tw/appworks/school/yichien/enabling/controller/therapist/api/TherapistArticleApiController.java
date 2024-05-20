package tw.appworks.school.yichien.enabling.controller.therapist.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.appworks.school.yichien.enabling.controller.ArticleApiController;
import tw.appworks.school.yichien.enabling.service.webpage.ArticleService;

@RestController
@RequestMapping("/api/1.0/therapist/{domain}/webpage/article")
public class TherapistArticleApiController extends ArticleApiController {
    public TherapistArticleApiController(ArticleService articleService) {
        super(articleService);
    }

}

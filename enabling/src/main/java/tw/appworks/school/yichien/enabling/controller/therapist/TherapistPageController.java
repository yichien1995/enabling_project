package tw.appworks.school.yichien.enabling.controller.therapist;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.service.ClientService;
import tw.appworks.school.yichien.enabling.service.TherapistService;
import tw.appworks.school.yichien.enabling.service.impl.SessionServiceImpl;
import tw.appworks.school.yichien.enabling.service.webpage.ArticleService;
import tw.appworks.school.yichien.enabling.service.webpage.HomepageService;

@Controller
@RequestMapping("/therapist/{domain}")
public class TherapistPageController {

    private final ClientService clientService;

    private final SessionServiceImpl sessionService;

    private final TherapistService therapistService;

    private final ArticleService articleService;

    private final HomepageService homepageService;

    public TherapistPageController(ClientService clientService, SessionServiceImpl sessionService, TherapistService therapistService, ArticleService articleService, HomepageService homepageService) {
        this.clientService = clientService;
        this.sessionService = sessionService;
        this.therapistService = therapistService;
        this.articleService = articleService;
        this.homepageService = homepageService;
    }

//	@GetMapping
//	public String therapistMainPage(@PathVariable String domain, Model model) {
//		model.addAttribute("domain", domain);
//		return "therapist/therapist";
//	}

    @GetMapping
    public String therapistMainPage(@PathVariable String domain, Model model,
                                    @CookieValue(value = "enabling", required = false) String sessionID)
            throws JsonProcessingException {
        Long institutionUserId = sessionService.getInstitutionUserIdFromSession(sessionID, domain);
        model.addAttribute("domain", domain);
        therapistService.renderAdminSidebar(domain, model);
        clientService.renderClientListPage(institutionUserId, domain, model);
        return "therapist/client_list";
    }

    @GetMapping("/webpage/article")
    public String setArticle(@PathVariable String domain, Model model) {
        articleService.renderArticleList(domain, model);
        therapistService.renderAdminSidebar(domain, model);
        return "therapist/set_article_therapist";
    }

    @GetMapping("/webpage/article/{id}")
    public String getArticle(@PathVariable String domain, @PathVariable String id,
                             @RequestParam(required = false) int draft, Model model) {
        articleService.renderPageByArticleId(id, model);
        articleService.renderArticleList(domain, model);
        therapistService.renderAdminSidebar(domain, model);
        return "therapist/set_article_therapist";
    }

    @GetMapping("webpage/article/preview")
    public String previewArticle(@PathVariable String domain, Model model) {
        homepageService.renderHeaderAndFooter(domain, model);
        articleService.renderArticlePreviewPage(domain, model);
        return "admin/webpage_setting/preview_article";
    }

    @GetMapping("/client/list")
    public String clientListPage(@PathVariable String domain, Model model,
                                 @CookieValue(value = "enabling", required = false) String sessionID)
            throws JsonProcessingException {
        Long institutionUserId = sessionService.getInstitutionUserIdFromSession(sessionID, domain);
        therapistService.renderAdminSidebar(domain, model);
        clientService.renderClientListPage(institutionUserId, domain, model);
        return "therapist/client_list";
    }

    @GetMapping("/client/report")
    public String clientReportPage(@PathVariable String domain, Model model,
                                   @CookieValue(value = "enabling", required = false) String sessionID)
            throws JsonProcessingException {
        Long institutionUserId = sessionService.getInstitutionUserIdFromSession(sessionID, domain);
        therapistService.renderAdminSidebar(domain, model);
        clientService.renderClientReportPage(institutionUserId, model);
        return "therapist/client_report";
    }

}

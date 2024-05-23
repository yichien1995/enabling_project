package tw.appworks.school.yichien.enabling.controller.mainpage;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import tw.appworks.school.yichien.enabling.service.MainPageService;
import tw.appworks.school.yichien.enabling.service.impl.SessionServiceImpl;

@Controller
public class MainPageController {

    private final MainPageService mainPageService;

    private final SessionServiceImpl sessionService;

    @Value("${prefix.domain}")
    private String domainPrefix;

    public MainPageController(MainPageService mainPageService, SessionServiceImpl sessionService) {
        this.mainPageService = mainPageService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public String index() {
        return "main_page/index";
    }

    @GetMapping("/register")
    public String register(@CookieValue(value = "enabling", required = false) String sessionID) {
        if (sessionID != null) {
            return "redirect:" + domainPrefix + "myinstitution";
        }
        return "main_page/register";
    }

    @GetMapping("/login")
    public String login(@CookieValue(value = "enabling", required = false) String sessionID) {
        if (sessionID != null) {
            return "redirect:" + domainPrefix + "myinstitution";
        }
        return "main_page/login";
    }

    @GetMapping("/myinstitution")
    public String user(Model model,
                       @CookieValue(value = "enabling", required = false) String sessionID,
                       HttpServletResponse response)
            throws JsonProcessingException {
        if (sessionID == null) {
            return "redirect:" + domainPrefix;
        }
        try {
            long userId = sessionService.getUserInfoDTOFromSession(sessionID).getUserId();
            String userName = sessionService.getUserInfoDTOFromSession(sessionID).getUserName();
            model.addAttribute("userName", userName);
            mainPageService.renderMyInstitutionPage(model, userId);
            return "main_page/new_my_institution";
        } catch (NullPointerException e) {
            //delete cookie
            Cookie cookie = new Cookie("enabling", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:" + domainPrefix;
        }
    }

    @GetMapping("/myinstitution/create")
    public String createInstitutionPage(Model model,
                                        @CookieValue(value = "enabling", required = false) String sessionID)
            throws JsonProcessingException {
        String userName = sessionService.getUserInfoDTOFromSession(sessionID).getUserName();
        model.addAttribute("userName", userName);
        return "main_page/new_create_institution";
    }

}

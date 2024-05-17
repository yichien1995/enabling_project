package tw.appworks.school.yichien.enabling.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.NewInstitutionForm;
import tw.appworks.school.yichien.enabling.service.InstitutionService;
import tw.appworks.school.yichien.enabling.service.impl.SessionServiceImpl;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/1.0/admin/institution")
public class InstitutionController {

    private final InstitutionService institutionService;

    private final SessionServiceImpl sessionService;

    public InstitutionController(InstitutionService institutionService, SessionServiceImpl sessionService) {
        this.institutionService = institutionService;
        this.sessionService = sessionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewInstitution(@ModelAttribute NewInstitutionForm form,
                                                  HttpServletResponse response,
                                                  @CookieValue(value = "enabling") String sessionID) throws JsonProcessingException {
        Map<String, Object> domainErrorMsg = institutionService.domainErrorMsg(form.getInstitutionDomain());
        if (domainErrorMsg != null) {
            return ResponseEntity.badRequest().body(domainErrorMsg);
        }
        // get userinfo from session
        long userId = sessionService.getUserInfoDTOFromSession(sessionID).getUserId();
        String email = sessionService.getUserInfoDTOFromSession(sessionID).getUserEmail();

        institutionService.createNewInstitution(userId, form);

        // reset cookie
        Cookie cookie = new Cookie("enabling", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        sessionService.setCookieAndStoreSession(email, response);

        Map<String, Object> result = new HashMap<>();
        result.put("success", "Create institution successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

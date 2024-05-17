package tw.appworks.school.yichien.enabling.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.MemberForm;
import tw.appworks.school.yichien.enabling.service.webpage.MemberService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/1.0/admin/{domain}/setting/member")
public class MemberApiController {

    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createServices(@PathVariable String domain,
                                            @ModelAttribute MemberForm memberForm) {
        memberService.saveMember(domain, memberForm);
        Map<String, Object> result = new HashMap<>();
        result.put("success", "Create member intro successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping(path = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateMember(@PathVariable String id, @PathVariable String domain, @ModelAttribute MemberForm memberForm) {
        long idValue = Long.parseLong(id);
        memberService.updateMember(domain, idValue, memberForm);
        Map<String, Object> result = new HashMap<>();
        result.put("success", "Update member intro successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteMember(@PathVariable String id, @PathVariable String domain) {
        long idValue = Long.parseLong(id);
        memberService.deleteMemberById(idValue);
        return ResponseEntity.ok("Delete member id: " + id);
    }
}

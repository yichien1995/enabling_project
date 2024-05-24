package tw.appworks.school.yichien.enabling.controller.admin.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.MemberForm;
import tw.appworks.school.yichien.enabling.response.SuccessResponse;
import tw.appworks.school.yichien.enabling.service.webpage.MemberService;

@RestController
@RequestMapping("api/1.0/admin/{domain}/webpage/member")
public class MemberApiController {

    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createMembers(@PathVariable String domain,
                                           @ModelAttribute MemberForm memberForm) {
        memberService.saveMember(domain, memberForm);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Create member intro successfully."));
    }

    @PatchMapping(path = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateMember(@PathVariable String id, @PathVariable String domain,
                                          @ModelAttribute MemberForm memberForm) {
        long idValue = Long.parseLong(id);
        memberService.updateMember(domain, idValue, memberForm);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Update member intro successfully."));
    }


    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteMember(@PathVariable String id,
                                          @PathVariable String domain) {
        long idValue = Long.parseLong(id);
        memberService.deleteMemberById(idValue);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Delete member."));
    }
}

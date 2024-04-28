package tw.appworks.school.yichien.enabling.service.impl.webpage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.MemberForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.webpage.Member;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionRepository;
import tw.appworks.school.yichien.enabling.repository.webpage.MemberRepository;
import tw.appworks.school.yichien.enabling.service.FileStorageService;
import tw.appworks.school.yichien.enabling.service.webpage.MemberService;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

	private final FileStorageService fileStorageService;

	private final MemberRepository memberRepository;

	private final InstitutionRepository institutionRepository;

	@Value("${prefix.image}")
	private String imageUrlPrefix;

	public MemberServiceImpl(FileStorageService fileStorageService, MemberRepository memberRepository, InstitutionRepository institutionRepository) {
		this.fileStorageService = fileStorageService;
		this.memberRepository = memberRepository;
		this.institutionRepository = institutionRepository;
	}

	@Override
	public void saveMember(String domain, MemberForm form) {
		Member member = new Member();
		Institution institution = institutionRepository.getInstitution(domain);

		// save image relative URL
		String uploadAndGetPath = fileStorageService.uploadFile(domain, form.getName(), form.getPhoto());

		member.setName(form.getName());
		member.setPhoto(uploadAndGetPath);
		member.setTitle(form.getTitle());
		member.setQualification(form.getQualification());
		member.setEducation(form.getEducation());
		member.setInstitutionDomain(institution);

		memberRepository.save(member);
	}

	@Override
	public void renderMemberPage(String domain, Model model) {
		List<Member> members = memberRepository.getAllMemberByDomain(domain);
		for (Member member : members) {
			member.setQualification(member.getQualification().replace("\n", "<br>"));
			member.setEducation(member.getEducation().replace("\n", "<br>"));
			member.setPhoto(imageUrlPrefix + member.getPhoto());
		}
		model.addAttribute("members", members);
	}

	@Override
	@Transactional
	public void deleteMemberById(Long id) {
		memberRepository.deleteMemberById(id);
	}
}

package tw.appworks.school.yichien.enabling.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.TeamMemberForm;
import tw.appworks.school.yichien.enabling.dto.management.TeamMemberInfoDto;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.account.InstitutionUser;
import tw.appworks.school.yichien.enabling.model.account.Role;
import tw.appworks.school.yichien.enabling.model.account.Users;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionRepository;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionUserRepository;
import tw.appworks.school.yichien.enabling.repository.account.RoleRepository;
import tw.appworks.school.yichien.enabling.repository.account.UsersRepository;
import tw.appworks.school.yichien.enabling.repository.projection.ProjectionRepo;
import tw.appworks.school.yichien.enabling.service.TeamManagementService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamManagementServiceImpl implements TeamManagementService {

	private final ProjectionRepo projectionRepo;

	private final InstitutionRepository institutionRepository;

	private final UsersRepository usersRepository;

	private final RoleRepository roleRepository;

	private final InstitutionUserRepository institutionUserRepository;

	public TeamManagementServiceImpl(ProjectionRepo projectionRepo, InstitutionRepository institutionRepository, UsersRepository usersRepository, RoleRepository roleRepository, InstitutionUserRepository institutionUserRepository) {
		this.projectionRepo = projectionRepo;
		this.institutionRepository = institutionRepository;
		this.usersRepository = usersRepository;
		this.roleRepository = roleRepository;
		this.institutionUserRepository = institutionUserRepository;
	}

	public void renderTeamManagementPage(String domain, Model model) {
		List<TeamMemberInfoDto> data = getTeamMemberInfoDtoByDomain(domain);
		model.addAttribute("TeamMemberInfoDto", data);
	}

	@Override
	public Map<String, Object> saveInstitutionUser(TeamMemberForm teamMemberForm, String domain) {
		Map<String, Object> errorMsg = new HashMap<>();
		InstitutionUser institutionUser = new InstitutionUser();
//		// set FK
		Institution institution = institutionRepository.getInstitution(domain);
		Users user = usersRepository.findUsersByEmail(teamMemberForm.getEmail());
		Role role = roleRepository.findRoleById(teamMemberForm.getRoleId());
		int employeeId = teamMemberForm.getEmployeeId();

		// validation
		if (user == null) {
			errorMsg.put("error", "該用戶不存在");
			return errorMsg;
		}
		// check duplicate employeeID
		boolean checkExistEmployeeId = existEmployeeId(institution, employeeId);
		if (checkExistEmployeeId) {
			errorMsg.put("error", "重複員工編號");
			return errorMsg;
		}

		boolean checkDuplicateUser = duplicateUser(domain, teamMemberForm.getEmail());

		if (checkDuplicateUser) {
			errorMsg.put("error", "該員工已存在");
			return errorMsg;
		}

		institutionUser.setInstitutionDomain(institution);
		institutionUser.setUserId(user);
		institutionUser.setRoleId(role);
		institutionUser.setEmployeeId(employeeId);

		institutionUserRepository.save(institutionUser);
		return null;
	}

	@Override
	@Transactional
	public void deleteInstitutionUserById(Long id) {
		institutionUserRepository.deleteInstitutionUserById(id);
	}

	private boolean duplicateUser(String domain, String email) {
		return institutionUserRepository.countInstitutionUserByDomainANDEmail(domain, email) != 0;
	}

	private boolean existEmployeeId(Institution institution, int employeeId) {
		return institutionUserRepository.existsInstitutionUserByInstitutionDomainAndEmployeeId(institution, employeeId);
	}

	private List<TeamMemberInfoDto> getTeamMemberInfoDtoByDomain(String domain) {
		return projectionRepo.getTeamMemberInfoDTO(domain);
	}
}

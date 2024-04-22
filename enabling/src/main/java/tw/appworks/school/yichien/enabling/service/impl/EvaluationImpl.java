package tw.appworks.school.yichien.enabling.service.impl;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.account.MemberDTO;
import tw.appworks.school.yichien.enabling.dto.form.NewEvaluationForm;
import tw.appworks.school.yichien.enabling.model.account.InstitutionUser;
import tw.appworks.school.yichien.enabling.model.clinial.Evaluation;
import tw.appworks.school.yichien.enabling.model.clinial.EvaluationCalendarEvent;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionUserRepository;
import tw.appworks.school.yichien.enabling.repository.clinial.EvaluationRepository;
import tw.appworks.school.yichien.enabling.repository.projection.ProjectionRepo;
import tw.appworks.school.yichien.enabling.service.EvaluationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvaluationImpl implements EvaluationService {

	private final ProjectionRepo projectionRepo;

	private final InstitutionUserRepository institutionUserRepository;

	private final EvaluationRepository evaluationRepository;

	public EvaluationImpl(ProjectionRepo projectionRepo, InstitutionUserRepository institutionUserRepository, EvaluationRepository evaluationRepository) {
		this.projectionRepo = projectionRepo;
		this.institutionUserRepository = institutionUserRepository;
		this.evaluationRepository = evaluationRepository;
	}

	@Override
	public void renderEvaluationSettingPage(String domain, Model model) {
		List<MemberDTO> memberDTO = getMemberDto(domain);
		List<Evaluation> evaluations = evaluationRepository.getEvaluationsByDomain(domain);

		model.addAttribute("memberData", memberDTO);
		model.addAttribute("evaluations", evaluations);
	}

	@Override
	public void renderEvaluationPage(String domain, Model model) {
		List<Evaluation> unreservedEvaluations = evaluationRepository.getUnreservedEvaluations(domain);
		List<EvaluationCalendarEvent> events = getEvaluationEvents(unreservedEvaluations);
		List<UnreservedEvaluationOption> options = getAllUnreservedEvaluation(unreservedEvaluations);

		model.addAttribute("events", events);
		model.addAttribute("evaluations", unreservedEvaluations);
		model.addAttribute("options", options);
	}

	@Override
	public void saveNewEvaluation(String domain, NewEvaluationForm newEvaluationForm) {
		Long institutionUserId = Long.parseLong(newEvaluationForm.getInstitutionUserId());
		Evaluation evaluation = new Evaluation();
		InstitutionUser institutionUser = institutionUserRepository.findInstitutionUserById(institutionUserId);

		evaluation.setEvaluationDate(newEvaluationForm.getEvaluationDate());
		evaluation.setEvaluationTime(newEvaluationForm.getEvaluationTime());
		evaluation.setInstitutionUserId(institutionUser);
		evaluation.setReserved(0);

		evaluationRepository.save(evaluation);

	}

	@Override
	@Transactional
	public void deleteEvaluation(long id) {
		evaluationRepository.deleteEvaluationById(id);
	}

	private List<EvaluationCalendarEvent> getEvaluationEvents(List<Evaluation> evaluations) {
		return evaluations.stream().map(this::mapToEvent).collect(Collectors.toList());
	}

	private List<MemberDTO> getMemberDto(String domain) {
		return projectionRepo.getMemberDto(domain);
	}

	private EvaluationCalendarEvent mapToEvent(Evaluation evaluation) {
		EvaluationCalendarEvent event = new EvaluationCalendarEvent();
		event.setTitle(evaluation.getInstitutionUserId().getUserId().getUsername());
		event.setStart(formatLocalDateTime(evaluation.getEvaluationDate(), evaluation.getEvaluationTime()));
		return event;
	}

	private String formatLocalDateTime(LocalDate date, LocalTime time) {
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return dateTime.format(formatter);
	}

	private List<UnreservedEvaluationOption> getAllUnreservedEvaluation(List<Evaluation> evaluations) {
		return evaluations.stream().map(this::mapUnreservedEvaluationOption).collect(Collectors.toList());
	}

	private UnreservedEvaluationOption mapUnreservedEvaluationOption(Evaluation evaluation) {
		UnreservedEvaluationOption unreservedEvaluationOption = new UnreservedEvaluationOption();
		unreservedEvaluationOption.setId(evaluation.getId());
		unreservedEvaluationOption.setDetail(evaluation.getEvaluationDate().toString() + " " +
				evaluation.getEvaluationTime().toString() + " " +
				evaluation.getInstitutionUserId().getUserId().getUsername());
		return unreservedEvaluationOption;
	}

	@Data
	private static class UnreservedEvaluationOption {
		private long id;
		private String detail;
	}
}

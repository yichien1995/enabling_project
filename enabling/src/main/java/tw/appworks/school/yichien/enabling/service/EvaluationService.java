package tw.appworks.school.yichien.enabling.service;

import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.NewEvaluationForm;

public interface EvaluationService {
	void renderEvaluationSettingPage(String domain, Model model);

	void renderEvaluationPage(String domain, Model model);

	void saveNewEvaluation(String domain, NewEvaluationForm newEvaluationForm);

	void deleteEvaluation(long id);
}

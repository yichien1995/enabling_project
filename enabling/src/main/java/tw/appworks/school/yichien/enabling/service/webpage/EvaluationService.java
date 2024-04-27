package tw.appworks.school.yichien.enabling.service.webpage;

import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.NewEvaluationForm;
import tw.appworks.school.yichien.enabling.dto.form.ReserveEvaluationForm;

public interface EvaluationService {
	void renderEvaluationSettingPage(String domain, Model model);

	void renderEvaluationPage(String domain, Model model);

	void saveNewEvaluation(String domain, NewEvaluationForm newEvaluationForm);

	void deleteEvaluation(long id);

	void reserveEvaluation(ReserveEvaluationForm reserveEvaluationForm);
}

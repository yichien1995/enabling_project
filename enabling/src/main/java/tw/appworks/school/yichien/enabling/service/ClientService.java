package tw.appworks.school.yichien.enabling.service;

import tw.appworks.school.yichien.enabling.dto.form.ClientReportForm;

public interface ClientService {

	void saveClientReport(Long institutionUserId, ClientReportForm form);
}

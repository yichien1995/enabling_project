package tw.appworks.school.yichien.enabling.service;

import tw.appworks.school.yichien.enabling.dto.account.HomepagePreviewDTO;
import tw.appworks.school.yichien.enabling.dto.form.HomepageForm;

public interface AdminService {
	HomepagePreviewDTO setPreviewData(HomepageForm h);
//	String checkDomain();
}

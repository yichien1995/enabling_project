package tw.appworks.school.yichien.enabling.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String uploadFile(String domainName, String newFileName, MultipartFile file);
}

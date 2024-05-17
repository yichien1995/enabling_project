package tw.appworks.school.yichien.enabling.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tw.appworks.school.yichien.enabling.service.FileStorageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Override
    public String uploadFile(String domainName, String newFileName, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                // Get the root path of the file
                Path baseDir = Paths.get(System.getProperty("user.dir"));
                // Set relative path
                String relativeDir = "uploads/" + domainName + "/";
                String originalFileName = file.getOriginalFilename();

                // Extract file extension
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

                // Modify file name (e.g., adding timestamp)

                // Combine modified file name and extension
                String newFileNameWithExtension = newFileName + fileExtension;

                // Construct file path
                Path filePath = baseDir.resolve(relativeDir).resolve(newFileNameWithExtension);

                // Create directory if it doesn't exist
                Files.createDirectories(filePath.getParent());

                // Save the file
                file.transferTo(filePath.toFile());

                // Return relative file path
                return Paths.get(relativeDir, newFileNameWithExtension).toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "fail to upload file";
            }
        }
        return null;
    }
}

package tw.appworks.school.yichien.enabling.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CommonsLog
@Component
public class S3UploadServiceImpl {

	@Value("${s3.access.key}")
	private String accessKey;

	@Value("${s3.secret.key}")
	private String secretKey;

	@Value("${s3.bucket.name}")
	private String bucketName;

	private static File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
		File file = new File(multipartFile.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(multipartFile.getBytes());
		fos.close();
		return file;
	}

	private AmazonS3 getS3Client() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

		// Create an S3 client
		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.AP_NORTHEAST_1)
				.build();
	}

	public void uploadFile(String saveKeyName, File file) {
		AmazonS3 s3 = getS3Client();
		try {
			s3.putObject(bucketName, saveKeyName, file);
		} catch (AmazonServiceException e) {
			log.error("upload " + saveKeyName + " failed: " + e.getMessage());
		}
	}

	public List<String> getFileList(String folderFilePrefix) {
		AmazonS3 s3 = getS3Client();

		ListObjectsV2Request request = new ListObjectsV2Request()
				.withBucketName(bucketName)
				.withPrefix(folderFilePrefix);
		List<String> keyList = new ArrayList<>();
		try {
			ListObjectsV2Result result = s3.listObjectsV2(request);
			List<S3ObjectSummary> objects = result.getObjectSummaries();

			keyList = objects.stream()
					.map(S3ObjectSummary::getKey)
					.collect(Collectors.toList());
		} catch (AmazonServiceException e) {
			log.error("Get file list with folder " + folderFilePrefix + " failed: " + e.getMessage());
		}
		return keyList;
	}

	public void saveMultipartFile(MultipartFile file, String filePath) throws IOException {
		File convertedFile = convertMultipartToFile(file);
		uploadFile(filePath, convertedFile);
		convertedFile.delete();
	}

	public String uploadFileToS3(String domainName, String newFileName, MultipartFile file) {
		if (file != null && !file.isEmpty()) {
			try {
				String originalFileName = file.getOriginalFilename();

				// Extract file extension
				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

				// Modify file name (e.g., adding timestamp)

				// Combine modified file name and extension
				String newFileNameWithExtension = newFileName + fileExtension;

				String relativeDir = "enabling/" + domainName + "/";

				String filePath = Paths.get(relativeDir, newFileNameWithExtension).toString();

				saveMultipartFile(file, filePath);

				return filePath;
			} catch (IOException e) {
				e.printStackTrace();
				return "fail to upload file";
			}
		}
		return null;
	}
}

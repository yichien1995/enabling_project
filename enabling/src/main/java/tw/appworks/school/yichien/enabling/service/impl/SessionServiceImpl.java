package tw.appworks.school.yichien.enabling.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import tw.appworks.school.yichien.enabling.dto.account.UserInfoDTO;
import tw.appworks.school.yichien.enabling.repository.projection.ProjectionRepo;

import java.util.UUID;

@Service
public class SessionServiceImpl {
	private final StringRedisTemplate redisTemplate;
	private final HashOperations<String, String, String> hashOperations;
	private final ProjectionRepo projectionRepo;

	@Autowired
	public SessionServiceImpl(StringRedisTemplate redisTemplate,
	                          ProjectionRepo projectionRepo) {
		this.redisTemplate = redisTemplate;
		this.hashOperations = redisTemplate.opsForHash();
		this.projectionRepo = projectionRepo;
	}

	public void setCookieAndStoreSession(String email, HttpServletResponse response) throws JsonProcessingException {
		// generate session ID
		String sessionId = generateRandomToken();
		Cookie cookie = new Cookie("enabling", sessionId);
		cookie.setPath("/");
		// TODO: set cookie expired time
		cookie.setMaxAge(3600);
		response.addCookie(cookie);

		// store session data
		UserInfoDTO userInfo = projectionRepo.getUserInfoDTO(email);
		//TODO: check institutionUserInfo.isEmpty()
//		List<InstitutionUserDTO> institutionUserInfo = projectionRepo.getInstitutionUserDTO(email);

		ObjectMapper mapper = new ObjectMapper();
		String stringUserInfoDTO = mapper.writeValueAsString(userInfo);
		saveSessionToRedis(sessionId, "user", stringUserInfoDTO);

	}

	private void saveSessionToRedis(String sessionId, String field, String value) {
		hashOperations.put(sessionId, field, value);
	}

	public UserInfoDTO getUserInfoDTOFromSession(String sessionId) {
		String userValue = hashOperations.get(sessionId, "user");
		return deserialize(userValue, UserInfoDTO.class);
	}

	private <T> T deserialize(String data, Class<T> clazz) {
		RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
		Jackson2JsonRedisSerializer<T> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(clazz);
		return jsonRedisSerializer.deserialize(serializer.serialize(data));
	}


	private String generateRandomToken() {
		return UUID.randomUUID().toString();
	}
}

package lk.softvil.assignment.eventm.security.service;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class OtpService {

    private final StringRedisTemplate redisTemplate;

    public OtpService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateOtp(String email) {
        String otp = RandomStringUtils.randomNumeric(6);
        // Store OTP with expiration time (e.g., 5 minutes)
        redisTemplate.opsForValue().set(email, otp, Duration.ofMinutes(5));
        return otp;
    }

    public boolean validateOtp(String email, String otp) {
        String cachedOtp = redisTemplate.opsForValue().get(email);
        return cachedOtp != null && cachedOtp.equals(otp);
    }

    public void clearOtp(String email) {
        redisTemplate.delete(email);
    }
}

package lk.softvil.assignment.eventm.security.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.cache.Cache;
import javax.cache.CacheManager;

@Service
public class OtpService {


    private final Cache<String, String> otpCache;

    public OtpService(CacheManager cacheManager) {
        this.otpCache = cacheManager.getCache("otp-cache");
    }

    public String generateOtp(String email) {
        String otp = RandomStringUtils.randomNumeric(6);
        otpCache.put(email, otp);
        return otp;
    }

    public boolean validateOtp(String email, String otp) {
        String cachedOtp = otpCache.get(email);
        return cachedOtp != null && cachedOtp.equals(otp);
    }

    public void clearOtp(String email) {
        otpCache.remove(email);
    }


}
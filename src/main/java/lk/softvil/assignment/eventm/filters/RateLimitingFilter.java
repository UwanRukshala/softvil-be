package lk.softvil.assignment.eventm.filters;



import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.grid.jcache.JCacheProxyManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import javax.cache.CacheManager;
import java.io.IOException;
import java.time.Duration;
import java.util.function.Supplier;

public class RateLimitingFilter implements Filter {

    private final ProxyManager<String> buckets;
    private final Supplier<BucketConfiguration> configSupplier;

    public RateLimitingFilter(CacheManager cacheManager) {
        this.buckets = new JCacheProxyManager<>(cacheManager.getCache("rate-limit-buckets"));

        // Define rate limits: 10 requests per minute per IP
        this.configSupplier = () -> BucketConfiguration.builder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1))))
                .build();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ip = getClientIp(httpRequest);

        // Resolve bucket for this IP
        Bucket bucket = buckets.builder().build(ip, configSupplier);

        // Check request allowance
        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json");
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.getWriter().write("""
                {
                    "error": "Too many requests",
                    "message": "Rate limit exceeded. Try again in 1 minute"
                }
                """);
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
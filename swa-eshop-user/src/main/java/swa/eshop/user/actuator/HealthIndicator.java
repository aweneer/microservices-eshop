package swa.eshop.user.actuator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class HealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.up()
                .withDetail("app", "User-service is UP")
                .withDetail("error", "User-service is DOWN");
    }

    @Override
    public Health getHealth(boolean includeDetails) {
        return null;
    }
}

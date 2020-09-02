package com.bankmanagement.loan.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "interestrate")
@Data
public class LoanProperties {
    private Map<Integer, BigDecimal> percentage;
}

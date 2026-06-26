package mx.com.unam.sispro3d.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Component
public class ServiceOffering {
    @Value("${SERVICE_ID}")
    private int id;
    @Value("${SERVICE_TITLE}")
    private String title;
    @Value("${SERVICE_DESC}")
    private String description;
    @Value("${SERVICE_BASE_PRICE}")
    private BigDecimal basePrice;
    @Value("${SERVICE_DELIVERY_TIME_DAYS}")
    private int deliveryTimeDays;
    private LocalDateTime createdAt = LocalDateTime.now();

    @Autowired
    private Category category;
}
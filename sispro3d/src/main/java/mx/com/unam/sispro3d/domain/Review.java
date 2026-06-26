package mx.com.unam.sispro3d.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Data
@Component
public class Review {
    @Value("${REVIEW_ID}")
    private int id;
    @Value("${REVIEW_RATING}")
    private int rating;
    @Value("${REVIEW_COMMENT}")
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now();

    @Autowired
    private Account account;

    @Autowired
    private ServiceOffering service;
}
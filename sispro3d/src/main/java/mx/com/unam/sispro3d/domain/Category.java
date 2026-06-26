package mx.com.unam.sispro3d.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Category {
    @Value("${CATEGORY_ID}")
    private int id;
    @Value("${CATEGORY_NAME}")
    private String name;
    @Value("${CATEGORY_DESC}")
    private String description;
}
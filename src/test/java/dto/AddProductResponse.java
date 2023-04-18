package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AddProductResponse {
//    Product product;
    Integer quantity;
    String code;

//    @Data
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public class Product{
//        String code;
//
//    }
}

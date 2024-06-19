package shinhan.server_common.domain.invest.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StockCartDate {
    private String stckBsopDate;
    private String stckOprc;
    private String stckHgpr;
    private String stckLwpr;
    private String stckClpr;
}

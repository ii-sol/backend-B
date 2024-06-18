package sinhan.server1.domain.invest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Data
public class StockFindCurrentResponseDTO {
    public String changePrice;
    public String changeSign;
    public String changeRate;
    public String currentPrice;
}

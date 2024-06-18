package sinhan.server2.domain.invest.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sinhan.server2.domain.invest.entity.StockCartDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StockFindDetailResponseDTO {
    public String companyName;
    public int currentPrice;
    public String marketCapitalization;
    public String dividendYield;
    public String PBR;
    public String PER;
    public String ROE;
    public String PSR;
    public String changePrice;
    public String changeSign;
    public String changeRate;
    public List<StockCartDate> charts;
}

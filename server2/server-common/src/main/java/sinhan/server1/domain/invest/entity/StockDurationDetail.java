package sinhan.server1.domain.invest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StockDurationDetail {
    public String per;
    public String pbr;
    @JsonProperty("hts_avls")
    public String marketCapitalization;

    @JsonProperty("hts_kor_isnm")
    public String companyName;

    @JsonProperty("stck_prpr")
    public int currentPrice;
    @JsonProperty("prdy_vrss")
    public String changePrice;
    @JsonProperty("prdy_vrss_sign")
    public String changeSign;
    @JsonProperty("prdy_ctrt")
    public String changeRate;

}
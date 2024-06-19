package shinhan.server_common.domain.invest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming
public class StockFianceDetail {
    @JsonProperty("roe_val")
    private String roe;
    @JsonProperty("sps")
    private int sps;
}

package shinhan.server_common.domain.invest.entity;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming
public class StockFianceResponseOutput {
    public List<StockFianceDetail> output;
}

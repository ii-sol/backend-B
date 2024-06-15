package sinhan.server1.domain.invest.entity;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockDuraionPriceOutput {
    
    private StockDurationDetail output1;
    private List<StockCartDate> output2;

}

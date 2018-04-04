package scrape.web.page.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductData {

    private List<Product> results;
    private BigDecimal total;

    public List<Product> getResults() {
        if (Objects.nonNull(results)) {
            return results;
        }
        return new ArrayList<>();
    }

    public void setResults(List<Product> results) {
        this.results = results;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}

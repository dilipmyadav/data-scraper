package scrape.web.page.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class Product {

    @JsonProperty("title")
    private String title;
    @JsonProperty("kcal_per_100g")
    private Integer kCalPerHundredGrams;
    @JsonProperty("unit_price")
    private BigDecimal unitPrice;
    @JsonProperty("description")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getkCalPerHundredGrams() {
        return kCalPerHundredGrams;
    }

    public void setkCalPerHundredGrams(Integer kCalPerHundredGrams) {
        this.kCalPerHundredGrams = kCalPerHundredGrams;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

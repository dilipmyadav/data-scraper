package scrape.web.page.command;

import org.jsoup.nodes.Document;
import scrape.web.page.data.ProductData;

import java.util.Objects;

public class ScrapeContext {

    private String url;
    private String section;
    private Document document;
    private ProductData productData;

    private ConnectionWrapper connectionWrapper;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public ProductData getProductData() {
        return productData;
    }

    public void setProductData(ProductData productData) {
        this.productData = productData;
    }

    public void setConnectionWrapper(ConnectionWrapper connectionWrapper) {
        this.connectionWrapper = connectionWrapper;
    }

    public ConnectionWrapper getConnectionWrapper() {
        if (Objects.nonNull(connectionWrapper))
        {
            return connectionWrapper;
        }
        return new ConnectionWrapper();
    }
}

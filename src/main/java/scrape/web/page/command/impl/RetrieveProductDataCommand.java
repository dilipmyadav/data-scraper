package scrape.web.page.command.impl;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import scrape.web.page.command.Command;
import scrape.web.page.command.ScrapeContext;
import scrape.web.page.data.Product;
import scrape.web.page.data.ProductData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class RetrieveProductDataCommand implements Command<ScrapeContext> {

    private static final Logger LOG = Logger.getLogger("ParseHtmlLogger");
    private static final String KCAL = "kcal";
    private static final String EMPTY_STRING = "";
    public static final String PRODUCT_LISTER = "productLister";
    public static final String PRODUCT_NAME_AND_PROMOTIONS = "productNameAndPromotions";
    public static final String ABS_HREF = "abs:href";
    public static final String PRICE_PER_UNIT = "pricePerUnit";
    public static final String PRODUCT = "product";
    public static final String PRODUCT_TITLE_DESCRIPTION_CONTAINER = "productTitleDescriptionContainer";
    public static final String NUTRITION_TABLE = "nutritionTable";
    public static final String PRODUCT_TEXT = "productText";
    public static final String TABLE_ROW_0 = "tableRow0";

    @Override
    public void execute(ScrapeContext context) throws Exception {

        LOG.info("Retrieving Product Information");

        ProductData productData = new ProductData();
        Elements products = getProducts(context);

        BigDecimal total = new BigDecimal(0.00);
        List<Product> productResults = new ArrayList<>();

        for (Element productInfo : products) {
            String productDetailsPageLink = getProductDetailsPage(productInfo);
            Document productDetailsDocument = context.getConnectionWrapper().getDocument(productDetailsPageLink);
            BigDecimal productPrice =  new BigDecimal(getProductPrice(productInfo));
            Product product = populateProduct(productDetailsDocument, productPrice);

            productResults.add(product);

            total = total.add(productPrice);
        }
        productData.setResults(productResults);
        productData.setTotal(total);

        context.setProductData(productData);
    }

    private Elements getProducts(ScrapeContext context) {
        Document document = context.getDocument();
        Element productsContainer = document.getElementById(context.getSection());
        Elements productsList = getElementsByClass(productsContainer, PRODUCT_LISTER);
        return getElementsByClass(productsList.first(), PRODUCT);
    }

    private Product populateProduct(Document productDetailsDocument, BigDecimal productPrice) {
        Product product = new Product();
        populateProductWithTitle(product, productDetailsDocument);
        populateProductWithKCalInformation(product, productDetailsDocument);
        populateProductWithPrice(product, productPrice);
        populateProductWithDescription(product, productDetailsDocument);
        return product;
    }

    private void populateProductWithKCalInformation(Product product, Document productDescriptionDocument) {
        Elements nutritionTable = getElementsByClass(productDescriptionDocument, NUTRITION_TABLE);

        if (Objects.nonNull(nutritionTable) && !nutritionTable.isEmpty()) {
            Element kCalInformation = getElementsByClass(nutritionTable.first(), TABLE_ROW_0).first();
            String kCalPer100gms;
            if (Objects.nonNull(kCalInformation)) {
                kCalPer100gms = kCalInformation.child(0).text().replace(KCAL, EMPTY_STRING);
                product.setkCalPerHundredGrams(Integer.parseInt(kCalPer100gms));
            } else {
                Elements rows = nutritionTable.select("tr");
                kCalPer100gms = rows.get(2).getAllElements().get(2).text();
                product.setkCalPerHundredGrams(Integer.parseInt(kCalPer100gms));
            }
        }
    }

    private void populateProductWithDescription(Product product, Document productDescriptionDocument) {
        Element productDescriptionElement = getElementsByClass(productDescriptionDocument, PRODUCT_TEXT).first();
        Element descriptionText = productDescriptionElement.select("p").first();
        product.setDescription(descriptionText.text());
    }

    private void populateProductWithPrice(Product product, BigDecimal productPrice) {
        product.setUnitPrice(productPrice);
    }

    private void populateProductWithTitle(Product product, Document detailsPageDocument) {
        Element productTitleElement = getElementsByClass(detailsPageDocument, PRODUCT_TITLE_DESCRIPTION_CONTAINER).first();
        Element titleText = productTitleElement.select("h1").first();
        product.setTitle(titleText.text());
    }

    private String getProductPrice(Element productInfo) {
        Element productPricePerUnit = getElementsByClass(productInfo, PRICE_PER_UNIT).select("p").first();
        String productUnitPrice = productPricePerUnit.text();
        return productUnitPrice.substring(1, 5);
    }

    private String getProductDetailsPage(Element productInfo) {
        Elements productNameAndPromotion = getElementsByClass(productInfo, PRODUCT_NAME_AND_PROMOTIONS);
        Element link = productNameAndPromotion.select("a").first();
        return link.attr(ABS_HREF);
    }

    private Elements getElementsByClass(Element element, String classNameOnElement) {
        return element.getElementsByClass(classNameOnElement);
    }
}

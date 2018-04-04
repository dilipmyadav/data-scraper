package scrape.web.page.command.impl;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import scrape.web.page.command.ConnectionWrapper;
import scrape.web.page.command.ScrapeContext;
import scrape.web.page.data.ProductData;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static scrape.web.page.command.impl.RetrieveProductDataCommand.ABS_HREF;
import static scrape.web.page.command.impl.RetrieveProductDataCommand.NUTRITION_TABLE;
import static scrape.web.page.command.impl.RetrieveProductDataCommand.PRICE_PER_UNIT;
import static scrape.web.page.command.impl.RetrieveProductDataCommand.PRODUCT;
import static scrape.web.page.command.impl.RetrieveProductDataCommand.PRODUCT_LISTER;
import static scrape.web.page.command.impl.RetrieveProductDataCommand.PRODUCT_NAME_AND_PROMOTIONS;
import static scrape.web.page.command.impl.RetrieveProductDataCommand.PRODUCT_TEXT;
import static scrape.web.page.command.impl.RetrieveProductDataCommand.PRODUCT_TITLE_DESCRIPTION_CONTAINER;
import static scrape.web.page.command.impl.RetrieveProductDataCommand.TABLE_ROW_0;

@RunWith(MockitoJUnitRunner.class)
public class RetrieveProductDataCommandTest {

    private static final String PRODUCTS_CONTAINER = "productsContainer";
    private static final String PRODUCT_DETAILS_PAGE_URL = "http://www.example.com";
    public static final String PRODUCT_TITLE = "Sainsbury's Cherries 400g";
    public static final String KCAL_INFO_1 = "33kcal";
    public static final String DESCRIPTION_TEXT_1 = "by Sainsbury's strawberries";
    public static final String UNIT_PRICE_1 = "£2.50/unit";

    private RetrieveProductDataCommand retrieveProductDataCommand;

    private ScrapeContext scrapeContext;

    @Mock
    private Document document;

    @Mock
    private Element productContainer;

    @Mock
    private Elements productList;

    @Mock
    private Element products;

    private Elements product;

    @Mock
    private Element product1Info;

    @Mock
    private Element product2Info;

    @Mock
    private Elements productNameAndPromotion;

    @Mock
    private Elements links;

    @Mock
    private Element link;

    @Mock
    private ConnectionWrapper connectionWrapper;

    @Mock
    private Document productDetailsPageDocument;

    @Mock
    private Elements priceElements;

    @Mock
    private Elements unitPriceElements;

    @Mock
    private Element unitPrice;

    @Mock
    private Elements productTitleElements;

    @Mock
    private Element productTitleElement;

    @Mock
    private Elements productTitle;

    @Mock
    private Element firstProductTitleElement;

    @Mock
    private Elements nutritionTable;

    @Mock
    private Element firstRow;

    @Mock
    private Elements columnInFirstRow;

    @Mock
    private Element columnInfoFirstRow;

    @Mock
    private Element kCalInfo;

    @Mock
    private Elements productText;

    @Mock
    private Element productDescriptionElement;

    @Mock
    private Elements descriptionTextElement;

    @Mock
    private Element firstDescriptionTextElement;

    @Mock
    private Elements rows;

    @Mock
    private Element secondRow;

    @Mock
    private Elements allElementsSecondRow;

    @Mock
    private Element selectedElement;

    @Before
    public void setUp() throws Exception {
        retrieveProductDataCommand = new RetrieveProductDataCommand();
        scrapeContext = new ScrapeContext();
        scrapeContext.setDocument(document);
        scrapeContext.setSection(PRODUCTS_CONTAINER);
        scrapeContext.setConnectionWrapper(connectionWrapper);
        product = new Elements(product1Info, product2Info);

        when(document.getElementById(PRODUCTS_CONTAINER)).thenReturn(productContainer);
        when(productContainer.getElementsByClass(PRODUCT_LISTER)).thenReturn(productList);
        when(productList.first()).thenReturn(products);
        when(products.getElementsByClass(PRODUCT)).thenReturn(product);
        when(product1Info.getElementsByClass(PRODUCT_NAME_AND_PROMOTIONS)).thenReturn(productNameAndPromotion);
        when(product2Info.getElementsByClass(PRODUCT_NAME_AND_PROMOTIONS)).thenReturn(productNameAndPromotion);
        when(productNameAndPromotion.select("a")).thenReturn(links);
        when(links.first()).thenReturn(link);
        when(link.attr(ABS_HREF)).thenReturn(PRODUCT_DETAILS_PAGE_URL);
        when(connectionWrapper.getDocument(anyString())).thenReturn(productDetailsPageDocument);
        when(product1Info.getElementsByClass(PRICE_PER_UNIT)).thenReturn(priceElements);
        when(product2Info.getElementsByClass(PRICE_PER_UNIT)).thenReturn(priceElements);
        when(priceElements.select("p")).thenReturn(unitPriceElements);
        when(unitPriceElements.first()).thenReturn(unitPrice);
        when(unitPrice.text()).thenReturn(UNIT_PRICE_1);
        when(productDetailsPageDocument.getElementsByClass(PRODUCT_TITLE_DESCRIPTION_CONTAINER)).thenReturn(productTitleElements);
        when(productTitleElements.first()).thenReturn(productTitleElement);
        when(productTitleElement.select("h1")).thenReturn(productTitle);
        when(productTitle.first()).thenReturn(firstProductTitleElement);
        when(firstProductTitleElement.text()).thenReturn(PRODUCT_TITLE);
        when(productDetailsPageDocument.getElementsByClass(PRODUCT_TEXT)).thenReturn(productText);
        when(productText.first()).thenReturn(productDescriptionElement);
        when(productDescriptionElement.select("p")).thenReturn(descriptionTextElement);
        when(descriptionTextElement.first()).thenReturn(firstDescriptionTextElement);
        when(firstDescriptionTextElement.text()).thenReturn(DESCRIPTION_TEXT_1);
    }

    @Test
    public void shouldPopulateContextWithProductData() throws Exception {
        // Given
        when(productDetailsPageDocument.getElementsByClass(NUTRITION_TABLE)).thenReturn(nutritionTable);
        when(nutritionTable.first()).thenReturn(firstRow);
        when(firstRow.getElementsByClass(TABLE_ROW_0)).thenReturn(columnInFirstRow);
        when(columnInFirstRow.first()).thenReturn(columnInfoFirstRow);
        when(columnInfoFirstRow.child(0)).thenReturn(kCalInfo);
        when(kCalInfo.text()).thenReturn(KCAL_INFO_1);

        // When
        retrieveProductDataCommand.execute(scrapeContext);

        // Then
        ProductData productData = scrapeContext.getProductData();
        assertThat(productData.getTotal(), is(new BigDecimal("5.00")));
        assertThat(productData.getResults().get(0).getTitle(), is(PRODUCT_TITLE));
        assertThat(productData.getResults().get(0).getkCalPerHundredGrams(), is(33));
        assertThat(productData.getResults().get(0).getUnitPrice(), is(new BigDecimal("2.50")));
        assertThat(productData.getResults().get(0).getDescription(), is(DESCRIPTION_TEXT_1));

    }

    @Test
    public void shouldPopulateProductDataWithKCalInformationEvenIfTheHtmlTableIsOfDifferentStyle() throws Exception {
        // Given
        when(productDetailsPageDocument.getElementsByClass(NUTRITION_TABLE)).thenReturn(nutritionTable);
        when(nutritionTable.first()).thenReturn(firstRow);
        when(firstRow.getElementsByClass(TABLE_ROW_0)).thenReturn(columnInFirstRow);
        when(columnInFirstRow.first()).thenReturn(null);
        when(nutritionTable.select("tr")).thenReturn(rows);
        when(rows.get(2)).thenReturn(secondRow);
        when(secondRow.getAllElements()).thenReturn(allElementsSecondRow);
        when(allElementsSecondRow.get(2)).thenReturn(selectedElement);
        when(selectedElement.text()).thenReturn("45");

        // When
        retrieveProductDataCommand.execute(scrapeContext);

        // Then
        ProductData productData = scrapeContext.getProductData();
        assertThat(productData.getTotal(), is(new BigDecimal("5.00")));
        assertThat(productData.getResults().get(0).getTitle(), is(PRODUCT_TITLE));
        assertThat(productData.getResults().get(0).getkCalPerHundredGrams(), is(45));
        assertThat(productData.getResults().get(0).getUnitPrice(), is(new BigDecimal("2.50")));
        assertThat(productData.getResults().get(0).getDescription(), is(DESCRIPTION_TEXT_1));

    }
}
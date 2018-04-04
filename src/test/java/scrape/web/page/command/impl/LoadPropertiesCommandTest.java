package scrape.web.page.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import scrape.web.page.command.ScrapeContext;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LoadPropertiesCommandTest {

    private static final String ACTUAL_URL = "www.test.com";
    private static final String PRODUCTS_CONTAINER = "productsContainer";
    private LoadPropertiesCommand loadPropertiesCommand;

    @Before
    public void setUp() {
        loadPropertiesCommand = new LoadPropertiesCommand("src/test/resources/test.properties");
    }

    @Test
    public void shouldLoadPropertiesFromFile() {
        // Given
        ScrapeContext scrapeContext = new ScrapeContext();

        // When
        loadPropertiesCommand.execute(scrapeContext);

        // Then
        assertThat(scrapeContext.getUrl(), is(ACTUAL_URL));
        assertThat(scrapeContext.getSection(), is(PRODUCTS_CONTAINER));
    }
}
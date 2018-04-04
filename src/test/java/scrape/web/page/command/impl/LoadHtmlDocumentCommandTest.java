package scrape.web.page.command.impl;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import scrape.web.page.command.ScrapeContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParseHtmlCommandTest {

    private ParseHtmlCommand parseHtmlCommand;

    @Mock
    private ScrapeContext scrapeContext;

    @Mock
    private Document document;

    @Before
    public void setUp() {
        parseHtmlCommand = new ParseHtmlCommand();
    }

    @Test
    public void shouldParseAndSetDocumentToContext() {
        // Given
        when(scrapeContext.getUrl()).thenReturn("http://www.test.com");
        when(scrapeContext.getDocument()).thenReturn(document);

        // When
        parseHtmlCommand.execute(scrapeContext);

        // Then
        verify(scrapeContext).setDocument(any(Document.class));
    }
}
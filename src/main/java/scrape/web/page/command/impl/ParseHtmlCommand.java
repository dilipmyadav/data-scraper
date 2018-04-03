package scrape.web.page.command.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import scrape.web.page.command.Command;
import scrape.web.page.command.ScrapeContext;

import java.io.IOException;
import java.util.logging.Logger;

public class ParseHtmlCommand implements Command<ScrapeContext> {

    private static final Logger LOG = Logger.getLogger("ParseHtmlLogger");

    @Override
    public void execute(ScrapeContext context) {
        try {
            Document document = Jsoup.connect(context.getUrl()).get();
            context.setDocument(document);

            LOG.info("Parsing: " + context.getDocument().title());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

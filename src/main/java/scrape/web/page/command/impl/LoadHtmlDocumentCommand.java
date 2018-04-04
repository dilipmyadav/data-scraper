package scrape.web.page.command.impl;

import org.jsoup.nodes.Document;
import scrape.web.page.command.Command;
import scrape.web.page.command.ScrapeContext;

import java.util.logging.Logger;

public class LoadHtmlDocumentCommand implements Command<ScrapeContext> {

    private static final Logger LOG = Logger.getLogger("ParseHtmlLogger");

    @Override
    public void execute(ScrapeContext context) {
        try {
            Document document = context.getConnectionWrapper().getDocument(context.getUrl());

            context.setDocument(document);

        } catch (Exception e) {
            LOG.severe("A problem occurred while getting html document: ");
            e.printStackTrace();
        }
    }
}

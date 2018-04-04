package scrape.web.page;

import scrape.web.page.command.Command;
import scrape.web.page.command.ScrapeContext;
import scrape.web.page.command.handler.ScrapeHandler;
import scrape.web.page.command.impl.LoadPropertiesCommand;
import scrape.web.page.command.impl.OutputDataCommand;
import scrape.web.page.command.impl.LoadHtmlDocumentCommand;
import scrape.web.page.command.impl.RetrieveProductDataCommand;

import java.util.logging.Logger;

public class Scraper {

    private static final Logger LOG =  Logger.getLogger("Scraper");

    public static void main(String args[]) {

        String propertiesFile = args[0];
        Command loadPropertiesCommand = new LoadPropertiesCommand(propertiesFile);
        Command loadHtmlDocumentCommand = new LoadHtmlDocumentCommand();
        Command retrieveProductDataCommand = new RetrieveProductDataCommand();
        Command outputDataCommand = new OutputDataCommand();
        ScrapeHandler scrapeHandler = new ScrapeHandler(loadPropertiesCommand, loadHtmlDocumentCommand,
                retrieveProductDataCommand, outputDataCommand);

        try {
            scrapeHandler.execute(new ScrapeContext());
        } catch (Exception e) {
           LOG.severe("A problem occurred while collecting product data: ");
           e.printStackTrace();
        }
    }
}

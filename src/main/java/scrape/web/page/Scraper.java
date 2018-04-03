package scrape.web.page;

import scrape.web.page.command.Command;
import scrape.web.page.command.ScrapeContext;
import scrape.web.page.command.handler.ScrapeHandler;
import scrape.web.page.command.impl.LoadPropertiesCommand;

public class Scraper {

    public static void main(String args[]) {

        String propertiesFile = args[0];
        Command loadPropertiesCommand = new LoadPropertiesCommand(propertiesFile);
        ScrapeHandler scrapeHandler = new ScrapeHandler(loadPropertiesCommand);

        try {
            scrapeHandler.execute(new ScrapeContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

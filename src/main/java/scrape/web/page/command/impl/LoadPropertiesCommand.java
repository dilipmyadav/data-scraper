package scrape.web.page.command.impl;

import scrape.web.page.command.Command;
import scrape.web.page.command.ScrapeContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class LoadPropertiesCommand implements Command<ScrapeContext> {

    private static final Logger LOG =  Logger.getLogger("ParseHtmlLogger");
    private static final String URL = "url";
    private static final String SECTION = "section";

    private String propertiesFilePath;

    public LoadPropertiesCommand(String propertiesFilePath) {
        this.propertiesFilePath = propertiesFilePath;
    };

    @Override
    public void execute(ScrapeContext context) {
        Properties properties = new Properties();
        try {
            InputStream input = new FileInputStream(propertiesFilePath);
            properties.load(input);

            context.setUrl(properties.getProperty(URL));
            context.setSection(properties.getProperty(SECTION));

        } catch (FileNotFoundException e) {
            LOG.severe("Please pass in config properties file name as first argument while running the application");
            e.printStackTrace();
        } catch (IOException e) {
            LOG.severe("Please pass in a valid url in the properties file");
            e.printStackTrace();
        }
    }
}

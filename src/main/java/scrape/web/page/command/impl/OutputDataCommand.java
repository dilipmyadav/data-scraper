package scrape.web.page.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import scrape.web.page.command.Command;
import scrape.web.page.command.ScrapeContext;

import java.util.logging.Logger;

public class OutputDataCommand implements Command<ScrapeContext> {

    private static final Logger LOG = Logger.getLogger("OutputDataLogger");

    @Override
    public void execute(ScrapeContext context) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        LOG.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(context.getProductData()));
    }
}

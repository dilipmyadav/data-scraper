package scrape.web.page.command.handler;

import scrape.web.page.command.Command;
import scrape.web.page.command.ScrapeContext;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class ScrapeHandler {

    private final List<Command> commands = new ArrayList<>();

    public ScrapeHandler(Command... commands) {
        this.commands.addAll(asList(commands));
    }

    @SuppressWarnings("unchecked")
    public void execute(ScrapeContext context) throws Exception {
        for (Command command : commands) {
            command.execute(context);
        }
    }
}

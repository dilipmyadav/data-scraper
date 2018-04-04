package scrape.web.page.command;

public interface Command<T extends ScrapeContext> {
    void execute(T context) throws Exception;
}

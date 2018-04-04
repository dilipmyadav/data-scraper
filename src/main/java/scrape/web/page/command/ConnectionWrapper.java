package scrape.web.page.command;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ConnectionWrapper {

    public Document getDocument(String productDetailsPageLink) throws Exception {
        return Jsoup.connect(productDetailsPageLink).get();
    }
}

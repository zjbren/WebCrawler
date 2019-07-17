/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spider;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 *
 * @author zjbre
 */
public class SpiderLeg {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWeKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<Node> urls = new LinkedList<Node>();
    private List<String> images = new LinkedList<String>();
    private Document webPage;
    

    public boolean crawl(Node seedNode) {
        try {
            String seedUrl = seedNode.getUrl();
            System.out.println(seedUrl);
            Connection connection = Jsoup.connect(seedUrl).userAgent(USER_AGENT);
            Document webPage = connection.get();
            this.webPage = webPage;

            if (connection.response().statusCode() == 200) //200 is HTTP OK Status
            {
                System.out.println("\nSuccessfully received page: " + seedUrl);
            }
            if (!connection.response().contentType().contains("text/html")) {
                System.out.println("Failed, retrieved page was not HTML");
                return false;
            }

            Elements pageLinks = webPage.select("a[href]");
            int count = 0;
            for (Element link : pageLinks) {
                this.urls.add(new Node(seedNode.getDepth() + 1, link.absUrl("href")));
                count++;
                if(count == 5)
                {
                    break;
                }
            }

            return true;
        } catch (IOException e) {
            System.out.println("Error in HTTP request: " + e);
            return false;
        }
    }

    public boolean findKeyword(String keyword) {
        //checks if crawl has been completed first
        if (this.webPage == null) {
            System.out.println("Run Crawl first before searching");
            return false;
        }

        System.out.println("Searching for keyword " + keyword + "...");
        String body = this.webPage.body().text();
        return body.toLowerCase().contains(keyword.toLowerCase());
    }

    public List<Node> getHyperLinks() {
        return this.urls;
    }

    public String getTitle() {
        return this.webPage.title();
    }

    public Elements getImages() {
        return this.webPage.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
    }

    public Metadata getMeta() {
        if (!(webPage == null)) {
            String description = "";
            if (!(webPage.select("meta[name=description]").isEmpty())) {
                description = webPage.select("meta[name=description]").get(0).attr("content");
            }

            String keysString = "";
            if (!(webPage.select("meta[name=keywords]").isEmpty())) {
                keysString = webPage.select("meta[name=keywords]").first().attr("content");
            }

            String[] keys = keysString.split(",");

            Metadata metaKey = new Metadata(description, keys);
            return metaKey;
        }
        return new Metadata("empty", new String[]{});
    }
}


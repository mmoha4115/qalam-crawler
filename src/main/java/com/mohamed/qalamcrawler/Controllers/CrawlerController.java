package com.mohamed.qalamcrawler.Controllers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.HashMap;
//import static com.sun.activation.registries.LogSupport.log;

@Controller
public class CrawlerController {

    @RequestMapping(value = "")
    public String result() throws IOException {
        HashMap<String,String> titleAndHref = new HashMap<>();
        Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
//        log(doc.title());
        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
//            log("%s\n\t%s", headline.attr("title"), headline.absUrl("href"));

            System.out.println(headline.attr("title")+" : " +headline.absUrl("href"));
//            titleAndHref.put()
        }
        return "hello";
    }
}

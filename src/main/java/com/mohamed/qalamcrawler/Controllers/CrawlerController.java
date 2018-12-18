package com.mohamed.qalamcrawler.Controllers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.*;
//import static com.sun.activation.registries.LogSupport.log;

@Controller
public class CrawlerController {

    private static String titleUrl = "http://www.qalaminstitute.org/seerah/#.XBgmD2hKiM-";
    private static String titleCssQuery = ".nv-recent-posts h4 a";
    private static String hrefUrl = "http://www.qalaminstitute.org/podcast/audio/seerah/?C=M;O=D";
    private static String hrefCssQuery = "table a";

    @RequestMapping(value = "")
    public String result(Model model) throws IOException {
        List<String> titleAndHrefs = new ArrayList<>();
        Document doc = Jsoup.connect(hrefUrl).get();
        Elements newsHeadlines = doc.select(hrefCssQuery);
        for (Element headline : newsHeadlines) {
            titleAndHrefs.add(headline.absUrl("href"));
            System.out.println(headline.text()+" : " + headline.absUrl("href"));
        }
        System.out.println(titleAndHrefs);
        model.addAttribute("titleandhrefs",titleAndHrefs);
        return "seerah/index";
    }

    @RequestMapping(value = "/result")
    public String titlesAndHrefs(Model model)throws IOException{
        Map<String,String> titlesAndHrefs = new HashMap<>();
        List<String> titles = getTitle(getPageDoc(titleUrl,titleCssQuery));
        List<String> hrefs = getHref(getPageDoc(hrefUrl,hrefCssQuery));
        System.out.println(titles.size()+" : "+hrefs.size());
        Map<String,String> result = new HashMap<>();
        for (int i = 0; i < hrefs.size();i++){
            result.put(titles.get(i),hrefs.get(i));
        }
        model.addAttribute("result",result);
        return "seerah/result";
    }

    private Elements getPageDoc(String url,String cssQuery) throws IOException{
        Document doc = Jsoup.connect(url).get();
        Elements titleAndHref = doc.select(cssQuery);
        return titleAndHref;
    }

    private List<String> getTitle(Elements links){
        List<String> title = new ArrayList<>();
        for (Element link: links){
            title.add(link.text());
        }
        return title;
    }

    private List<String> getHref(Elements links){
        List<String> href = new ArrayList<>();
        for (Element link:links) {
            String ahref = link.absUrl("href");
            if (ahref.contains("seerah_")) {
                href.add(link.absUrl("href"));
                System.out.println(link.absUrl("href") + " :  ----this is audion");
            }
        }
        System.out.println(href.size());
        return href;
    }

}

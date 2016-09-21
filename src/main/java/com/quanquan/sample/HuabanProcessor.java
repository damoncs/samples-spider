package com.quanquan.sample;

/**
 * Created with IntelliJ IDEA.
 * User: ZQQ.
 * Date: 2016-08-01 0001 8:21.
 */
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
//import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class HuabanProcessor implements PageProcessor {

    private Site site;

    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("http://huaban\\.com/.*").all());
        if (page.getUrl().toString().contains("pins")) {
            page.putField("img", page.getHtml().xpath("//div[@id='pin_img']/a/img/@src").toString());
        } else {
            page.getResultItems().setSkip(true);
        }
    }

    public Site getSite() {
        if (null == site) {
            site = Site.me().setDomain("huaban.com").setSleepTime(0);
        }
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new HuabanProcessor()).thread(5)
                .addPipeline(new FilePipeline("/data/webmagic/test/"))
//                .setDownloader(new SeleniumDownloader("/Users/yihua/Downloads/chromedriver"))
                .addUrl("http://huaban.com/")
                .runAsync();
    }
}

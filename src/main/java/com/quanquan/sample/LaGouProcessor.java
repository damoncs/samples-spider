package com.quanquan.sample;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

/**
 * @author ZQQ
 * @since 0.5.0
 */
public class LaGouProcessor implements PageProcessor {

    private Site site = Site.me();

    private static final String ARITICALE_URL = "http://www.lagou.com/jobs/[\\d+].html";

    private static final String LIST_URL = "http://www.lagou.com/jobs/positionAjax.json?px=default&city=%E5%8C%97%E4%BA%AC&*";

    public void process(Page page) {
        for (int i = 0; i < 15; i++) {
            page.putField("positionName",
                    new JsonPathSelector("$.content.positionResult.result["+ i +"].positionName").select(page.getRawText()));
            page.putField("companyShortName",
                    new JsonPathSelector("$.content.positionResult.result["+ i +"].companyShortName").select(page.getRawText()));

        }


    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new LaGouProcessor())
                .addUrl("http://www.lagou.com/jobs/positionAjax.json?px=new&city=%E5%8C%97%E4%BA%AC&needAddtionalResult=false")
                .run();
    }
}

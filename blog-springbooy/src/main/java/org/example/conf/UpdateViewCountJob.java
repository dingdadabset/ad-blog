package org.example.conf;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.example.entity.SgArticle;
import org.example.service.SgArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SgArticleService articleService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");

        List<SgArticle> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new SgArticle(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库中
        if (articles.size()>0) {
            //articleService.updateBatchById(articles);
        }
    }
}
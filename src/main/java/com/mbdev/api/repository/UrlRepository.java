package com.mbdev.api.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
public class UrlRepository {
    private final Jedis jedis;
    private final String urlKey;
    private final String totalViewKey;
    private final String urlViewsKey;
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlRepository.class);

    public UrlRepository() {
        this.jedis = new Jedis();
        this.urlKey = "linkAddress";
        this.totalViewKey = "totalView";
        this.urlViewsKey = "urlViews";
    }

    public UrlRepository(Jedis jedis, String urlKey) {
        this.jedis = jedis;
        this.urlKey = urlKey;
        this.totalViewKey = "totalView";
        this.urlViewsKey = "urlViews";
    }

    public UrlRepository(Jedis jedis, String urlKey, String totalViewKey, String urlViewsKey) {
        this.jedis = jedis;
        this.urlKey = urlKey;
        this.totalViewKey = totalViewKey;
        this.urlViewsKey = urlViewsKey;
    }

    public Long incrementID() {
        
        Long id = jedis.incr(totalViewKey);
        LOGGER.info("Incrementing ID: {}", id-1);
        return id - 1;
    }

    public void incrementByShortenURL(String shortenUrl) {
        LOGGER.info("Increment : {}", shortenUrl);
        jedis.hincrBy(urlViewsKey, shortenUrl, 1);
    }
    
    public void saveUrl(String key, String longUrlAddress) {
        LOGGER.info("Saving: {} at {}", longUrlAddress, key);
        jedis.hset(urlKey, key, longUrlAddress);
    }

    public String getUrl(String id) throws Exception {
        LOGGER.info("Retrieving at {}", id);
        String url = jedis.hget(urlKey, id);
        LOGGER.info("Retrieved {} at {}", url ,id);
        if (url == null) {
            throw new Exception("URL at key" + id + " does not exist");
        }
        return url;
    }

}

package com.mbdev.api.service;

import com.mbdev.api.common.IdConverter;
import com.mbdev.api.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlConverterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlConverterService.class);
    private final UrlRepository urlRepository;
    private final int keyLength = 6;

    @Value("${domain}")
    private String newDomain;

    @Autowired
    public UrlConverterService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String generateShortenUrl(String longUrlAddress) {
        LOGGER.info("Shortening {}", longUrlAddress);
        urlRepository.incrementID();
        String uniqueID = IdConverter.generateKey(keyLength);

        String shortenedURL = newDomain + uniqueID;
        urlRepository.saveUrl(shortenedURL, longUrlAddress);

        return shortenedURL;
    }

    public String generateShortenUrl(String shortenUrlAddress, String longUrlAddress) throws Exception {
        LOGGER.info("Shortening {}", longUrlAddress);
        urlRepository.incrementID();
        urlRepository.saveUrl(shortenUrlAddress, longUrlAddress);
        return shortenUrlAddress;
    }

    public String getLongUrlByShortenUrl(String shortenURL) throws Exception {
        String longUrlAddress = urlRepository.getUrl(shortenURL);
        LOGGER.info("Converting shortening URL back to {}", longUrlAddress);
        return longUrlAddress;
    }

    public void incrementByURL(String shortenURL) throws Exception {
        urlRepository.incrementByShortenURL(shortenURL);
        LOGGER.info("Incremented shortened URL value to {}", shortenURL);
    }

}

package com.tj.urlShorteningService.service;

import com.google.common.hash.Hashing;
import com.tj.urlShorteningService.model.Url;
import com.tj.urlShorteningService.model.UrlDto;
import com.tj.urlShorteningService.repository.UrlRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class UrlServiceImplementation implements UrlService {

    @Autowired
    UrlRepository urlRepository;
    @Override
    public Url generateLink(UrlDto urlDto) {
        if(StringUtils.isNotEmpty(urlDto.getUrl())){
            String encodedUrl = encodeUrl(urlDto.getUrl());
            Url urlToPersist = new Url();
            urlToPersist.setCreationDate(LocalDateTime.now());
            urlToPersist.setOgUrl(urlDto.getUrl());
            urlToPersist.setShortLink(encodedUrl);
            urlToPersist.setExpirationDate(getExpDate(urlDto.getExpiryDate(),urlToPersist.getCreationDate()));
            Url urlReturn = persistLink(urlToPersist);
            if(urlReturn!=null){
                return urlReturn;
            }
        }
        return null;
    }

    private LocalDateTime getExpDate(String expiryDate, LocalDateTime creationDate) {
        if(StringUtils.isBlank(expiryDate)){
            return creationDate.plusSeconds(60);
        }
        LocalDateTime expirationDateReturn = LocalDateTime.parse(expiryDate);
        return expirationDateReturn;
    }



    private String encodeUrl(String url) {
        String encodedUrl = "";
        LocalDateTime time = LocalDateTime.now();
        encodedUrl = Hashing.murmur3_32().hashString(url.concat(time.toString()), StandardCharsets.UTF_8).toString();
        return encodedUrl;
    }

    @Override
    public Url persistLink(Url url) {
        Url urltoReturn = urlRepository.save(url);
        return urltoReturn;
    }

    @Override
    public Url getEncryptedUrl(String url) {
        Url urltoReturn = urlRepository.findByShortLink(url);
        return urltoReturn;
    }

    @Override
    public void deleteLnk(Url url) {
        urlRepository.delete(url);
    }
}

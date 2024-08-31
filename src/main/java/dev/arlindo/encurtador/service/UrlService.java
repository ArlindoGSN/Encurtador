package dev.arlindo.encurtador.service;

import dev.arlindo.encurtador.dto.ShortenUrlRequest;
import dev.arlindo.encurtador.entity.UrlEntity;
import dev.arlindo.encurtador.repository.UrlRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class UrlService {
    private UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrlgenerate(){
        String id;
        do{
            id = RandomStringUtils.randomAlphanumeric(5, 10);
        }while(urlRepository.existsById(id));
        return id;
    }

    public String getFullUrl(String id){
        var url = urlRepository.findById(id).isPresent() ? urlRepository.findById(id).get() : null;
        if (url == null) {
            return null;
        }
        return url.getFullUrl();
    }

   public String saveUrl(ShortenUrlRequest request) {
       String id = shortenUrlgenerate();
       Date expiresAt = Date.from(LocalDateTime.now().plusMinutes(2).atZone(ZoneId.systemDefault()).toInstant());
       urlRepository.save(new UrlEntity(id, request.url(), expiresAt));
       return id;
   }


}

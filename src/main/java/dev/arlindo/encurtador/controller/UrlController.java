package dev.arlindo.encurtador.controller;

import dev.arlindo.encurtador.dto.ShortenUrlRequest;
import dev.arlindo.encurtador.dto.ShortenUrlResponse;
import dev.arlindo.encurtador.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlController {

    private UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping(value = "/shorten-url")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@RequestBody ShortenUrlRequest request, HttpServletRequest servletRequest) {
        var shortenUrl = urlService.saveUrl(request);
        var url = servletRequest.getRequestURL().toString().replace("shorten-url", shortenUrl);
        return ResponseEntity.ok(new ShortenUrlResponse(url));
    }
    @GetMapping("{id}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String id) {

            var url = urlService.getFullUrl(id);

            if (url == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, url).build();
    }
}

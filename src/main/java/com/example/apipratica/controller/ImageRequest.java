package com.example.apipratica.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpHeaders.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpHeaders.*;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ImageRequest {
    private final String GITHUB_API_URL = "https://api.github.com/repos/cat-milk/Anime-Girls-Holding-Programming-Books/contents/";

    public String getFolderImages(String folderName) {
        String apiUrl = String.format("%s/%s", GITHUB_API_URL, folderName);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        return responseEntity.getBody();
    }

    public List<String> processImagesLinkJson(String imagesJson) throws JsonProcessingException {
        List<String> imageUrls = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(imagesJson);
        for (JsonNode imageNode : jsonNode) {
            String imageUrl = imageNode.get("download_url").asText();
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }

}

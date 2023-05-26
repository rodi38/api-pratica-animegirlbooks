package com.example.apipratica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ImageController {
    private ImageRequest imageRequest;
    public ImageController(ImageRequest imageRequest) {
        this.imageRequest = imageRequest;
    }

    @GetMapping("/images")
    public String getAllImages(Model model) {
        try {
            //List<String> folderList = imageRequest.getFolderList(); para pegar todas imagens, erro de limite de requisição
            List<String> folderList = List.of("C", "Python");
            List<String> imageUrls = new ArrayList<>();

            for (String folderName : folderList) {
                String imagesJson = imageRequest.getFolderImages(folderName);
                List<String> folderImageUrls = imageRequest.processImagesLinkJson(imagesJson);

                List<String> limitedImageUrls = folderImageUrls.stream().limit(10).toList();

                imageUrls.addAll(limitedImageUrls);
            }

            model.addAttribute("imageUrls", imageUrls);
            return "image-gallery";
        } catch (Exception e) {
            model.addAttribute("error", "Ocorreu algum erro ao obter imagens");
            return "error";
        }
    }

}

package io.harry.zealotboot.controller;

import io.harry.zealotboot.model.AjaeGag;
import io.harry.zealotboot.response.ZealotResponse;
import io.harry.zealotboot.service.AjaeGagService;
import io.harry.zealotboot.service.AjaeGagStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/ajae-gags")
public class AjaeGagController {
    private final AjaeGagService ajaeGagService;
    private final AjaeGagStorageService ajaeGagStorageService;

    @Autowired
    public AjaeGagController(AjaeGagService ajaeGagService, AjaeGagStorageService ajaeGagStorageService) {
        this.ajaeGagService = ajaeGagService;
        this.ajaeGagStorageService = ajaeGagStorageService;
    }

    @GetMapping
    public @ResponseBody
    ZealotResponse<List<AjaeGag>> getAjaeGags(@RequestParam boolean verified) {
        return new ZealotResponse<>(ajaeGagService.getAjaeGagList(verified));
    }

    @PostMapping
    public ZealotResponse<AjaeGag> postAjaeGags(@RequestParam("file") MultipartFile upload) {
        try {
            URL url = ajaeGagStorageService.uploadImage(upload.getInputStream());
            AjaeGag ajaeGag = ajaeGagService.createAjaeGag(new AjaeGag(url.toString()));

            return new ZealotResponse<>(ajaeGag);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ZealotResponse<>(null);
    }
}

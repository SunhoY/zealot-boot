package io.harry.zealotboot.controller;

import io.harry.zealotboot.model.AjaeGag;
import io.harry.zealotboot.response.ZealotResponse;
import io.harry.zealotboot.service.AjaeGagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/ajae-gags")
public class AjaeGagController {
    private final AjaeGagService ajaeGagService;

    @Autowired
    public AjaeGagController(AjaeGagService ajaeGagService) {
        this.ajaeGagService = ajaeGagService;
    }

    @RequestMapping(method = GET)
    public @ResponseBody
    ZealotResponse<List<AjaeGag>> getAjaeGags() {
        return new ZealotResponse<>(ajaeGagService.getAjaeGagList());
    }
}

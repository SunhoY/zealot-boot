package io.harry.zealotboot.service;

import io.harry.zealotboot.model.AjaeGag;
import io.harry.zealotboot.repository.AjaeGagRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AjaeGagService {
    private AjaeGagRepository ajaeGagRepository;

    @Autowired
    public AjaeGagService(AjaeGagRepository ajaeGagRepository) {
        this.ajaeGagRepository = ajaeGagRepository;
    }

    public List<AjaeGag> getAjaeGagList(boolean verified) {
        return ajaeGagRepository.findAllByVerifiedEqualsOrderByCreatedAtDesc(verified);
    }

    public AjaeGag createAjaeGag(AjaeGag ajaeGag) {
        AjaeGag saveThis = new AjaeGag(ajaeGag.getUrl(), new DateTime().getMillis(), false);

        return ajaeGagRepository.save(saveThis);
    }
}

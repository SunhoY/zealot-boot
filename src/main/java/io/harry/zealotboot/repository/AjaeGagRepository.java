package io.harry.zealotboot.repository;

import io.harry.zealotboot.model.AjaeGag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AjaeGagRepository extends MongoRepository<AjaeGag, String> {
    List<AjaeGag> findAllByVerifiedEqualsOrderByCreatedAtDesc(boolean verified);
}

package io.harry.zealotboot.repository;

import io.harry.zealotboot.model.AjaeGag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AjaeGagRepository extends MongoRepository<AjaeGag, String> {
}

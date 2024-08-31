package dev.arlindo.encurtador.repository;

import dev.arlindo.encurtador.entity.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<UrlEntity, String> {
}
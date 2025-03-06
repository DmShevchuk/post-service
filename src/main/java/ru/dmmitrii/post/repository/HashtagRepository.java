package ru.dmmitrii.post.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmmitrii.post.domain.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, UUID> {
    Optional<Hashtag> findByTag(String tag);
}

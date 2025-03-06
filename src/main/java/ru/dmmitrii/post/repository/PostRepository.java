package ru.dmmitrii.post.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmmitrii.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, UUID> {
    Page<Post> findByHashtagsTag(String tag, Pageable pageable);
}

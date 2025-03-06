package ru.dmmitrii.post.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dmmitrii.post.dto.request.CreatePostRequest;
import ru.dmmitrii.post.dto.response.PostResponse;
import ru.dmmitrii.post.service.PostService;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Tag(name = "Посты", description = "CRUD для постов и поиск по хэштегам")
public class PostController {

    private final PostService postService;

    @PostMapping
    @Operation(
            summary = "Создать пост",
            description = "Создаёт пост и автоматически извлекает хэштеги."
    )
    public PostResponse createPost(@RequestBody CreatePostRequest request) {
        return postService.createPost(request);
    }

    @GetMapping
    @Operation(
            summary = "Поиск постов по хэштегу",
            description = "Возвращает посты, содержащие указанный хэштег."
    )
    @RateLimiter(name = "myRateLimiter")
    @Cacheable(value = "postsByHashtag", key = "#hashtag", condition = "#hashtag != null")
    public Page<PostResponse> getPostsByHashtag(@RequestParam String hashtag,
                                                Pageable pageable) {
        return postService.getPostsByHashtag(hashtag, pageable);
    }

}

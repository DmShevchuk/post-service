package ru.dmmitrii.post.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmmitrii.post.domain.Hashtag;
import ru.dmmitrii.post.domain.Post;
import ru.dmmitrii.post.dto.request.CreatePostRequest;
import ru.dmmitrii.post.dto.response.PostResponse;
import ru.dmmitrii.post.repository.HashtagRepository;
import ru.dmmitrii.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final HashtagRepository hashtagRepository;

    private static final Pattern HASHTAG_PATTERN = Pattern.compile("#[\\wа-яА-ЯёЁ]+");

    @Transactional
    public PostResponse createPost(CreatePostRequest request) {
        var hashtagStrings = extractHashtags(request.content());

        var hashtags = hashtagStrings.stream()
                .map(this::getOrCreateHashtag)
                .collect(Collectors.toSet());

        var post = new Post();
        post.setContent(request.content());
        post.setHashtags(hashtags);
        post.setCreatedDate(ZonedDateTime.now());

        postRepository.save(post);
        return mapToPostResponse(post);
    }

    public Page<PostResponse> getPostsByHashtag(String hashtag, Pageable pageable) {
        var posts = postRepository.findByHashtagsTag(hashtag, pageable);
        return posts.map(this::mapToPostResponse);
    }

    private List<String> extractHashtags(String text) {
        var matcher = HASHTAG_PATTERN.matcher(text);
        List<String> hashtags = new ArrayList<>();
        while (matcher.find()) {
            hashtags.add(matcher.group().substring(1));
        }
        return hashtags;
    }

    private Hashtag getOrCreateHashtag(String tag) {
        return hashtagRepository.findByTag(tag)
                .orElseGet(() -> hashtagRepository.save(new Hashtag(tag)));
    }

    private PostResponse mapToPostResponse(Post post) {
        List<String> hashtags = post.getHashtags().stream()
                .map(Hashtag::getTag)
                .toList();
        return new PostResponse(post.getId(), post.getContent(), hashtags);
    }
}

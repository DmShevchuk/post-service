package ru.dmmitrii.post.dto.response;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public record PostResponse(UUID id, String content, List<String> hashtags) implements Serializable {
}

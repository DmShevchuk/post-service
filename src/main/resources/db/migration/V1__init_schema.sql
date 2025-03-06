CREATE TABLE IF NOT EXISTS hashtags
(
    id  UUID PRIMARY KEY,
    tag VARCHAR(255) NOT NULL UNIQUE
);


CREATE TABLE IF NOT EXISTS posts
(
    id         UUID PRIMARY KEY,
    content    TEXT,
    created_date TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS post_to_hashtags
(
    post_id    UUID NOT NULL,
    hashtag_id UUID NOT NULL,
    PRIMARY KEY (post_id, hashtag_id),
    FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
    FOREIGN KEY (hashtag_id) REFERENCES hashtags (id) ON DELETE CASCADE
);

package com.github.swapnil.reddit.service;

import com.github.swapnil.reddit.dto.PostRequest;
import com.github.swapnil.reddit.dto.PostResponse;
import com.github.swapnil.reddit.mapper.PostMapper;
import com.github.swapnil.reddit.model.Post;
import com.github.swapnil.reddit.model.Subreddit;
import com.github.swapnil.reddit.model.User;
import com.github.swapnil.reddit.repository.PostRepository;
import com.github.swapnil.reddit.repository.SubredditRepository;
import com.github.swapnil.reddit.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class PostServiceTest {

    @Mock private SubredditRepository subredditRepo;

    @Mock private AuthService authSvc;

    @Mock private PostMapper postMapper;

    @Mock private PostRepository postRepo;

    @Mock private UserRepository userRepo;

    @InjectMocks private PostService postService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should Find Post By Id")
    public void shouldFindPostById() {
        when(postRepo.findById(123L)).thenReturn(Optional.of(DUMMY_DATA.post));
        when(postMapper.mapToDto(any(Post.class))).thenReturn(DUMMY_DATA.expectedRespForDummyPost);

        PostResponse actualPostResponse = postService.getPost(123L);

        assertThat(actualPostResponse.getId()).isEqualTo(DUMMY_DATA.expectedRespForDummyPost.getId());
        assertThat(actualPostResponse.getPostName()).isEqualTo(DUMMY_DATA.expectedRespForDummyPost.getPostName());
    }

    @Test
    @DisplayName("Should save the post")
    public void shouldSaveThePost() {
        when(subredditRepo.findByName("First Subreddit")).thenReturn(Optional.of(DUMMY_DATA.subreddit));
        when(postMapper.map(DUMMY_DATA.postRequest, DUMMY_DATA.subreddit, DUMMY_DATA.user)).thenReturn(DUMMY_DATA.post);
        when(authSvc.getCurrentUser()).thenReturn(DUMMY_DATA.user);

        postService.save(DUMMY_DATA.postRequest);
        verify(postRepo, times(1)).save(ArgumentMatchers.any(Post.class));
    }

    private final static class DUMMY_DATA {
        private final static Post post;

        private final static PostResponse expectedRespForDummyPost;

        private final static User user;

        private final static Subreddit subreddit;

        private final static PostRequest postRequest;

        static {
            post = Post.builder()
                    .id(123L).postName("Dummy Post").url("http://url.site").description("Test").voteCount(0)
                    .user(null).createdDate(Instant.now()).subreddit(null)
                    .build();

            expectedRespForDummyPost = PostResponse.builder()
                    .id(123L).postName("Dummy Post").url("http://url.site").description("Test").voteCount(0)
                    .userName("Test User").subredditName("Test Subreddit").commentCount(0).duration("1 Hour Ago")
                    .upVote(false).downVote(false)
                    .build();

            user = new User(123L, "test user", "secret password", "user@email.com", Instant.now(), true);
            subreddit = new Subreddit(123L, "First Subreddit", "Subreddit Description", Collections.emptyList(), Instant.now(), user);
            postRequest = new PostRequest(null, "First Subreddit", "First Post", "http://url.site", "Test");
        }
    }
}
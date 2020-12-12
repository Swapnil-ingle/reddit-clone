package com.github.swapnil.reddit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;


public class CommentsServiceTest {
    @InjectMocks
    private CommentsService commentsService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Comments should not contain any swear words - Input does NOT contains swear words")
    public void testContainsSwearWords_DoesNotContains() {
        String comment = "This is a clean comment";
        assertThat(commentsService.containsSwearWords(comment)).isFalse();
    }

    @Test
    @DisplayName("Comments should not contain any swear words - Input is empty")
    public void testContainsSwearWords_EmptyInput() {
        String comment = "";
        assertThat(commentsService.containsSwearWords(comment)).isFalse();
    }

    @Test
    @DisplayName("Comments should not contain any swear words - Input DOES contains swear words")
    public void testContainsSwearWords_DoesContains() {
        String comment = "This is a shitty comment";
        assertThat(commentsService.containsSwearWords(comment)).isTrue();

        String comment2 = "This is a fucked up comment";
        assertThat(commentsService.containsSwearWords(comment2)).isTrue();
    }
}
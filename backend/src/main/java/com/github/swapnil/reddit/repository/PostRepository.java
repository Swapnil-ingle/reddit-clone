package com.github.swapnil.reddit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.swapnil.reddit.model.Post;
import com.github.swapnil.reddit.model.Subreddit;
import com.github.swapnil.reddit.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllBySubreddit(Subreddit subreddit);

	List<Post> findByUser(User user);
}

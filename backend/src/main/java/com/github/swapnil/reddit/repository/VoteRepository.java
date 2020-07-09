package com.github.swapnil.reddit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.swapnil.reddit.model.Post;
import com.github.swapnil.reddit.model.User;
import com.github.swapnil.reddit.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
	Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User currentUser);
}

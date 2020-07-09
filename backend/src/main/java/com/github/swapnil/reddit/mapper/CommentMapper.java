package com.github.swapnil.reddit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.github.swapnil.reddit.dto.CommentsDto;
import com.github.swapnil.reddit.model.Comment;
import com.github.swapnil.reddit.model.Post;
import com.github.swapnil.reddit.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "text", source = "commentsDto.text")
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "post", source = "post")
	Comment map(CommentsDto commentsDto, User user, Post post);

	@Mapping(target = "postId", source = "post.id")
	@Mapping(target = "username", source = "user.username")
	CommentsDto mapToDto(Comment comment);
}

package telran.java31.forum.service;

import java.time.LocalDate;
import java.util.List;

import telran.java31.forum.dto.CommentDto;
import telran.java31.forum.dto.DatePeriodDto;
import telran.java31.forum.dto.PostDto;
import telran.java31.forum.dto.PostResponseDto;

public interface ForumService {

	PostResponseDto addPost(String author, PostDto postDto);

	PostResponseDto findPostById(String id);

	PostResponseDto deletePost(String id);

	PostResponseDto updatePost(String id, PostDto postDto);

	boolean addLikeToPost(String id);

	PostResponseDto addCommentToPost(String id, String author, CommentDto commentDto);

	List<PostResponseDto> findPostByAuthor(String author);

	Iterable<PostResponseDto> findPostsByTags(List<String> tags);

	Iterable<PostResponseDto> findPostsCreatedBetweenDates(DatePeriodDto datePeriodDto);

}

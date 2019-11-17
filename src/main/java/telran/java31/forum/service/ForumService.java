package telran.java31.forum.service;

import java.util.List;

import telran.java31.forum.dto.CommentDto;
import telran.java31.forum.dto.DatePeriodDto;
import telran.java31.forum.dto.NewCommentDto;
import telran.java31.forum.dto.NewPostDto;
import telran.java31.forum.dto.PostDto;

public interface ForumService {
	PostDto addNewPost(NewPostDto newPost, String author);

	PostDto getPost(String id);

	PostDto removePost(String id);

	PostDto updatePost(NewPostDto postUpdateDto, String id);

	boolean addLike(String id);

	PostDto addComment(String id, String author, NewCommentDto newCommentDto);

	Iterable<PostDto> findPostsByAuthor(String author);
	
	Iterable<PostDto> findPostsByTags(List<String> tags);
	
	Iterable<PostDto> findPostsCreatedBetweenDates(DatePeriodDto datePeriodDto);
	
	Iterable<CommentDto> findAllPostComments(String id);
	
	Iterable<CommentDto> findAllPostCommentsByAuthor(String id, String author);

}

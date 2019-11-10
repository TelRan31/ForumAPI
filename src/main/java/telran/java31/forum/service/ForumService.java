package telran.java31.forum.service;

import java.util.List;

import telran.java31.forum.dto.CommentDto;
import telran.java31.forum.dto.PostDto;
import telran.java31.forum.dto.PostResponseDto;

public interface ForumService {

	public PostResponseDto addPost(String author, PostDto postDto);

	public PostResponseDto findPostById(String id);

	public PostResponseDto deletePost(String id);

	public PostResponseDto updatePost(String id, PostDto postDto);

	public boolean addLikeToPost(String id);

	public PostResponseDto addCommentToPost(String id, String author, CommentDto commentDto);

	public List<PostResponseDto> findPostByAuthor(String author);

}

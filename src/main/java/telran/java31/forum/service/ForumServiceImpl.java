package telran.java31.forum.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.java31.forum.dao.ForumRepository;
import telran.java31.forum.dto.CommentDto;
import telran.java31.forum.dto.PostDto;
import telran.java31.forum.dto.PostNotFoundException;
import telran.java31.forum.dto.PostResponseDto;
import telran.java31.forum.model.Comment;
import telran.java31.forum.model.Post;

@Service
public class ForumServiceImpl implements ForumService {
	@Autowired
	ForumRepository forumRepository;

	@Override
	public PostResponseDto addPost(String author, PostDto postDto) {
		Post post = new Post(postDto.getTitle(), postDto.getContent(), author, postDto.getTags());
		forumRepository.save(post);
		return postToPostResponseDto(post);
	}

	private PostResponseDto postToPostResponseDto(Post post) {
		return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(),
				post.getAuthor(), post.getDateCreated(), post.getTags(), post.getLikes(), post.getComments());
	}

	@Override
	public PostResponseDto findPostById(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		return postToPostResponseDto(post);
	}

	@Override
	public PostResponseDto deletePost(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		forumRepository.delete(post);
		return postToPostResponseDto(post);
	}

	@Override
	public PostResponseDto updatePost(String id, PostDto postDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		if (post.getTitle() != null) {
			post.setTitle(postDto.getTitle());
		}
		if (post.getContent() != null) {
			post.setContent(postDto.getContent());
		}
		if (post.getTags() != null) {
			post.setTags(postDto.getTags());
		}
		forumRepository.save(post);
		return postToPostResponseDto(post);
	}

	@Override
	public boolean addLikeToPost(String id) {
		if (forumRepository.existsById(id)) {
			Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
			post.addLikePost();
			forumRepository.save(post);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public PostResponseDto addCommentToPost(String id, String author, CommentDto commentDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		Comment comment = new Comment(author, commentDto.getMassege());
		post.addComment(comment);
		forumRepository.save(post);
		return postToPostResponseDto(post);
	}

	@Override
	public List<PostResponseDto> findPostByAuthor(String author) {

		return forumRepository.findByAuthor(author).map(this::postToPostResponseDto).collect(Collectors.toList());
	}

}

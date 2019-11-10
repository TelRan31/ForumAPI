package telran.java31.forum.dao;

import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;

import telran.java31.forum.model.Post;

public interface ForumRepository extends CrudRepository<Post, String > {

	Stream<Post> findByAuthor(String author);
}

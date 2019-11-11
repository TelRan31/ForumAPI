package telran.java31.forum.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;

import telran.java31.forum.model.Post;

public interface ForumRepository extends CrudRepository<Post, String> {

	Stream<Post> findByAuthor(String author);

	Stream<Post> findByTagsIn(List<String> tags);

	Stream<Post> findByDateCreatedBetween(LocalDate from, LocalDate to);
}

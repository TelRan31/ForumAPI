package telran.java31.forum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = { "id" })
@ToString
public class Post {
	@Id
	String id;
	@Setter
	String title;
	@Setter
	String content;
	String author;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime dateCreated;
	@Setter
	Set<String> tags;
	int likes;
	List<Comment> comments;

	public Post(String title, String content, String author, Set<String> tags) {
		id = new ObjectId().toString();
		this.title = title;
		this.content = content;
		this.author = author;
		this.tags = tags;
		dateCreated = LocalDateTime.now();
		comments = new ArrayList<Comment>();
	}

	public void addLikePost() {
		likes++;
	}
	public void addComment(Comment comment) {
		comments.add(comment);
	}

}

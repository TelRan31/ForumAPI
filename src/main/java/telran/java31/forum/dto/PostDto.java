package telran.java31.forum.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDto {
	String title;
	String content;
	String [] tags;

}

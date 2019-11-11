package telran.java31.forum.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDto {
	String title;
	String content;
	Set<String> tags;

}

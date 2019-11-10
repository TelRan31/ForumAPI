package telran.java31.forum.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
public class PostNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PostNotFoundException(String id) {
		super("Post " + id);
	}
}

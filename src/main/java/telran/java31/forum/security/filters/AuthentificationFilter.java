package telran.java31.forum.security.filters;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import telran.java31.forum.configuration.AccountConfiguration;
import telran.java31.forum.configuration.UserCredentials;
import telran.java31.forum.dao.UserAccountRepository;
import telran.java31.forum.model.UserAccount;

@Service
@Order(10)
public class AuthentificationFilter implements Filter {

	@Autowired
	UserAccountRepository accountRepository;

	@Autowired
	AccountConfiguration accountConfiguration;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String path = request.getServletPath();
		String method = request.getMethod();
		String auth = request.getHeader("Authorization");
		UserCredentials userCredentials = null;
		if (!checkPointCut(path, method)) {

			try {
				userCredentials = accountConfiguration.tokenDecode(auth);
			} catch (Exception e) {
				response.sendError(401, "Header not found");
				return;
			}
			UserAccount userAccount = accountRepository.findById(userCredentials.getLogin()).orElse(null);
			if (userAccount == null) {
				response.sendError(401, "User not found");
				return;
			}
			if (!BCrypt.checkpw(userCredentials.getPassword(), userAccount.getPassword())) {
				response.sendError(403, "Password is not valid");
				return;
			}
			chain.doFilter(new WrapperRequest(request, userCredentials.getLogin()), response);
			return;
		}

		chain.doFilter(request, response);
	}

	private boolean checkPointCut(String path, String method) {

		boolean check = "/account/user".equalsIgnoreCase(path) && "Post".equalsIgnoreCase(method);
		check = check || path.startsWith("/forum/posts");
		return check;

	}

	private class WrapperRequest extends HttpServletRequestWrapper {
		String user;

		public WrapperRequest(HttpServletRequest request, String user) {
			super(request);
			this.user = user;
		}

		@Override
		public Principal getUserPrincipal() {
			return new Principal() {

				@Override
				public String getName() {
					// TODO Auto-generated method stub
					return user;
				}
			};

		}

	}

}

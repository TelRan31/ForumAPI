package telran.java31.forum.security.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import telran.java31.forum.dao.UserAccountRepository;
import telran.java31.forum.model.UserAccount;

@Service
@Order(30)
public class AdminAuthorizationFilter implements Filter {

	@Autowired
	UserAccountRepository accountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String path = request.getServletPath();
		String method = request.getMethod();

		if (checkPointCut(path, method)) {
			String login = request.getUserPrincipal().getName();
			UserAccount userAccount = accountRepository.findById(login).get();
			if (!userAccount.getRoles().contains("Administrator")) {
				response.sendError(403, "User is not admin");
				return;
			}
		}
		chain.doFilter(request, response);

	}

	private boolean checkPointCut(String path, String method) {
		String[] pathes = path.split("//");
		boolean check = pathes.length == 3 && "account".equalsIgnoreCase(pathes[0]) && ("Put").equalsIgnoreCase(method)
				|| ("Delete").equalsIgnoreCase(method);
		return check;
	}

}

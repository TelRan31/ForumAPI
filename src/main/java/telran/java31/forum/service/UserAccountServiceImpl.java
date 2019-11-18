package telran.java31.forum.service;

import java.time.LocalDateTime;
import java.util.Set;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.java31.forum.configuration.AccountConfiguration;
import telran.java31.forum.configuration.UserCredentials;
import telran.java31.forum.dao.UserAccountRepository;
import telran.java31.forum.dto.NewUserDto;
import telran.java31.forum.dto.UserEditDto;
import telran.java31.forum.dto.UserProfileDto;
import telran.java31.forum.exceptions.ForbiddenException;
import telran.java31.forum.exceptions.UserAutenticationException;
import telran.java31.forum.exceptions.UserExistsException;
import telran.java31.forum.model.UserAccount;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	@Autowired
	UserAccountRepository accountRepository;
	
	@Autowired
	AccountConfiguration accountConfiguration;

	@Override
	public UserProfileDto register(NewUserDto newUserDto) {
		if (accountRepository.existsById(newUserDto.getLogin())) {
			throw new UserExistsException();
		}
		String hashPassword = BCrypt.hashpw(newUserDto.getPassword(), BCrypt.gensalt());
		UserAccount userAccount = UserAccount.builder()
									.login(newUserDto.getLogin())
									.password(hashPassword)
									.firstName(newUserDto.getFirstName())
									.lastName(newUserDto.getLastName())
									.role("User")
									.expDate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod()))
									.build();
		accountRepository.save(userAccount);
		return userAccountToUserProfileDto(userAccount);
	}

	private UserProfileDto userAccountToUserProfileDto(UserAccount userAccount) {
		return UserProfileDto.builder()
				.login(userAccount.getLogin())
				.firstName(userAccount.getFirstName())
				.lastName(userAccount.getLastName())
				.roles(userAccount.getRoles())
				.build();
	}

	@Override
	public UserProfileDto findUser(String token) {
		UserCredentials userCredentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = accountRepository.findById(userCredentials.getLogin())
				.orElseThrow(UserAutenticationException::new);
		if (!BCrypt.checkpw(userCredentials.getPassword(), userAccount.getPassword())) {
			throw new ForbiddenException();
		}
		return userAccountToUserProfileDto(userAccount);
	}

	@Override
	public UserProfileDto removeUser(String token) {
		UserCredentials userCredentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = accountRepository.findById(userCredentials.getLogin())
				.orElseThrow(UserAutenticationException::new);
		if (!BCrypt.checkpw(userCredentials.getPassword(), userAccount.getPassword())) {
			throw new ForbiddenException();
		}
		accountRepository.delete(userAccount);
		return userAccountToUserProfileDto(userAccount);
	}

	@Override
	public UserProfileDto editUser(UserEditDto userEditDto, String token) {
		UserCredentials userCredentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = accountRepository.findById(userCredentials.getLogin())
				.orElseThrow(UserAutenticationException::new);
		if (!BCrypt.checkpw(userCredentials.getPassword(), userAccount.getPassword())) {
			throw new ForbiddenException();
		}
		if (userEditDto.getFirstName() != null) {
			userAccount.setFirstName(userEditDto.getFirstName());
		}
		if (userEditDto.getLastName() != null) {
			userAccount.setLastName(userEditDto.getLastName());
		}
		accountRepository.save(userAccount);
		return userAccountToUserProfileDto(userAccount);
	}

	@Override
	public Set<String> addRole(String login, String role, String token) {
		if (!isAdmin(token)) {
			throw new ForbiddenException();
		}
		UserAccount userAccount = accountRepository.findById(login)
				.orElseThrow(() -> new UserExistsException());
		userAccount.addRole(role);
		return userAccount.getRoles();
	}

	@Override
	public Set<String> removeRole(String login, String role, String token) {
		if (!isAdmin(token)) {
			throw new ForbiddenException();
		}
		UserAccount userAccount = accountRepository.findById(login)
				.orElseThrow(() -> new UserExistsException());
		userAccount.removeRole(role);
		return userAccount.getRoles();
	}
	
	private boolean isAdmin(String token) {
		UserCredentials userCredentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = accountRepository.findById(userCredentials.getLogin())
				.orElseThrow(UserAutenticationException::new);
		if (!BCrypt.checkpw(userCredentials.getPassword(), userAccount.getPassword())) {
			throw new ForbiddenException();
		}
		return userAccount.getRoles().contains("Administrator");
	}

	@Override
	public void changePassword(String token, String password) {
		UserCredentials userCredentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = accountRepository.findById(userCredentials.getLogin())
				.orElseThrow(UserAutenticationException::new);
		if (!BCrypt.checkpw(userCredentials.getPassword(), userAccount.getPassword())) {
			throw new ForbiddenException();
		}
		String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		userAccount.setPassword(hashPassword);
		userAccount.setExpDate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod()));
		accountRepository.save(userAccount);
	}

}
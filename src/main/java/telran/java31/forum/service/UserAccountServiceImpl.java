package telran.java31.forum.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.java31.forum.dao.UserAccountRepository;
import telran.java31.forum.dto.NewUserDto;
import telran.java31.forum.dto.UserProfileDto;
import telran.java31.forum.exceptions.UserExistsException;
import telran.java31.forum.model.UserAccount;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	@Autowired
	UserAccountRepository accountRepository;
	
	long expPeriod = 90;

	@Override
	public UserProfileDto register(NewUserDto newUserDto) {
		if (accountRepository.existsById(newUserDto.getLogin())) {
			throw new UserExistsException();
		}
		UserAccount userAccount = UserAccount.builder()
									.login(newUserDto.getLogin())
									.password(newUserDto.getPassword())
									.firstName(newUserDto.getFirstName())
									.lastName(newUserDto.getLastName())
									.role("User")
									.expDate(LocalDateTime.now().plusDays(expPeriod))
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

}

package telran.java31.forum.service;

import java.util.Set;

import telran.java31.forum.dto.NewUserDto;
import telran.java31.forum.dto.UserEditDto;
import telran.java31.forum.dto.UserProfileDto;

public interface UserAccountService {

	UserProfileDto register(NewUserDto newUserDto);

	UserProfileDto findUser(String login);

	UserProfileDto removeUser(String login);

	UserProfileDto editUser(UserEditDto userEditDto, String login);

	Set<String> addRole(String login, String role);

	Set<String> removeRole(String login, String role);

	void changePassword(String login, String password);
}

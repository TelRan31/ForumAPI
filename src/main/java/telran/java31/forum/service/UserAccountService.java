package telran.java31.forum.service;

import telran.java31.forum.dto.NewUserDto;
import telran.java31.forum.dto.UserProfileDto;

public interface UserAccountService {
	
	UserProfileDto register(NewUserDto newUserDto);
}

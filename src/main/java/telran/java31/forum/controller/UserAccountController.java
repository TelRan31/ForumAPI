package telran.java31.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.java31.forum.dto.NewUserDto;
import telran.java31.forum.dto.UserProfileDto;
import telran.java31.forum.service.UserAccountService;

@RestController
@RequestMapping("/account")
public class UserAccountController {
	
	@Autowired
	UserAccountService userAccountService;
	
	@PostMapping("/user")
	public UserProfileDto register(@RequestBody NewUserDto newUserDto) {
		return userAccountService.register(newUserDto);
	}
	
	@PostMapping("/login")
	public UserProfileDto userLogin(@RequestHeader("Authorization") String token) {
		return userAccountService.findUser(token);
	}
	

}

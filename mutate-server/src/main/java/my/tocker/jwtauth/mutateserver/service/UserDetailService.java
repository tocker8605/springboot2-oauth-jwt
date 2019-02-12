package my.tocker.jwtauth.mutateserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

import my.tocker.jwtauth.mutateserver.domain.LoginUser;
import my.tocker.jwtauth.database.domain.User;
import my.tocker.jwtauth.database.repository.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {

	private UserRepository userRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("UsernameNotFound [" + username + "]");
		}

		return createUser(user);
	}
	
	private LoginUser createUser(User user) {
		LoginUser loginUser = new LoginUser(user);

		switch (loginUser.getUserType()) {
			case "3":
				loginUser.setRoles(Collections.singletonList("ROLE_TOCKER"));
				break;
			case "2":
				loginUser.setRoles(Collections.singletonList("ROLE_ADMIN"));
				break;
			default:
				loginUser.setRoles(Collections.singletonList("ROLE_USER"));
				break;
		}

		return loginUser;
	}

}

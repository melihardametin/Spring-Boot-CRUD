package com.root.project_2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	MessageRepository messageRepository;


	@GetMapping("/error_page")
	public String getErrorPage() {
		return "error_page";
	}

	@GetMapping("/login")
	public String getLoginPage() {
		return "login_page";
	}

	@GetMapping("/user")
	public String getUserPage() {
		return "user_page";
	}

	@GetMapping("/adminuser")
	public String getAdminUserPage() {
		return "adminuser_page";
	}

	@GetMapping("/register")
	public String getRegisterPage() {
		return "register_page";
	}

	@GetMapping("/adminuser/add_user")
	public String getAddUser() {
		return "adduser_page";
	}


	@GetMapping({"/user/send", "/adminuser/send"})
	public String sendMessage() {
		return "send_page";
	}

	@GetMapping({"/user/outbox", "/adminuser/outbox"})
	public String getOutbox() {
		return "outbox_page";
	}

	@GetMapping("/adminuser/delete_user")
	public String getdeleteUser() {
		return "deleteuser_page";
	}

	@GetMapping("/adminuser/list_users")
	public String getListUsers() {
		return "list_page";
	}

	@PutMapping("/adminuser")
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
		token = token.substring(7);
		try {
			User userData = userRepository.findFirstByToken(token);
			if (userData == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			userData.setToken("x");
			userRepository.save(userData);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	///////////////////////////////////


	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		if (user == null) {
			return ResponseEntity.ok("login_page");
		}
		try {
			User userData = userRepository.findFirstByUsername(user.getUsername());
			if (userData != null) {
				if (userData.getPassword().equals(user.getPassword())) {
					RandomString a = new RandomString();
					String token = a.nextString();
					userData.setToken(token);
					userRepository.save(userData);
					if (userData.getAdmin()) return new ResponseEntity<>(token, HttpStatus.OK);
					else return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
				}
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping({"/adminuser/get_userId", "/user/get_userId"})
	public ResponseEntity<String> getUsername(@RequestHeader("Authorization") String token) {
		token = token.substring(7);
		try {
			User u = userRepository.findFirstByToken(token);
			return new ResponseEntity<>(u.getUsername(), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>("nanana", HttpStatus.OK);
		}
	}


	@DeleteMapping("/adminuser/delete_user")
	public ResponseEntity<HttpStatus> deleteUser(@RequestHeader("Authorization") String token, @RequestBody User user) {
		token = token.substring(7);
		try {

			// Perform token validation logic here
			User curr_user = userRepository.findFirstByToken(token);
			if (curr_user == null || !curr_user.getAdmin()) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			User _user = userRepository.findFirstByUsername(user.getUsername());
			if (_user == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			userRepository.delete(_user);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/adminuser/list_users")
	public ResponseEntity<List<User>> listUsers(@RequestHeader("Authorization") String token) {
		token = token.substring(7);
		try {
			// Perform token validation logic here
			User curr_user = userRepository.findFirstByToken(token);
			if (curr_user == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			List<User> messages = userRepository.findAll();

			if (messages.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(messages, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping("/adminuser/add_user")
	public ResponseEntity<User> addUser(@RequestBody User user, @RequestHeader("Authorization") String token) {
		token = token.substring(7);
		try {
			// Perform token validation logic here
			User curr_user = userRepository.findFirstByToken(token);
			if (curr_user == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			User _user = userRepository.save(new User(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getDate(), user.getGender(), user.getMail(), user.getCity(), user.getAdmin()));
			return new ResponseEntity<>(_user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		try {
			User _user = userRepository.save(new User(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getDate(), user.getGender(), user.getMail(), user.getCity(), user.getAdmin()));
			return new ResponseEntity<>(_user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}


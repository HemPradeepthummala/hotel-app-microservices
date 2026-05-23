package org.example.hotelauth.service;



import org.example.hotelauth.exception.UserNotFoundException;
import org.example.hotelauth.model.User;
import org.example.hotelauth.repository.UserRepository;
import org.example.hotelauth.view.LoginRequest;
import org.example.hotelauth.view.SignUpRequest;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class AppUserDetailsService implements UserDetailsService {

  private final  UserRepository userRepository;
  private final IdGenerator idGenerator;

  public AppUserDetailsService(UserRepository userRepository, IdGenerator idGenerator) {
    this.userRepository = userRepository;
    this.idGenerator = idGenerator;
  }

  @Override
  public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
    UserDetails userDetails = userRepository.findUserByUsername(username);
    if (userDetails == null) throw new UsernameNotFoundException("user doesn't exist");
    return userDetails;
  }

  @Transactional
  public String registerUser(SignUpRequest userDetails) {
    User user = new User(idGenerator.generate(), userDetails.username(), userDetails.password());
    this.userRepository.save(user);

    return this.userRepository.findUserIdByUsername(userDetails.username());
  }

  public PasswordEncoder passWordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public String loginUser(LoginRequest loginRequest) throws UserNotFoundException {
    UserDetails user = this.userRepository.findUserByUsername(loginRequest.username());
    String storedPassword = user.getPassword();

    assert storedPassword != null;
    if (!storedPassword.equals(loginRequest.password())) {
      throw new UserNotFoundException("");
    }
    return user.getUsername();
  }

  public String getUserId(String user) {
    String userId = this.userRepository.findUserIdByUsername(user);
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(userId);
    String id = jsonNode.get("_id").asText();
    return id;
  }
}

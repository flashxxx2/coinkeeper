//package payments.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import payments.entity.UserEntity;
//import payments.repository.UsersRepository;
//
//@RequiredArgsConstructor
//@Service
//public class UserService implements UserDetailsService {
//
//    private final UsersRepository repository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserEntity user = repository.findByUsername(username);
//        if(user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        return new User(user.getUsername(), user.getPassword(), null);
//    }
//}

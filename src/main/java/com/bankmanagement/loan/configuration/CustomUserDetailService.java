package com.bankmanagement.loan.configuration;

import com.bankmanagement.loan.entity.User;
import com.bankmanagement.loan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        optionalUser.orElseThrow(()->new UsernameNotFoundException("User Details Not available"));
        return optionalUser.map(CustomUseDetails::new).get();
    }
}

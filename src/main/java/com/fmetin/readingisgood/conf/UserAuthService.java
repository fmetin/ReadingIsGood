package com.fmetin.readingisgood.conf;

import com.fmetin.readingisgood.entity.Customer;
import com.fmetin.readingisgood.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Autowired
    public UserAuthService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(username);
        if (customer == null)
            throw new UsernameNotFoundException("User not found");
        return new CustomerUserDetails(customer);
    }
}

package it.epicode.valhallagaming.service;

import it.epicode.valhallagaming.entity.Admin;
import it.epicode.valhallagaming.repository.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    public AdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
        return User.builder()
                .username(admin.getUserName())
                .password(admin.getPassword())
                .roles("ADMIN")
                .build();
    }
}

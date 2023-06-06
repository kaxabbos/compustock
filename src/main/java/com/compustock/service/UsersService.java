package com.compustock.service;

import com.compustock.model.Users;
import com.compustock.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService implements UserDetailsService {

    private final UsersRepo usersRepo;

    @Autowired
    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepo.findByUsername(username);
    }

    public void add(Users users) {
        usersRepo.save(users);
    }
    public Users addAndFlush(Users users) {
        return usersRepo.saveAndFlush(users);
    }

    public void update(Users users) {
        usersRepo.save(users);
    }

    public void delete(Long id) {
        usersRepo.deleteById(id);
    }

    public void delete(Users users) {
        usersRepo.delete(users);
    }

    public Users find(Long id) {
        return usersRepo.findUserById(id);
    }

    public List<Users> findAllByOrderByRole() {
        return usersRepo.findAllByOrderByRole();
    }

    public Users findByUsername(String username) {
        return usersRepo.findByUsername(username);
    }

    public List<Users> findAll() {
        return usersRepo.findAll();
    }
}

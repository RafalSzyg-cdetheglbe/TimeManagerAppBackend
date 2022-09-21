package com.start.timemanager.service.implementation;

import com.start.timemanager.dto.UserDTO;
import com.start.timemanager.error.NotFoundException;
import com.start.timemanager.model.*;
import com.start.timemanager.repository.BucketMemberRepository;
import com.start.timemanager.repository.BucketRepository;
import com.start.timemanager.repository.TaskMemberRepository;
import com.start.timemanager.service.interfaces.IUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.start.timemanager.repository.UserRepository;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService, UserDetailsService{
    private final UserRepository userRepository;
    private final ModificationHistoryService modificationHistoryService;
    private final BucketRepository bucketRepository;
    private final BucketService bucketService;
    private final BucketMemberService bucketMemberService;
    private final TaskMemberService taskMemberService;
    private final BucketMemberRepository bucketMemberRepository;
    private final TaskMemberRepository taskMemberRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if(user == null){
            log.error("User Not Found In The Database");
            throw new UsernameNotFoundException("User Not Found In The Database");
        }else{
            log.info("User Found In The Database {}", username);
        }
        
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            // user.isActive(),
            // true,
            // true,
            // true,
            grantAuthorities(user.getUserRole().getName()));
        }

    public Collection<? extends GrantedAuthority> grantAuthorities(String role) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.toUpperCase()));
            return authorities;
        }

    public List<User> getUsers() {//get not deleted users

        return userRepository.findAll();

    }

    public User getUser(String username) {
        User user=this.userRepository.findUserByEmail(username);
        if(!user.isDeleted())
        return user;
        else return null;
    }

    @Transactional
    public void deleteUser(Long userId, String username) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found in database"));
        user.setActive(false);
        user.setDeleted(true);
        List<Bucket>buckets=this.bucketRepository.findAuthorBucketsForUser(userId);
        for(Bucket b:buckets){
            this.bucketService.deleteBucket(b.getId(),user.getEmail());//user from 87 line because only admin can del account???
        }
        List<BucketMember>bucketMembers=this.bucketMemberRepository.findByUserId(user.getId());
        List<TaskMember>taskMembers=this.taskMemberRepository.findByUserId(user.getId());

        for(BucketMember bm: bucketMembers){
            this.bucketMemberService.deleteBucketMember(bm.getId());
        }
        for(TaskMember tm: taskMembers){
            this.taskMemberService.deleteTaskMember(tm.getId());
        }

        modificationHistoryService.saveHistory(1L, 3L, 1L, user.getId(), LocalDateTime.now());
    }

    @Transactional
    public void editUser(UserDTO userDTO, Long id) {

        UserRole userRole = new UserRole();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found in database"));

        userRole.setId(userDTO.getUserRole());

        if (userDTO.getEmail() != null
                && !Objects.equals(user.getEmail(), userDTO.getEmail())) {

            if (!RegistrationService.isValidEmailAddress(userDTO.getEmail()))
                throw new IllegalStateException("Wrong Email");
            user.setEmail(userDTO.getEmail());
        }

        user.setUserRole(userRole);
        if (userDTO.getFirstName() != null
                && !Objects.equals(user.getFirstName(), userDTO.getFirstName()))
            user.setFirstName(userDTO.getFirstName());
        if (userDTO.getLastName() != null
                && !Objects.equals(user.getLastName(), userDTO.getLastName()))
            user.setLastName(userDTO.getLastName());

        // 1 parametr zmienić na uZytkownika z sesji
        modificationHistoryService.saveHistory(1L, 2L, 1L, id, LocalDateTime.now());

    }

    @Transactional
    public void editOwnUser(UserDTO userDTO, String username) {
        User user = userRepository.findUserByEmail(username);
                // .orElseThrow(() -> new NotFoundException("User not found in database"));

        if (userDTO.getEmail() != null
                && !Objects.equals(user.getEmail(), userDTO.getEmail())) {

            if (!RegistrationService.isValidEmailAddress(userDTO.getEmail()))
                throw new IllegalStateException("Wrong Email");
            user.setEmail(userDTO.getEmail());
        }
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        modificationHistoryService.saveHistory(user.getId(), 2L, 1L, user.getId(), LocalDateTime.now());

    }

    public List<UserDTO> getAllUsers() {
        return ((List<User>) userRepository
                .findAll())
                .stream()
                .map(this::convertUserIntoDTO)
                .collect(Collectors.toList());
    }

    private UserDTO convertUserIntoDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(null);
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUserRole(user.getUserRole().getId());
        userDTO.setCreatedDate(user.getCreatedDate());
        userDTO.setDeleted(user.isDeleted());

        return userDTO;
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

}

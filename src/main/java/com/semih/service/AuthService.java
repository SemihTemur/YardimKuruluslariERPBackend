package com.semih.service;

import com.semih.dto.request.EmailRequest;
import com.semih.dto.request.LoginRequest;
import com.semih.dto.request.ProfileRequest;
import com.semih.dto.request.RegisterRequest;
import com.semih.dto.response.BaseResponse;
import com.semih.dto.response.RegisterResponse;
import com.semih.exception.ConflictException;
import com.semih.exception.NotFoundException;
import com.semih.model.User;
import com.semih.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    private final EmailService emailService;

    public AuthService(UserRepository userRepository, RoleService roleService,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtTokenService jwtTokenService, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.emailService = emailService;
    }

    private User mapToEntity(RegisterRequest registerRequest) {
        return new User(
                registerRequest.username(),
                registerRequest.email(),
                bCryptPasswordEncoder.encode(registerRequest.password()),
                roleService.getRoleByName(registerRequest.roleName())
        );
    }


    private RegisterResponse mapToRegisterResponse(User user) {
        return new RegisterResponse(
                new BaseResponse(
                        user.getId(),
                        user.getCreatedDate(),
                        user.getModifiedDate()
                ),
                user.getDisplayUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getRole()
        );
    }

    private User mapToProfileRequest(ProfileRequest profileRequest) {
        return new User(
                profileRequest.userName(),
                profileRequest.email(),
                bCryptPasswordEncoder.encode(profileRequest.password())
        );
    }

    private void validateUniqueness(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.username())) {
            throw new ConflictException("Bu Kullanıcı Adı zaten mevcut!!!");
        }
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new ConflictException("Bu Email hesabı zaten mevcut!!!");
        }
    }

    private void validateUniquenessProfile(ProfileRequest profileRequest) {
        if (userRepository.existsByUsername(profileRequest.userName())) {
            throw new ConflictException("Bu Kullanıcı Adı zaten mevcut!!!");
        }
        if (userRepository.existsByEmail(profileRequest.email())) {
            throw new ConflictException("Bu Email hesabı zaten mevcut!!!");
        }
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("Bu Email hesabı zaten mevcut!!!");
        }
    }

    private void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new ConflictException("Bu Kullanıcı Adı zaten mevcut!!!");
        }
    }

    private String generateRandomPassword() {
        int length = 10;
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public RegisterResponse register(RegisterRequest registerRequest) {
        validateUniqueness(registerRequest);
        User user = mapToEntity(registerRequest);
        User savedUser = userRepository.save(user);
        return mapToRegisterResponse(savedUser);
    }

    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenService.generateToken(authentication);
    }

    public String updateProfile(ProfileRequest profileRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getPrincipal() instanceof User user) {
                userId = user.getId();
            } else {
                throw new NotFoundException("Kullanıcı bulunamadı!!!");
            }
        }

        if (userId != null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("Kullanıcı bulunamadı!!!"));
            controlUser(user, profileRequest);

            User newUser = mapToProfileRequest(profileRequest);
            newUser.setId(userId);
            newUser.setCreatedDate(user.getCreatedDate());
            newUser.setRole(user.getRole());
            userRepository.save(newUser);
        }

        return "Başarıyla güncellendi. Değişikliklerin geçerli olması için çıkış yapılıyor...";
    }

    private void controlUser(User user, ProfileRequest profileRequest) {
        if (!user.getEmail().equals(profileRequest.email())
                && !user.getDisplayUsername().equals(profileRequest.userName())) {
            validateUniquenessProfile(profileRequest);
        } else if (!user.getEmail().equals(profileRequest.email())) {
            validateEmail(profileRequest.email());
        } else if (!user.getDisplayUsername().equals(profileRequest.userName())) {
            validateUsername(profileRequest.userName());
        }
    }

    public List<RegisterResponse> getUserList() {
        return userRepository.findAll()
                .stream()
                .filter(user -> !"SUPER_ADMIN".equals(user.getRole().getRole()))
                .map(this::mapToRegisterResponse)
                .collect(Collectors.toList());
    }

    public RegisterResponse updateUserById(Long id, RegisterRequest registerRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Kullanıcı Bulunamadı!!!"));
        User updatedUser = mapToEntity(registerRequest);
        updatedUser.setId(user.getId());
        updatedUser.setCreatedDate(user.getCreatedDate());
        updatedUser.setModifiedDate(user.getModifiedDate());
        return mapToRegisterResponse(userRepository.save(updatedUser));
    }

    public RegisterResponse deleteUserById(Long id) {
        User deletedUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı Bulunamadı!!!"));
        userRepository.delete(deletedUser);
        return mapToRegisterResponse(deletedUser);
    }

    public String sendPasswordResetCode(EmailRequest emailRequest) {
        User user = userRepository.findByEmail(emailRequest.email())
                .orElseThrow(() -> new NotFoundException("Belirtilen e-posta adresine sahip kullanıcı bulunamadı."));


        String newPassword = generateRandomPassword();
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);

        emailService.sendEmail(emailRequest.email(), newPassword);

        return "Yeni şifreniz e-posta adresinize gönderildi.";
    }

    public Map<String, Object> auth(String token) {
        return jwtTokenService.getClaimsFromToken(token);
    }

}

package com.ElegantDevelopment.iacWebshop.controller;

import com.ElegantDevelopment.iacWebshop.exception.InvalidCredentials;
import com.ElegantDevelopment.iacWebshop.exception.ResourceNotFoundException;
import com.ElegantDevelopment.iacWebshop.exception.UsernameAlreadyTakenException;
import com.ElegantDevelopment.iacWebshop.model.User;
import com.ElegantDevelopment.iacWebshop.model.UserForm;
import com.ElegantDevelopment.iacWebshop.repository.RoleRepo;
import com.ElegantDevelopment.iacWebshop.repository.UserRepo;
import com.ElegantDevelopment.iacWebshop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/rest/user")
public class UserController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JwtUtil jwtUtil;

    private MessageDigest md5 = MessageDigest.getInstance("MD5");

    public UserController() throws NoSuchAlgorithmException {
    }

    // Get All Notes
    @GetMapping("")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId, @RequestHeader("Authorization") String token) throws UnsupportedEncodingException {
        System.out.println(jwtUtil.parseJWT(token));
        return userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }


    @PostMapping("/registration")
    public User registerUser(@Valid @
            RequestBody UserForm userForm) {

        if(userRepo.checkUserNameExistance(userForm.getUsername())) {
            throw new UsernameAlreadyTakenException("Username already exists");
        }

        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(getMD5(userForm.getPassword()));
        user.setJwtToken(jwtUtil.generateJWT(user));
        user.setCustomer(userForm.getCustomer());
        user.setRole(roleRepo.getRoleByName("customer"));

        return userRepo.save(user);
    }

    @GetMapping("/fetchRole")
    public ResponseEntity<Object> getRoleForUser(@RequestHeader("Authorization") String token){
        Map<String, Object> response = new LinkedHashMap<>();
        User u = null;

        try {
            u = this.jwtUtil.parseJWT(token);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.put("id", u.getRole().getId());
        response.put("name", u.getRole().getRoleName());
        return ResponseEntity.status(HttpStatus.OK).body(response);


    }

    @PostMapping("/login")
    public User login(@Valid @RequestBody UserForm userForm){
        User user = null;
        try{
            user = userRepo.getUserByUsername(userForm.getUsername());
        } catch (Exception e){
            throw new InvalidCredentials("invalid login credentials");
        }
        if (!user.getPassword().equals(getMD5(userForm.getPassword()))){
            throw new InvalidCredentials("invalid login credentials");
        }
        user.setJwtToken(jwtUtil.generateJWT(user));


        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable(value = "id") Long userId,
                                   @Valid @RequestBody User userDetails) {

        User user= userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setJwtToken(userDetails.getJwtToken());

        User updatedUser = userRepo.save(user);
        return updatedUser;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userRepo.delete(user);

        return ResponseEntity.ok().build();
    }

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
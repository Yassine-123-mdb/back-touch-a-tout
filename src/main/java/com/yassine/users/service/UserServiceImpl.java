package com.yassine.users.service;

import java.util.ArrayList;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yassine.users.entities.Role;
import com.yassine.users.entities.User;
import com.yassine.users.repos.RoleRepository;
import com.yassine.users.repos.UserRepository;
import com.yassine.users.service.exception.EmailAlreadyExistsException;
import com.yassine.users.service.exception.ExpiredTokenException;
import com.yassine.users.service.exception.InvalidTokenException;
import com.yassine.users.service.register.RegistrationRequest;
import com.yassine.users.service.register.VerificationToken;
import com.yassine.users.service.register.VerificationTokenRepository;
import com.yassine.users.util.EmailSender;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRep;

	@Autowired
	private RoleRepository roleRep;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	EmailSender emailSender;
	@Autowired
	private VerificationTokenRepository verificationTokenRepo;

	@Autowired
    public UserServiceImpl(@Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
	@Override
	public User saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRep.save(user);
	}

	@Override
	public User addRoleToUser(String username, String rolename) {
		User usr = userRep.findByUsername(username);
		Role r = roleRep.findByRole(rolename);

		usr.getRoles().add(r);
		return usr;
	}

	@Override
	public Role addRole(Role role) {
		return roleRep.save(role);
	}

	@Override
	public User findUserByUsername(String username) {
		return userRep.findByUsername(username);
	}

	@Override
	public List<User> findAllUsers() {
		return userRep.findAll();
	}

	@Override
	public User registerUser(RegistrationRequest request) {
	    // Vérifier si l'email existe déjà
	    Optional<User> optionalUser = userRep.findByEmail(request.getEmail());
	    if (optionalUser.isPresent()) {
	        throw new EmailAlreadyExistsException("Email déjà existant!");
	    }

	    // Créer un nouvel utilisateur avec les informations fournies
	    User newUser = new User();
	    newUser.setUsername(request.getUsername());
	    newUser.setEmail(request.getEmail());
	    newUser.setPrenom(request.getPrenom());
	    newUser.setAdress(request.getAdress());
	    newUser.setTel(request.getTel());
	    newUser.setSiret(request.getSiret());
	    newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword())); // Crypter le mot de passe
	    newUser.setEnabled(true); // Le compte est inactif par défaut

	    // Associer un rôle par défaut
	    Role r = roleRep.findByRole(request.getRoles());
	    List<Role> roles = new ArrayList<>();
	    roles.add(r);
	    newUser.setRoles(roles);

	    // Sauvegarder l'utilisateur
	    userRep.save(newUser);

	    // Générer un code de vérification
	    String code = this.generateCode();
	    VerificationToken token = new VerificationToken(code, newUser);
	    verificationTokenRepo.save(token);

	    // Envoyer un email de vérification à l'utilisateur
	    //sendEmailUser(newUser, token.getToken());

	    return newUser;
	    
	}


	private String generateCode() {
		 Random random = new Random();
		 Integer code = 100000 + random.nextInt(900000);

		 return code.toString();

	}
	
	@Override
	public void sendEmailUser(User u, String code) {
		 String emailBody ="Bonjour "+ "<h1>"+u.getUsername() +"</h1>" +
		 " Votre code de validation est "+"<h1>"+code+"</h1>";
		 
		emailSender.sendEmail(u.getEmail(), emailBody);
		}
	@Override
	public String generateToken(User user) {
	    String SECRET_KEY = "your-secret-key"; // Remplacez par une clé secrète forte
	    return Jwts.builder()
	            .setSubject(user.getUsername())
	            .claim("roles", user.getRoles()) // Ajouter les rôles comme claim
	            .setIssuedAt(new Date())
	            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expiration en 24h
	            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
	            .compact();
	}
	@Override
	public User validateToken(String code) {
		VerificationToken token = verificationTokenRepo.findByToken(code);
		
		if(token == null){
			throw new InvalidTokenException("Invalid Token !!!!!!!");
		}

		User user = token.getUser();
		
		Calendar calendar = Calendar.getInstance();
		
		if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
			verificationTokenRepo.delete(token);
			throw new ExpiredTokenException("expired Token");
		}
		
		user.setEnabled(true);
		userRep.save(user);
		return user;
	}
}
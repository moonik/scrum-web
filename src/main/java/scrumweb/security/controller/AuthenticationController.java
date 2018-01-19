package scrumweb.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scrumweb.dto.UserInformationDto;
import scrumweb.security.JwtAuthenticationRequest;
import scrumweb.security.JwtAuthenticationResponse;
import scrumweb.security.JwtTokenUtil;
import scrumweb.security.JwtUserDetailsServiceImpl;
import scrumweb.user.account.repository.UserAccountRepository;
import scrumweb.user.account.service.UserAccountService;

import javax.servlet.http.HttpServletResponse;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL)
public class AuthenticationController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsServiceImpl jwtUserDetailsService;

    @PostMapping("/auth")
    public ResponseEntity createAuthenticationToken(@RequestBody JwtAuthenticationRequest jwtAuthenticationRequest, HttpServletResponse httpServletResponse) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        jwtAuthenticationRequest.getUsername(),
                        jwtAuthenticationRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // generate token
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtAuthenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User userDTO = userService.getUserByName(auth.getName());
        UserInformationDto userInformationDto = userAccountService.getUserInformation(userAccountRepository.findByUsername(auth.getName()));

        return ResponseEntity.ok(new JwtAuthenticationResponse(token, userInformationDto));
    }


}

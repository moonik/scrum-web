package scrumweb.security.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import scrumweb.dto.UserDto;
import scrumweb.dto.UserInformationDto;
import scrumweb.security.*;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;
import scrumweb.user.account.service.UserAccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static scrumweb.common.ApplicationConstants.API_URL;
import static scrumweb.common.ApplicationConstants.HEADER_STRING;
import static scrumweb.common.ApplicationConstants.TOKEN_PREFIX;

@RestController
@RequestMapping(API_URL)
@AllArgsConstructor
public class AuthenticationController {

    private UserAccountService userAccountService;

    private UserAccountRepository userAccountRepository;

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

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
        UserInformationDto userInformationDto = userAccountService.getUserInformation(userAccountRepository.findByUsername(auth.getName()));

        return ResponseEntity.ok(new JwtAuthenticationResponse(token, userInformationDto));
    }

    @GetMapping("/refresh")
    public ResponseEntity refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);

        if(jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserAccount userAccount = userAccountRepository.findByUsername(auth.getName());
            UserInformationDto userInformationDto = userAccountService.getUserInformation(userAccount);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken, userInformationDto));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}

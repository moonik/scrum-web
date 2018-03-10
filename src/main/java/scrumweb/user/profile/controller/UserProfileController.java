package scrumweb.user.profile.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import scrumweb.common.asm.UserProfileAsm;
import scrumweb.dto.user.UserProfileDto;
import scrumweb.user.profile.repository.UserProfileRepository;

import java.util.List;
import java.util.stream.Collectors;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "profile")
@AllArgsConstructor
public class UserProfileController {

    private UserProfileRepository repository;
    private UserProfileAsm userProfileAsm;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserProfileDto> allUsers() {
        return repository.findAll().stream()
            .map(userProfile -> userProfileAsm.convertFromUserProfileToUserProfileDto(userProfile))
            .collect(Collectors.toList());
    }
}

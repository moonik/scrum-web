package scrumweb.user.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scrumweb.dto.UserDto;
import scrumweb.user.account.service.UserAccountService;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "user-account")
public class UserAccountController {

    @Autowired
    protected UserAccountService userAccountService;

    @PostMapping("/save")
    public void save(@RequestBody UserDto userDto) {
        userAccountService.save(userDto);
    }

}

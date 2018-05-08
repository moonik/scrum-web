package scrumweb.user.account.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import scrumweb.dto.notification.NotificationDto;
import scrumweb.dto.user.UserDto;
import scrumweb.user.account.service.UserAccountService;

import java.util.List;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "user-account")
@AllArgsConstructor
public class UserAccountController {

    protected UserAccountService userAccountService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public void save(@RequestBody UserDto userDto) {
        userAccountService.save(userDto);
    }

    @GetMapping("/notifications")
    public List<NotificationDto> getNotifications() {
        return userAccountService.getNotifications();
    }

    @PutMapping("/update-notifications")
    public void setSeen() {
        userAccountService.setSeen();
    }
}

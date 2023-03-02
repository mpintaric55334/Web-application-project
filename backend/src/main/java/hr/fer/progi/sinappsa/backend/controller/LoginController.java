package hr.fer.progi.sinappsa.backend.controller;

import hr.fer.progi.sinappsa.backend.entity.AppUser;
import hr.fer.progi.sinappsa.backend.entity.ErrorMsg;
import hr.fer.progi.sinappsa.backend.repository.AppUserRepository;
import hr.fer.progi.sinappsa.backend.service.AppUserService;
import hr.fer.progi.sinappsa.backend.service.dto.login.LoginInfoDTO;
import hr.fer.progi.sinappsa.backend.service.dto.user.AppUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sinapsa/login")
@CrossOrigin
public class LoginController {

    private final AppUserService appUserService;

    private final AppUserRepository appUserRepository;

    @Autowired
    public LoginController(AppUserService appUserService, AppUserRepository appUserRepository) {
        this.appUserService = appUserService;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("")
    public ResponseEntity<?> check(@RequestBody LoginInfoDTO info) {
        AppUser user = new AppUser();

        if (appUserService.checkLogin(info.getNickname(), info.getPassword())) {
            AppUserDTO dto = new AppUserDTO(appUserRepository.findByNickname(info.getNickname()));
            return new ResponseEntity<>(
                dto,
                HttpStatus.OK);
        } else {
            ErrorMsg msg = new ErrorMsg("Pogrešno korisničko ime ili lozinka");
            return new ResponseEntity<ErrorMsg>(
                msg,
                HttpStatus.UNAUTHORIZED);
        }
    }

}

package hr.fer.progi.sinappsa.backend.service;

import hr.fer.progi.sinappsa.backend.entity.AppUser;
import hr.fer.progi.sinappsa.backend.service.dto.profile.EditProfileDTO;
import hr.fer.progi.sinappsa.backend.service.dto.user.DeleteUserDTO;
import hr.fer.progi.sinappsa.backend.service.dto.user.GradeUserDTO;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AppUserService {

    List<AppUser> listAll();

    ResponseEntity<?> createAppUser(AppUser user, String siteURL)
        throws UnsupportedEncodingException, MessagingException, RequestDeniedException;

    void sendVerificationEmail(AppUser user, String siteURL)
        throws MessagingException, IOException;

    boolean verify(String verificationCode);

    boolean checkLogin(String name, String password);

    ResponseEntity<?> deleteUser(DeleteUserDTO data);

    ResponseEntity<?> updateProfile(EditProfileDTO data);

    ResponseEntity<?> rankings();

    ResponseEntity<?> displayStudentProfile(String nickname);

    boolean checkForModerator(Long usrId);

    void notifyEmail(AppUser noticeOwner, String message, String subject) throws MessagingException;

    ResponseEntity<?> mailAfterDelete(Long userId, String message) throws MessagingException;

    ResponseEntity<?> mailAfterSentQuery(AppUser receiver, AppUser sender, String queryMsg);

    ResponseEntity<?> gradeUser(GradeUserDTO dto);

}

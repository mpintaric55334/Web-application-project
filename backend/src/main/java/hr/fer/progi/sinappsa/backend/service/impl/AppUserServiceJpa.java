package hr.fer.progi.sinappsa.backend.service.impl;

import hr.fer.progi.sinappsa.backend.entity.AppUser;
import hr.fer.progi.sinappsa.backend.entity.AppUserGrade;
import hr.fer.progi.sinappsa.backend.entity.ErrorMsg;
import hr.fer.progi.sinappsa.backend.entity.Notice;
import hr.fer.progi.sinappsa.backend.entity.Query;
import hr.fer.progi.sinappsa.backend.entity.enums.QueryStatus;
import hr.fer.progi.sinappsa.backend.repository.AppUserGradeRepository;
import hr.fer.progi.sinappsa.backend.repository.AppUserRepository;
import hr.fer.progi.sinappsa.backend.repository.NoticeRepository;
import hr.fer.progi.sinappsa.backend.repository.QueryRepository;
import hr.fer.progi.sinappsa.backend.service.AppUserService;
import hr.fer.progi.sinappsa.backend.service.RequestDeniedException;
import hr.fer.progi.sinappsa.backend.service.dto.profile.EditProfileDTO;
import hr.fer.progi.sinappsa.backend.service.dto.profile.ProfileViewDTO;
import hr.fer.progi.sinappsa.backend.service.dto.rankings.RankingsDTO;
import hr.fer.progi.sinappsa.backend.service.dto.user.DeleteUserDTO;
import hr.fer.progi.sinappsa.backend.service.dto.user.GradeUserDTO;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserServiceJpa implements AppUserService {

    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;

    private final AppUserRepository appUserRepo;

    private final AppUserGradeRepository appUserGradeRepository;

    private final NoticeRepository noticeRepo;

    private final QueryRepository queryRepository;

    @Autowired
    public AppUserServiceJpa(AppUserRepository appUserRepo, PasswordEncoder passwordEncoder,
        JavaMailSender mailSender, AppUserGradeRepository appUserGradeRepository, NoticeRepository noticeRepo, QueryRepository queryRepository) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.appUserGradeRepository = appUserGradeRepository;
        this.noticeRepo = noticeRepo;
        this.queryRepository = queryRepository;
    }

    @Override
    public List<AppUser> listAll() {
        return appUserRepo.findAll();
    }

    @Override
    public ResponseEntity<?> createAppUser(AppUser user, String siteURL)
        throws UnsupportedEncodingException, MessagingException, RequestDeniedException {
        if (user.getPassword().length() == 0) {
            throw new RequestDeniedException("Password can't be blank");
        }

        if (appUserRepo.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<ErrorMsg>(
                new ErrorMsg("E-mail je već u uporabi"),
                HttpStatus.BAD_REQUEST);
        }

        if (appUserRepo.findByNickname(user.getNickname()) != null) {
            return new ResponseEntity<ErrorMsg>(
                new ErrorMsg("Korisničko ime je već u uporabi"),
                HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String domain = user.getEmail().split("@")[1];

        if (!domain.equals("fer.hr")) {
            return new ResponseEntity<ErrorMsg>(
                new ErrorMsg("E-mail must be in @fer domain"),
                HttpStatus.BAD_REQUEST);
        }

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setRegistered(false);

        AppUser usr = appUserRepo.save(user);
        sendVerificationEmail(user, siteURL);

        return new ResponseEntity<String>(
            "",
            HttpStatus.OK);
    }

    @Override
    public void sendVerificationEmail(AppUser user, String siteURL)
        throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "ssinapsa@gmail.com";
        String subject = "Registration confirmed";
        String content = "Dear [[firstName]],<br>"
                + "We are confirming your registration<br>"
                +"Welcome to Sinappsa<br>"
                +"Thank you,<br>"
                + "Sinapsa Team.";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom(fromAddress);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[firstName]]", user.getFirstName());

        helper.setText(content, true);;
        mailSender.send(mimeMessage);
    }

    @Override
    public boolean verify(String verificationCode) {
        AppUser user = appUserRepo.findByVerificationCode(verificationCode);

        if (user == null || user.isRegistered()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setRegistered(true);
            appUserRepo.save(user);

            return true;
        }

    }

    @Override
    public boolean checkLogin(String nickname, String password) {
        boolean ret = false;

        AppUser user = appUserRepo.findByNickname(nickname);

        if (user != null) {
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            boolean isPasswordMatches = bcrypt.matches(password, user.getPassword());
            if (isPasswordMatches) {// TODO (user.isRegistered() || user.isModerator()), removed this so you dont need email verif
                ret = true;
            }
        }

        return ret;
    }

    @Override
    public ResponseEntity<?> deleteUser(DeleteUserDTO data) {
        String pass = data.getOldPassword();
        Long id = data.getId();
        AppUser user = appUserRepo.findByUserId(id);

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        boolean isPasswordMatches = bcrypt.matches(pass, user.getPassword());

        if (isPasswordMatches) {
            appUserRepo.deleteById(id);
            noticeRepo.deleteNoticeByAppUser_Id(id);
            return new ResponseEntity<String>(
                "",
                HttpStatus.OK);
        } else {
            return new ResponseEntity<ErrorMsg>(
                new ErrorMsg("Neispravna šifra"),
                HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> updateProfile(EditProfileDTO data) {
        String oldPass = data.getOldPassword();
        String newPass = data.getNewPassword();
        String avatar = data.getAvatar();
        String nickname = data.getNickname();
        Long id = data.getId();
        AppUser user = appUserRepo.findByUserId(id);

        //u slučaju prazne šifre
        if (newPass == "") {
            newPass = oldPass;
        }
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        boolean isPasswordMatches = bcrypt.matches(oldPass, user.getPassword());
        if (isPasswordMatches && (appUserRepo.findByNickname(nickname) == null
            || (long)appUserRepo.findByNickname(nickname).getId() == (long)id)) {
            String newPassEncrypt = passwordEncoder.encode(newPass);
            appUserRepo.updateProfile(nickname, avatar, newPassEncrypt, id);
            return new ResponseEntity<String>(
                "",
                HttpStatus.OK);
        } else if(!isPasswordMatches){
            return new ResponseEntity<ErrorMsg>(
                    new ErrorMsg("Neispravna stara lozinka"),
                    HttpStatus.BAD_REQUEST);
        }else if(!isPasswordMatches && (appUserRepo.findByNickname(nickname) == null
            || appUserRepo.findByNickname(nickname).getId() == id)) {
            return new ResponseEntity<ErrorMsg>(
                new ErrorMsg("Neispravna stara lozinka"),
                HttpStatus.BAD_REQUEST);

        } else {
            return new ResponseEntity<ErrorMsg>(
                new ErrorMsg("Korisničko ime u uporabi"),
                HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<?> rankings() {
        final List<AppUser> list = appUserRepo.findAllByOrderByRankingPointsDesc();
        final List<RankingsDTO> rankingList = new ArrayList<>();
        int rankingListSize = 10;
        //EDIT HERE
        if(list.size()<rankingListSize){
            rankingListSize=list.size();
        }
        //END EDIT
        for (int i = 0; i < rankingListSize; i++) {
            AppUser appUser = list.get(i);
            RankingsDTO dto = new RankingsDTO(appUser, Integer.toUnsignedLong(i + 1));
            rankingList.add(dto);
        }

        return new ResponseEntity<>(
            rankingList,
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> displayStudentProfile(String nickname) {
        AppUser user = appUserRepo.findByNickname(nickname);
        ProfileViewDTO
            dto = new ProfileViewDTO(user.getAvatar(), user.getFirstName(), user.getLastName(), user.getNickname(),
            user.getEmail());
        return new ResponseEntity<ProfileViewDTO>(
            dto,
            HttpStatus.OK
        );
    }

    @Override
    public boolean checkForModerator(Long usrId) {
        AppUser myUser = appUserRepo.findByUserId(usrId);

        return myUser.isModerator();
    }

    @Override
    public void notifyEmail(AppUser mailReceiver, String message, String subject)
        throws MessagingException {
        String toAddress = mailReceiver.getEmail();
        String fromAddress = "ssinapsa@gmail.com";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom(fromAddress);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        if (message != null) {
            helper.setText(message, true);
        } else {
            helper.setText("Your notice has been deleted by moderator", true);
        }

        mailSender.send(mimeMessage);
    }

    @Override
    public ResponseEntity<?> mailAfterDelete(Long noticeOwnerId, String message)
        throws MessagingException {
        AppUser noticeOwner = appUserRepo.findByUserId(noticeOwnerId);
        String subject = "Notice removal";

        if (noticeOwner != null) {
            notifyEmail(noticeOwner, message, subject);
            return new ResponseEntity<AppUser>(
                noticeOwner,
                HttpStatus.OK);
        }

        return new ResponseEntity<ErrorMsg>(
            new ErrorMsg("Ne može se poslati mail!"),
            HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> mailAfterSentQuery(AppUser receiver, AppUser sender, String queryMsg) {
        if (receiver == null) {
            return new ResponseEntity<ErrorMsg>(
                new ErrorMsg("Korisnik koji šalje upit ne postoji!"),
                HttpStatus.BAD_REQUEST);
        }

        if (sender == null) {
            return new ResponseEntity<ErrorMsg>(
                new ErrorMsg("Korisnik koji prima upit ne postoji!"),
                HttpStatus.BAD_REQUEST);
        }

        final String subject = "You got a new query!";
        final String message = String.format("Korisnik: %s<br>Email korisnika: %s<br>Sadržaj upita: %s<br>",
            sender.getNickname(), sender.getEmail(), queryMsg);

        try {
            notifyEmail(receiver, message, subject);
        } catch (MessagingException messagingException) {
            return new ResponseEntity<ErrorMsg>(
                new ErrorMsg("Mail se ne može posalati!"),
                HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ErrorMsg>(
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> gradeUser(GradeUserDTO dto) {
        final Query query = queryRepository.findById(dto.getQueryId()).get();
        final Notice notice = query.getNotice();
        final Long appUserId = dto.getAppUserId();
        final boolean isAskingHelp = notice.getIsAskingHelp();
        final AppUser sender = query.getSender();
        final AppUser receiver = query.getReceiver();
        final QueryStatus status = query.getStatus();



        System.out.println(status);
        if (isAskingHelp && (long)appUserId == (long)receiver.getId() && status == QueryStatus.IN_PROGRESS) {
            updateUserPoints(sender, dto.getGrade());
            return new ResponseEntity<>(
                HttpStatus.OK);
        }
        if (!isAskingHelp && (long)appUserId ==(long) sender.getId() && status == QueryStatus.IN_PROGRESS) {
            updateUserPoints(receiver, dto.getGrade());
            return new ResponseEntity<>(
                HttpStatus.OK);
        }

        return new ResponseEntity<>(
            new ErrorMsg("Korisnik nije uspiješno ocijenjen"),
            HttpStatus.BAD_REQUEST);
    }

    private void updateUserPoints(AppUser user, Double grade) {
        final List<AppUserGrade> grades = user.getGrades();
        final AppUserGrade newGrade = new AppUserGrade(grade, user);
        grades.add(newGrade);

        user.setAverageGrade(grades.stream().mapToDouble(AppUserGrade::getGrade).average().getAsDouble());
        user.setNumberOfAcceptedNotices(user.getNumberOfAcceptedNotices() + 1);
        user.setRankingPoints(Math.round(user.getAverageGrade() * user.getNumberOfAcceptedNotices() * 10));

        appUserRepo.save(user);
        appUserGradeRepository.save(newGrade);
    }

}

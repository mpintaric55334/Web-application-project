package hr.fer.progi.sinappsa.backend.repository;

import hr.fer.progi.sinappsa.backend.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByNickname(String nickname);

    @Query("SELECT u FROM AppUser u WHERE u.id = :id")
    AppUser findByUserId(@Param("id") Long id);

    AppUser findByEmail(String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM AppUser u WHERE u.id =:id")
    void deleteById(@Param("id") Long id);

    @Query("SELECT u FROM AppUser u WHERE u.verificationCode = ?1")
    AppUser findByVerificationCode(String code);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser u SET u.nickname= :nickname,u.avatar= :avatar,u.password= :newPassword WHERE u.id=:id")
    void updateProfile(@Param("nickname") String nickname, @Param("avatar") String avatar,
        @Param("newPassword") String newPassword, @Param("id") Long id);

    List<AppUser> findAllByOrderByRankingPointsDesc();

}

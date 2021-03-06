package kr.ant.booksharing.controller;

import kr.ant.booksharing.model.UserCampusAuthImageReq;
import kr.ant.booksharing.service.UserCampusAuthImageService;
import kr.ant.booksharing.utils.auth.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import static kr.ant.booksharing.model.DefaultRes.FAIL_DEFAULT_RES;

@Slf4j
@RestController
@RequestMapping("authImage")
public class UserCampusAuthImageController {
    private final UserCampusAuthImageService userCampusAuthImageService;

    public UserCampusAuthImageController(final UserCampusAuthImageService userCampusAuthImageService) {
        this.userCampusAuthImageService = userCampusAuthImageService;
    }

    /**
     * 학생 인증 사진 저장
     *
     * @return ResponseEntity
     */
    @Auth
    @PostMapping("")
    public ResponseEntity saveUserCampusAuthImage(@RequestPart(value = "userCampusAuthImageFile")
                                                  final MultipartFile userCampusAuthImageFile,
                                                  final HttpServletRequest httpServletRequest) {
        try {
            final int userIdx = (int) httpServletRequest.getAttribute("userIdx");
            return new ResponseEntity<>(userCampusAuthImageService.saveUserCampusAuthImage(userCampusAuthImageFile, userIdx), HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}", e);
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 학생 인증 사진 저장
     *
     * @return ResponseEntity
     */
    @GetMapping("")
    public ResponseEntity getUserCampusAuthImage(@RequestParam("userId") final int userId) {
        try {
            return new ResponseEntity<>(userCampusAuthImageService.findUserCampusAuthImageByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}", e);
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.NOT_FOUND);
        }
    }


    /**
     * 학생 인증 승인
     *
     * @return ResponseEntity
     */
    @GetMapping("/confirm")
    public ResponseEntity authUserAuthComplete(@RequestParam("userId") final int userId) {
        try {
            return new ResponseEntity<>(userCampusAuthImageService.changeUserAuthComplete(userId), HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}", e);
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.NOT_FOUND);
        }
    }
}

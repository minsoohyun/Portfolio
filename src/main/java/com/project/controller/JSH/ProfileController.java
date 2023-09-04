package com.project.controller.JSH;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.Member;
import com.project.entity.Paychk;
import com.project.entity.Profile;
import com.project.entity.Profileimg;
import com.project.mapper.JSH.ProfileMapper;
import com.project.repository.MemberRepository;
import com.project.repository.PaychkRepository;
import com.project.repository.ProfileRepository;
import com.project.repository.ProfileimgRepository;
import com.project.service.JSH.ProfileService;
import com.project.service.JeongService.JeongService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/profile")
@RequiredArgsConstructor
public class ProfileController {

    final String format = "ProfileContrller => {}";
    final ProfileService pService;
    final ProfileMapper pMapper;
    final MemberRepository mRepository;
    final JeongService jService;
    final ProfileRepository pRepository;
    final PaychkRepository pcRepository;
    final ProfileimgRepository piRepository;
    final HttpSession httpSession;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    @Value("${default.profileimg}")
    private String defaultprofileimg;
    final ResourceLoader resourceLoader;

    @GetMapping(value = "/profileimage")
    public ResponseEntity<byte[]> image(@RequestParam(name = "profileno", defaultValue = "0") BigInteger profileno)
            throws IOException {
        Profileimg obj = piRepository.findByProfile_Profileno(profileno);
        HttpHeaders headers = new HttpHeaders(); // import org.springframework.http.HttpHeaders;

        if (obj != null) { // 이미지가 존재할 경우
            headers.setContentType(MediaType.parseMediaType(obj.getFiletype()));
            return new ResponseEntity<>(obj.getFiledata(), headers, HttpStatus.OK);
        }

        // 이미지가 없을 경우
        InputStream is = resourceLoader.getResource(defaultprofileimg).getInputStream(); // exception 발생됨.
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
    }

    // 프로필 선택창
    // paychk에서 가장 최신의 M(멤버쉽)하나를 가져와서 regdate 만료 확인(+ 30일)
    // 현재 멤버쉽은 GRADE 값
    @GetMapping(value = "/profilelist.do")
    public String profilelistGET(@AuthenticationPrincipal User user, Model model, HttpSession session) {
        try {
            String id = user.getUsername();
            log.info("list id => {}", id);
            log.info("list user => {}", user.getUsername());

            ArrayList<Profile> list = pService.selectprofile(id);
            model.addAttribute("list", list);
            for (Profile profile : list) {
                BigInteger profileno = profile.getProfileno();
                Profileimg profileimg = piRepository.findByProfile_Profileno(profileno);
                String nickname = profile.getNickname();
                model.addAttribute("nickname1", nickname);
                log.info("list => {}", list.toString());

                if (profileimg != null) {
                    model.addAttribute("profileno", profileno);
                    break;
                }
                if (profile.getProfilepw() == null) {
                    model.addAttribute("pwchk", 0);
                } else {
                    model.addAttribute("pwchk", 1);
                }
            }

            // 멤버십 등급 확인
            Paychk paychk = pcRepository.findTop1ByMember_idAndTypeOrderByRegdateDesc(id, "M");
            if (paychk != null) {
                BigInteger grade = paychk.getFee().getGrade();
                // 멤버십 등급 전달
                model.addAttribute("grade", grade);
                log.info("grade => {}", grade);
            } else {
                model.addAttribute("grade", "0");
            }

            // 프로필 갯수 확인
            long profilecnt = pRepository.countByMember_id(id);

            // 프로필 수 전달
            model.addAttribute("profilecnt", profilecnt);
            log.info("profilecnt => {}", profilecnt);

            Calendar calendar = Calendar.getInstance();

            // 현재 날짜 가져오기
            Date currentDate = new Date();

            // 가장 최신의 Paychk 조회
            if (!pcRepository.findByMember_id(id).isEmpty()) { // Paychk의 정보가 있으면

                List<Paychk> list1 = pMapper.selectPaychk(id);
                if (!list1.isEmpty()) {
                    Paychk latestPaychk = list1.get(0);

                    // 만료 날짜와 현재 시간 비교
                    calendar.setTime(latestPaychk.getRegdate());
                    calendar.add(Calendar.MONTH, 1);
                    Date oneMonthAfter = calendar.getTime();

                    log.info("oneMonthAfter => {}", oneMonthAfter);
                    log.info("currentDate => {}", currentDate);

                    // 날짜 비교
                    if (currentDate.after(oneMonthAfter)) { // 만료되었다면
                        model.addAttribute("chk", "0");
                    } else { // 아직 남아있을 때
                        model.addAttribute("chk", "1");
                    }
                } else { // Paychk의 정보가 없으면
                    model.addAttribute("chk", "0");
                }
            } else {
                model.addAttribute("chk", "0");
            }
            return "/JSH/profilelist";
        } catch (Exception e) {
            e.printStackTrace();
            return "/JSH/profilelist";
        }
    }

    // 암호 초기화
    @PostMapping(value = "/clearpw.do")
    public String clearpwPOST(@RequestParam("clearpw_nickname") String nickname) {
        log.info("nickname => {}", nickname);
        Profile profile = pRepository.findByNickname(nickname);
        log.info("before clearpw => {}", profile.getProfilepw());
        profile.setProfilepw(null);
        pRepository.save(profile);
        log.info("mid clearpw => {}", profile.getProfilepw());
        profile.setProfilepw(bcpe.encode("1234"));
        pRepository.save(profile);
        log.info("after clearpw => {}", profile.getProfilepw());

        return "redirect:/profile/profilelist.do";
    }

    // 프로필 생성
    @GetMapping(value = "/create.do")
    public String createGET(Model model) {
        model.addAttribute("profile", new Profile());
        return "/JSH/profilecreate";
    }

    @PostMapping(value = "/create.do")
    public String createPOST(@ModelAttribute("profile") Profile profile,
            @RequestParam("nickname") String nickname,
            HttpSession session, @AuthenticationPrincipal User user) {
        // 세션에서 멤버 ID 가져오기
        String memberId = user.getUsername();

        // 프로필 정보 설정
        Member member = new Member();
        member.setId(memberId);
        profile.setMember(member);
        profile.setNickname(nickname);
        // log.info("profile =>", profile);
        // 프로필 저장
        pRepository.save(profile);

        // 프로필 생성 후 리다이렉트할 페이지 지정
        return "redirect:/profile/profilelist.do";
    } // 완료

    // 프로필 로그인
    @GetMapping(value = "/login.do")
    public String loginGET(@RequestParam(name = "nickname1") String nickname,
            Model model, HttpSession session, @AuthenticationPrincipal User user) {
        model.addAttribute("nickname", nickname);
        String id = user.getUsername();
        String role = mRepository.findById(id).get().getRole();
        Profile profile1 = pRepository.findByNickname(nickname);

        if (profile1.getProfilepw() == null) {
            // httpSession.setAttribute("role", role);
            session.setAttribute("token", mRepository.findById(user.getUsername()).get().getToken());
            session.setAttribute("role", role);
            session.setAttribute("profileno", profile1.getProfileno());
            session.setAttribute("nickname", nickname);
            log.info("{}", mRepository.findById(user.getUsername()).get().getToken());
            return "redirect:/profile/mypage.do";
        } else {
            return "/JSH/logintest";
        }
    }

    @PostMapping(value = "/login.do")
    public String loginPOST(@RequestParam("nickname1") String nickname,
            @RequestParam(value = "profilepw", required = false) String profilepw, Model model, HttpSession session,
            @AuthenticationPrincipal User user) {
        String id = user.getUsername();
        String role = mRepository.findById(id).get().getRole();
        Profile profile = pRepository.findByNickname(nickname);
        Paychk paychk = jService.findPaychkMemberidAndTypeTopByRegdate(profile.getMember().getId(), "M");
        // log.info("{}",paychk.getFee().getGrade());
        // log.info("{}",paychk1.toString());

        try {
            if (paychk != null) {
                session.setAttribute("grade", paychk.getFee().getGrade());
            }
            session.setAttribute("token", mRepository.findById(user.getUsername()).get().getToken());
            session.setAttribute("role", role);
            session.setAttribute("id", user.getUsername());
            session.setAttribute("profileno", profile.getProfileno());
            session.setAttribute("nickname", nickname);
            // log.info("{}", mRepository.findById(user.getUsername()).get().getToken());
            // 로그인 후 갈 홈화면
            return "redirect:/kdh/home.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:profile/profilelist.do";
        }
    }

    @GetMapping(value = "/myprofile.do")
    public String showHomePage(HttpSession session) {
        String nickname = (String) session.getAttribute("nickname");
        String profileno = (String) session.getAttribute("profileno");
        log.info("nickname => {}", nickname);
        log.info("profileno => {}", profileno);

        if (nickname != null) {
            return "/JSH/profilenickname"; // 로그인된 경우 홈 페이지 경로
        } else {
            return "redirect:/profile/login.do"; // 로그인되지 않은 경우 로그인 폼으로 리다이렉트
        }
    }

}
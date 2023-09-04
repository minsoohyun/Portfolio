package com.project.restcontroller.KSH;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.Member;
import com.project.repository.MemberRepository;
import com.project.service.KSH.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api/member")
@RequiredArgsConstructor
public class RestMemberController {

    final HttpSession httpSession;
    final MemberService mService;
    final MemberRepository mRepository;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    @GetMapping(value = "/idcheck.json")
    public Map<String, Object> idcheckGET(@RequestParam(name = "id") String id) {
        Map<String, Object> retMap = mService.idcheck(id);
        return retMap;
    }

    @GetMapping(value = "/pwcheck.json")
    public Map<String, Object> pwcheckGET(@RequestParam(name = "pw") String pw,@AuthenticationPrincipal User user) {
        Map<String, Object> retMap = mService.pwcheck(pw, (String)user.getUsername());
        return retMap;
    }

    @GetMapping(value = "/emailchk.json")
    public Map<String, Object> emailchkGET(@RequestParam(name = "email") String email) {
        Map<String, Object> retMap = mService.emailchk(email);
        return retMap;
    }

    @PostMapping(value = "/login.json")
    public Map<String, Object> loginJsonPOST(
            @RequestBody Member obj1) {
        Map<String, Object> retMap = mService.loginRest(obj1);
        return retMap;
    }

    @PostMapping(value = "/findid.json")
    public Map<String, Object> findidPOST(@RequestBody Member obj) {
        Map<String, Object> retMap = mService.findidRest(obj);
        return retMap;
    }

    @PostMapping(value = "/findpw.json")
    public Map<String, Object> findpwPOST(@RequestBody Member obj) {
        Map<String, Object> retMap = mService.findPwRest(obj);
        return retMap;
    }
}

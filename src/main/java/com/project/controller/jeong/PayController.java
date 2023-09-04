package com.project.controller.jeong;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.dto.Jeong.PaymentVideolist;
import com.project.entity.Chargetoken;
import com.project.entity.Fee;
import com.project.entity.Member;
import com.project.entity.Paychk;
import com.project.entity.Paymentlist;
import com.project.entity.Profile;
import com.project.repository.PaychkRepository;
import com.project.repository.PaymentlistRepository;
import com.project.repository.Projections.MemberProjection;
import com.project.service.JeongService.JeongService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/pay")
public class PayController {

    final HttpSession httpSession;
    final JeongService jService;
    final PaychkRepository payRepository;
    final PaymentlistRepository paymentlistRepository;

    SimpleDateFormat fmt = new SimpleDateFormat("yyyy. MM. dd.");

    @GetMapping(value = "/havelist.do")
    public String haveGET(Model model){
        List<Paymentlist> paymentList = paymentlistRepository.findByProfile_profilenoOrderByRegdateDesc((BigInteger)httpSession.getAttribute("profileno"));
        if(paymentList !=null){
            List<PaymentVideolist> list1 = jService.paymenstlistToPaymentVideolist(paymentList);
            List<PaymentVideolist> list = jService.getVideoTitle(list1);        
            model.addAttribute("list1", paymentList);
            model.addAttribute("list", list);
        }
        return "/jeong/StreamPark_havelist";
    }


    @PostMapping(value = "/update.do")
    public String updatePOST(@RequestParam(name = "grade") int grade) {
        log.info("updatepost -> {}", grade);
        String id = (String) httpSession.getAttribute("id");
        Member member = new Member();
        member.setId(id);
        member.setMembershipchk(BigInteger.valueOf(grade));
        int ret = jService.updateMembership(member);
        if (ret == 1) {
            return "redirect:/kdh/home.do";

        } else {
            return "redirect:/kdh/home.do";
        }
    }

    @PostMapping(value = "/pay.do")
    public String payPOST(@RequestParam(name = "membership", required = false, defaultValue = "0") int grade,
            @RequestParam(name = "price") int price,
            @RequestParam(name = "token", required = false, defaultValue = "0") long token,
            @AuthenticationPrincipal User user) {
        String id = user.getUsername();

        log.info("payPOST -> 아이디 {}, 등급 {}, 가격 {}, 토큰 {} ", id, grade, price, token);
        Paychk obj = new Paychk();
        Fee fee = new Fee();
        Member member = new Member();
        Chargetoken chargetoken = new Chargetoken();
        int ret = 0;
        if (grade != 0) { // 멤버쉽 결제시
            chargetoken.setToken("0"); // 토큰 0 설정
            member.setId(id);
            fee.setGrade(BigInteger.valueOf(grade));
            obj.setFee(fee);
            httpSession.setAttribute("grade", obj.getFee().getGrade());
            obj.setMember(member);
            obj.setType("M");
            obj.setChargetoken(chargetoken);
            obj.setPrice(BigInteger.valueOf(price));
            ret = jService.insertPaychkMembership(obj);
        } else if (token != 0) { // 토큰 결제시
            chargetoken.setToken(String.valueOf(token)); // 받아온 토큰 갯수 설정
            member.setId(id);
            fee.setGrade(BigInteger.valueOf(grade)); // 등급 0 설정
            obj.setFee(fee);
            obj.setMember(member);
            obj.setType("T");
            obj.setChargetoken(chargetoken);
            obj.setPrice(BigInteger.valueOf(price));
            ret = jService.insertPaychkToken(obj);
        }

        if (ret == 1) {
            return "redirect:/kdh/home.do";

        } else {
            return "redirect:/kdh/home.do";
        }

    }

    @GetMapping(value = "/paychk.do")
    public String paychkGET(Model model, @AuthenticationPrincipal User user) {
        String id = user.getUsername();
        List<Paychk> plist = payRepository.findByMember_id(id);
        List<Paychk> membershipList = payRepository.findByMember_idAndType(id, "M");
        List<Paychk> tokenList = payRepository.findByMember_idAndType(id, "T");

        // log.info("{}", plist);
        // log.info("{}", membershipList);
        // log.info("{}", tokenList);

        model.addAttribute("plist", plist);
        model.addAttribute("mlist", membershipList);
        model.addAttribute("tlist", tokenList);
        return "/jeong/StreamPark_paychk";
    }

    @GetMapping(value = "/token.do")
    public String tokenGET(Model model, @AuthenticationPrincipal User user) {
        BigInteger profileno = (BigInteger) httpSession.getAttribute("profileno");
        String id = user.getUsername();
        Profile profile = jService.findProfileById(profileno.longValue());
        MemberProjection member = jService.findMemberById(profile.getMember().getId());
        Paychk paychk = jService.findPaychkMemberidAndTypeTopByRegdate(profile.getMember().getId(), "M");


        if (paychk == null) { // 멤버쉽 결제 내역이 없을때
            
           
        } else { // 멤버쉽 결제내역이 있을때
            Date nowDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(paychk.getRegdate());// 등록된 날짜
            cal.add(Calendar.DATE, 30); // 등록된 날짜 + 30일(cal)
            if(nowDate.compareTo(cal.getTime())==-1){
                //토큰결제 접근 못하도록 홈으로 
                return "redirect:/kdh/home.do";
            }                  

        }

        model.addAttribute("id", id);
        model.addAttribute("token", member.getToken());
        return "/jeong/StreamPark_chargetoken";
    }

    @GetMapping(value = "/membership.do")
    public String membershipGET(Model model,
            @RequestParam(name = "menu", required = false, defaultValue = "0") int menu,
            @AuthenticationPrincipal User user) {
        BigInteger profileno = (BigInteger) httpSession.getAttribute("profileno");
        Profile profile = jService.findProfileById(profileno.longValue());
        String nickname =  (String)httpSession.getAttribute("nickname");
        String id = user.getUsername();
        MemberProjection member = jService.findMemberById(profile.getMember().getId());
        // Paychk paychk = jService.findPaychkTopByRegdate();
        // log.info("membershipGET member -> {}", member.toString());
        Paychk paychk = jService.findPaychkMemberidAndTypeTopByRegdate(profile.getMember().getId(), "M");
        List<Fee> feelist = jService.findFeeAll();
        // httpSession.setAttribute("id", member.getId());
        // httpSession.setAttribute("nickname", profile.getNickname());
        // httpSession.setAttribute("role", "C");

        log.info("{}", feelist);
        
        // log.info("membershipGET profile -> {}", profile);
        // log.info("membershipGET paychk -> {}", paychk);
        // log.info("membershipGET paychk2 -> {}", paychk2);
        if ((member.getMembershipchk() != null )) {// null이 아닐 때
            log.info("next membership {}", member.getMembershipchk());
            model.addAttribute("nextfee", feelist.get(member.getMembershipchk().intValue()));
            model.addAttribute("nextgrade", member.getMembershipchk());

        }

        if (paychk == null) { // 멤버쉽 결제 내역이 없을때
            model.addAttribute("cal", 0); // cal이 현재 날짜보다 과거면 1 미래면 -1 -> -1이면 유효 1이면 만료
            model.addAttribute("grade", 0);
            if(menu != 1){
                return "redirect:/pay/membership.do?menu=1";
            }
        } else { // 멤버쉽 결제내역이 있을때
            Date nowDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(paychk.getRegdate());// 등록된 날짜
            cal.add(Calendar.DATE, 30); // 등록된 날짜 + 30일(cal)
            model.addAttribute("expired", fmt.format(cal.getTime()) );
            model.addAttribute("cal", nowDate.compareTo(cal.getTime())); // cal이 현재 날짜보다 과거면 1 미래면 -1 -> -1이면 유효 1이면 만료
            log.info("멤버십 구매 날짜 : {} ", paychk.getRegdate().toString());
            model.addAttribute("grade", paychk.getFee().getGrade());
            Fee nowFee = jService.findFeeById(paychk.getFee().getGrade());
            if(menu !=2 && nowDate.compareTo(cal.getTime()) == -1){
                //멤버쉽이 유효 하면
                return "redirect:/pay/membership.do?menu=2";
            }else if(menu !=1 && nowDate.compareTo(cal.getTime()) != -1){
                return "redirect:/pay/membership.do?menu=1";
            }
            // if (menu != 0) {
                
            //     if (menu == 1 && (nowDate.compareTo(cal.getTime()) == -1)) {
            //         //멤버쉽이 유효할 때 멤버쉽 가입으로 가면 가입,변경 버튼만 있는 페이지로
            //         return "redirect:/pay/membership.do";
            //     }                

            // }
            model.addAttribute("fee", nowFee);
        }

        model.addAttribute("id", id);
        model.addAttribute("feelist", feelist);
        model.addAttribute("nickname", nickname);
        model.addAttribute("role", user.getAuthorities());
        model.addAttribute("token", member.getToken());
        model.addAttribute("currentTokens", member.getToken());

        return "/jeong/StreamPark_membership";
    }

}

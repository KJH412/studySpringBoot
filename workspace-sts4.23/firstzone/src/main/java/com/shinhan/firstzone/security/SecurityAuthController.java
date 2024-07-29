package com.shinhan.firstzone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shinhan.firstzone.vo2.MemberEntity;

@Controller
@RequestMapping("/auth")
public class SecurityAuthController {
	
	@GetMapping("/login")
	public void f1() {}
	
	//Spring이 post수행 .......UserDetailsService를 구현한 class수행. 
	//=> (MemberService.java) loginUserByUsername()함수를 수행한다.
	//@PostMappin("/login")
	//public void loginPost(){}
	
	@GetMapping("/loginSuccess")
	public void f2() {}
	
	@GetMapping("/logout")
	public void f3() {}
	
	@GetMapping("/accessDenined")
	public void f4() {}
	
	@GetMapping("/signup")
	public String f5() {
		return "auth/joinForm";
	}
	
	@Autowired
	MemberService mSerivce;
	
	@PostMapping("/joinProc")
	public String f6(MemberEntity member) {
		MemberEntity newMember = mSerivce.joinUser(member);
		return "redirect:login";
	}
}
package com.shinhan.firstzone.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/security")
public class SecurityTestController {
	
	@GetMapping("/all")
	public void f1() {
		log.info("모든 사용자가 접근가능한 요청이다.");
	}

	@GetMapping("/user")
	public void f2() {
		log.info("user권한을 가진 Member가 접근가능한 요청이다.");
	}

	@GetMapping("/admin")
	public void f3() {
		log.info("admin권한을 가진 Member가 접근가능한 요청이다.");
	}
	
	@GetMapping("/manager")
	public void f4() {
		log.info("manager권한을 가진 Member가 접근가능한 요청이다.");
	}
}

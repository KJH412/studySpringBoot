package com.shinhan.firstzone.controller2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shinhan.firstzone.repository3.FreeBoardRepository;
import com.shinhan.firstzone.vo2.MemberEntity;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

//@Controller는 뷰를 반환한다.
//@RestController 주용도는 Json 형태로 객체 데이터를 반환하는 것

//@RestController //@Controller + @ResponseBody
@Controller  // 요청하면 Page forward or 다른 요청으로 redirect
@RequestMapping("/coffee")
@RequiredArgsConstructor //@Autowired랑 같은 역할
public class CoffeeController {
	// @RestController -> 응답 문서의 body에 전달
	// @Controller -> Page 반환
	
	//@Autowired 생략. 필수 일 때 final
	final FreeBoardRepository boardRepo;
	
	@GetMapping("/layout1")
	String f6() {
		
		return "layout/layout1";
	}
	
	@GetMapping("/exlayout1")
	String f5() {
		return "layout/exLayout1"; //실제 html파일이 다른 경로에 있을 경우 리턴시켜서 뷰를 반환한다.
		//layout/exLayout1.html
	}
	
	@GetMapping("/detail")
	void f4(Long bno, Model model) {
		model.addAttribute("board", boardRepo.findById(bno).get());
		//findById(): Optional <FreeBoardEntity> 
		//Optional =>있을 수 도 있고 없을 수도 있다는 의미로 
		//.get으로 무조건 받아오던지 orElse()로 받는다.
	}
	
	@GetMapping("/list")
	void f3(@RequestParam("keyword") String keyword, 
						Model model, 
						HttpSession session) {
		System.out.println("keyword" + keyword);
		model.addAttribute("blist", boardRepo.findAll() );
		MemberEntity member =  MemberEntity.builder()
				.mid("idid")
				.mname("김이름")
				.build();
		
		session.setAttribute("loginUser", member);
	}
	
	@GetMapping("/page2")
	public String f2(Model model) {
		model.addAttribute("menu", "아메리카노");
		MemberEntity member =  MemberEntity.builder()
							.mid("zz")
							.mname("나야나~")
							.build();
		model.addAttribute("member", member);
		return "coffee/page1";
	
	}
	@GetMapping("/page1")
	public void f1(Model model) {
		model.addAttribute("menu", "아메리카노");
		MemberEntity member =  MemberEntity.builder()
							.mid("abc")
							.mname("찐")
							.build();
		model.addAttribute("member", member);
	
	}
	
	/*
	@GetMapping("/page2")
	public String f2() {
		return "coffee/page1";
	}
	
 	@GetMapping("/page1")
	public void f1(Model model) {
 	// 리턴 타입 void -> 페이지 forward (default)
	// return "coffee/page1"; // 요청 주소와 페이지 경로가 똑같으면 생략 가능
	}
	 */

	// @RestController의 경우 넘겨줄 데이터를 작성하지 않으면 페이지에 아무것도 보이지 않음
	//@GetMapping("/page1")
	public String f() {
		return "Hello";
	}
}

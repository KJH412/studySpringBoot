package com.shinhan.firstzone.controller2;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shinhan.firstzone.paging.PageRequestDTO;
import com.shinhan.firstzone.security.MemberService;
import com.shinhan.firstzone.service2.WebBoardService;
import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo4.WebBoardDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

//@Controller : 요청->서비스수행->data,page가 return  (page는 Thymeleaf template를 사용중)
//@RestController : 요청->서비스수행->data return (ReactJs에서 사용할 예정)

@Controller
@RequestMapping("/webboard")
@RequiredArgsConstructor
@Log4j2
public class WebBoardController {
	
	final WebBoardService boardService;
	final MemberService memService;
	
	@GetMapping("/register")
	void registerForm() {
		//리턴 없음 : forward 
	}
	
	@PostMapping("/register")
	String register(WebBoardDTO dto, RedirectAttributes attr, Principal principal,
			Authentication authentication, HttpSession session) { 
		
		System.out.println("방법1 principal : " + principal);
		Object principal2 = authentication.getPrincipal();
		System.out.println("방법2 authentication : " + principal2);
		
		Object principal3 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("방법3 authentication : " + principal3);	
		
		UserDetails userDetails = (UserDetails) principal3;
		System.out.println("userDetails : " + userDetails);	
		//****로그인한 멤버 id가 Board의 mid로 입력된다.
		String mid = principal.getName();
		//
		UserDetails userDetails2 = memService.loadUserByUsername(mid);
		System.out.println("방법4 loadUserByUsername : " + userDetails2);
		
		MemberEntity loginUser = (MemberEntity) session.getAttribute("user");
		System.out.println("방법5 session에서 읽기 : " + loginUser);
		
		dto.setMid(mid);
		Long bno = boardService.register(dto); 
		attr.addFlashAttribute("msg", "입력완료");
		return "redirect:list";
	}
	
	@GetMapping("/list") 
	String getList(Model model, PageRequestDTO pageRequestDTO) {
		//List<WebBoardDTO> blist = boardService.getList();
		//model.addAttribute("blist", blist); //(html에서)타임리프가 사용할 변수 저장. 

		if(pageRequestDTO.getSize() == 0) {
			pageRequestDTO.setPage(1); //한 페이지에 10줄
			pageRequestDTO.setSize(10);
			//pageRequestDTO.setType("twc"); //t:title,w:writer, c:content
			//pageRequestDTO.setKeyword("다"); //키워드
		}
		//페이징 처리
		//데이터가 넘어오지 않을 수 도 있음. null
		model.addAttribute("result", boardService.getList(pageRequestDTO));
		model.addAttribute("pageRequestDTO", pageRequestDTO); //페이지 정보 저장
		return "webboard/boardlist"; //webboard/boardlist.html
	}
	
	@GetMapping("/detail")
	void detail(Long bno, Model model) {
		model.addAttribute("board", boardService.selectById(bno)); 
	}
	
	@PostMapping("/modify")
	String update(WebBoardDTO dto, RedirectAttributes attr) {
		boardService.modify(dto);
		attr.addFlashAttribute("msg", "수정완료");
		return "redirect:list";
	}
	@PostMapping("/delete")
	String delete(Long bno, RedirectAttributes attr) {
		boardService.delete(bno);
		attr.addFlashAttribute("msg", "삭제완료");
		return "redirect:list";
	}
}

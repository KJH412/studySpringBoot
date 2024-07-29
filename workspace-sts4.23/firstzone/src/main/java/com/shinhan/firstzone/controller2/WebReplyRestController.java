package com.shinhan.firstzone.controller2;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.shinhan.firstzone.service2.WebReplyService;
import com.shinhan.firstzone.vo4.WebReplyDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

//@Controller : 요청->서비스수행->data,page가 return  (page는 Thymeleaf template를 사용중)
//@RestController : 요청->서비스수행->data return (ReactJs에서 사용할 예정)

@Tag(name = "댓글", description = "여기에서는 WebReply CRUD 작성됨") 
@RestController  //뷰는 리액트 사용 -> 백엔드는 RestController 사용해야한다.
@RequestMapping("/replies")
@RequiredArgsConstructor //final인 field들 @Autowired
public class WebReplyRestController {
	
	final WebReplyService replyService;
	
	@Tag(name = "댓글list", description = "댓글조회")
	@GetMapping("/list/{bno}")
	List<WebReplyDTO> list(@PathVariable("bno") Long bno) {
		return replyService.getList(bno);
	}
	@PostMapping("/register")
	Long insert(@RequestBody WebReplyDTO dto) {
		return replyService.register(dto);
	}
	@PutMapping("/modify")
	String update(@RequestBody WebReplyDTO dto) {
		replyService.modify(dto);
		return "success";
	}
	@DeleteMapping("/delete/{rno}")
	String delete(@PathVariable("rno") Long rno) {
		replyService.delete(rno);
		return "success";
	}
	
}

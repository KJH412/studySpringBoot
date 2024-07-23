package com.shinhan.firstzone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.firstzone.service.GuestBookSerivce;
import com.shinhan.firstzone.vo2.GuestBookDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guest")
public class GuestBookRestController {
	
	final GuestBookSerivce gService;

	//gService.getSearch가 안찾아졌던 이유?
	//GuestBookServiceImpl(서비스)에서 구현하기 전에
	//GuestBookSerivce(interface)에 먼저 정의 한 후 컨트롤러에서 사용했어야 함.
	@GetMapping("/search")
	List<GuestBookDTO> search(String type, String keyword){
		return gService.getSearch(type, keyword);
	}
	
	@GetMapping("/list")
	List<GuestBookDTO> list(){
		return gService.readAll();
	}
	
	@GetMapping("/get/{gno}")
	GuestBookDTO read(@PathVariable("gno") Long gno){
		return gService.readById(gno);
	}
	
	@PostMapping("/insert")
	String insert(@RequestBody GuestBookDTO dto) {
		gService.create(dto);
		return "입력작업";
	}
	
	@PutMapping("/update")
	String update(@RequestBody GuestBookDTO dto) { //@RequestBody로 받는 이유?
		gService.update(dto);
		return "수정작업";
	}
	@DeleteMapping("/delete")
	String delete(@PathVariable("gno") Long gno) {
		gService.delete(gno);
		return "수정작업";
	}
}

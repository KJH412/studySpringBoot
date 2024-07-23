package com.shinhan.firstzone.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.firstzone.repository.BoardRepository;
import com.shinhan.firstzone.vo.BoardEntity;
import com.shinhan.firstzone.vo.CarVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController //@Controller + @ResponseBody(응답문서) , @RestController:데이터만 받고 view가 필요 없음.
@RequestMapping("/sample")
public class SampleRestController {
	
	@Autowired
	BoardRepository bRepo;
	

	
	//삭제 @DeleteMapping
	@DeleteMapping("/delete/{bno}")
	public String delete(@PathVariable("bno") Long bno) {
		bRepo.deleteById(bno);
		return "삭제성공";
	}
	
	//수정 @PutMapping
	@PutMapping("/update")
	public String update(@RequestBody BoardEntity board) {
		bRepo.findById(board.getBno()).ifPresent(b -> { //기존 bno값(1)을 가져온 후 update를 진행함.
			log.info("before:" + b);
			
			board.setRegDate(b.getRegDate());
			
			//b.setContent("~~~수정~~~~~");
			//b.setTitle("springboot");
			//b.setWriter("manager");
			
			BoardEntity updateBoard = bRepo.save(board); 
			log.info("after:" + updateBoard);
		});
		return "수정성공";
	}
	
	//입력 (postman으로 확인)
	@PostMapping("/insert")
	public Long insert(@RequestBody BoardEntity board) {
		BoardEntity newBoard = bRepo.save(board);
		return newBoard.getBno();
	}
	
	//상세보기
	@GetMapping("/detail/{bno}")
	public BoardEntity detail(@PathVariable("bno") Long bno){
		return bRepo.findById(bno).orElse(null);
	}
	//조회
	@GetMapping("/list")
	public List<BoardEntity> list(){
		return (List<BoardEntity>)bRepo.findAll();
	}
	
	@GetMapping("/test1")
	public String f1() {
		return "Hello~~~반갑습니다.";
	}
	
	@GetMapping("/test2")
	public CarVO f2() {
		CarVO car = CarVO.builder()
					.model("ABC모델")
					.price(2000)
					.build();
		return car;
	}
	
	@GetMapping("/test3")
	public List<CarVO> f3() {
		List<CarVO> carList = new ArrayList<>();
		IntStream.rangeClosed(1,5).forEach(i->{
			CarVO car = CarVO.builder()
					.model("ABC모델" + i)
					.price(2000 * i)
					.build();
			
			carList.add(car);
		});
		
		return carList;
	}
	
}

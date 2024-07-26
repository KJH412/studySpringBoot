package com.shinhan.firstzone.paging;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data  //PageResultDTO<DTO, EN>타입으로 변환하기 (WebBoardSerivce 조회참고)
public class PageResultDTO<DTO, EN> { //페이징은 여러곳에서 사용할 것이기 때문에 제네릭타입<>가변으로 받는다.
	//data
	private List<DTO> dtoList;  //데이터는 여기 들어 있음. 이걸로 데이터 출력함
	// 총페이지번호
	private int totalPage;
	// 현재페이지 번호
	private int page;
	// 목록사이즈
	private int size;
	// 시작페이지번호, 끝페이지번호
	private int start, end;
	// 이전, 다음
	private boolean prev, next;
	// 페이지번호 목록
	private List<Integer> pageList;
	// Page<Entity>객체들을 DTO객체로 변환해서 자료구조에 넣기 
	//<EN, DTO>:Entity가 들어가면 DTO가 반환. <>들아가는 타입은 가변이다.
	public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) { 
		dtoList = result.stream().map(fn).collect(Collectors.toList());
		totalPage = result.getTotalPages();
		makePageList(result.getPageable());	 
	}
	private void makePageList(Pageable pageable) {
		this.page = pageable.getPageNumber() + 1;
		this.size = pageable.getPageSize();
		int tempEnd = (int)(Math.ceil(page/10.0))*10;//ceil:올림 //1~10 start:1,end:10/ 11~20
		//한화면에 10개의 페이지번호를 출력하기로 가정함 (10으로 나눔)
		start = tempEnd - 9;   //시작은 1	
		end = totalPage > tempEnd?tempEnd:totalPage;
		end = totalPage < 10 ? totalPage : end;  //10개보다 작다면 page번호까지만 		
		prev = start > 1;  //전페이지기 있는가?
		next = totalPage > tempEnd;
		System.out.printf("tempEnd:%d, totalPage:%d start:%d end:%d \n" ,
				tempEnd, totalPage,start,end);
		//int -> Integer
		pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
	}
}




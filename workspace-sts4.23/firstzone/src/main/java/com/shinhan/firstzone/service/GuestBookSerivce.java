package com.shinhan.firstzone.service;

import java.util.List;

import com.shinhan.firstzone.vo2.GuestBookDTO;
import com.shinhan.firstzone.vo2.GuestBookEntity;

public interface GuestBookSerivce { //interface에서 설계
	
	//들어오고 나가는건 DTO이고,
	//DB와 소통하는 것은 Entity이다.
	
	//CRUD
	//1.Create
	void create(GuestBookDTO dto);  //DTO를 받는다. 생략(public abstract) void create
	//2.Read
	List<GuestBookDTO> readAll();
	
	GuestBookDTO readById(Long gno);
	
	public List<GuestBookDTO> getSearch(String type, String keyword); 
	
	//3.Update (리턴 없음)
	void update(GuestBookDTO dto);
	//4.Delete
	void delete(Long gno);
	
	//interface는 default,static 가능
	
	/* DTO와 Entity가 서로 소통하는 함수 만듦 */
	
	//Entity -> DTO
	default GuestBookDTO entityToDTO(GuestBookEntity entity) {
			GuestBookDTO dto = GuestBookDTO.builder()
					.gno(entity.getGno())
					.title(entity.getTitle())
					.content(entity.getContent())
					.writer(entity.getWriter())
					.regDate(entity.getRegDate())
					.modDate(entity.getModDate())
					.build();
			return dto;
	}
	//요청이 들어올 때 DTO가 들어오는데 DTO를 Entity로 바꿔서 
	
	//DTO -> Entity
	default GuestBookEntity dtoToEntity(GuestBookDTO dto){
		GuestBookEntity entity = GuestBookEntity.builder()
				.title(dto.getTitle())
				.content(dto.getContent())
				.writer(dto.getWriter())
				.build();
		return entity;
	}
	
}

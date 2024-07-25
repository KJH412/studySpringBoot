package com.shinhan.firstzone.service2;

import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo4.QWebBoardEntity;
import com.shinhan.firstzone.vo4.WebBoardDTO;
import com.shinhan.firstzone.vo4.WebBoardEntity;

//브라우저-insert->|    JPA|-->DB
//브라우저<-(보드정보,멤버정보)조회-|    웹보드엔티티<-JAP|<--DB
// =>db에서 가져온 데이터와 브라우저에 보여주는 데이터 형태가 다르다. DTO필요
public interface WebBoardService { //설계
	//CRUD작업제공
	//1.입력
	Long register(WebBoardDTO dto);
	//2.조회
	List<WebBoardDTO> getList();
	WebBoardDTO selectById(Long bno);
	//3.수정
	void modify(WebBoardDTO dto);
	//4.삭제
	void delete(Long bno); 
	
	//동적 SQL만들기
	//t->title/ c->content/ w->writer/ tc->title,content/ tcw->title,content,writer
	public default Predicate makePredicate(String type, String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		QWebBoardEntity board = QWebBoardEntity.webBoardEntity;
		builder.and(board.bno.gt(0));
		//검색조건처리
		if(type==null) return builder;
		
		BooleanBuilder builder2 = new BooleanBuilder();
		if(type.contains("t"))builder2.or(board.title.like("%"+keyword+"%"));
		if(type.contains("c"))builder2.or(board.content.like("%"+keyword+"%"));
		if(type.contains("w"))builder2.or(board.writer.mname.like("%"+keyword+"%"));
		builder.and(builder2); //원래조건+3개의조건
		
		return builder;
	}
	
	//DTO => Entity (DB에 반영하기 위함)
	 default WebBoardEntity dtoToEntity(WebBoardDTO dto) {
		 MemberEntity member = MemberEntity.builder().mid(dto.getMid()).build();
		 //date는 타임스탬프로 찍힐거라 안가져와도 됨.
		 WebBoardEntity entity = WebBoardEntity.builder()
				 .bno(dto.getBno())
				 .title(dto.getTitle())
				 .content(dto.getContent())
				 .writer(member)
				 .build();
		 
		 return entity;
	 }
	 
	//interface를 구현하고 싶을 때? default 사용!
	//Entity => DTO  (Data전송을 위함, controller, service, view에서 작업)
	default WebBoardDTO entityToDTO(WebBoardEntity entity){
		WebBoardDTO dto = WebBoardDTO.builder()
				.bno(entity.getBno())
				.title(entity.getTitle())
				.content(entity.getContent())
				.mid(entity.getWriter().getMid())
				.mname(entity.getWriter().getMname())
				.regdate(entity.getRegdate())
				.updatedate(entity.getUpdatedate())
				.replyCount(entity.getReplies().size())
				.build();
		return dto;
	}
}
package com.shinhan.firstzone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.shinhan.firstzone.repository.GuestBookRepository;
import com.shinhan.firstzone.vo2.GuestBookDTO;
import com.shinhan.firstzone.vo2.GuestBookEntity;
import com.shinhan.firstzone.vo2.QGuestBookEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //필수 field를 constructor를 통해 injection
public class GuestBookServiceImpl implements GuestBookSerivce {
	
	final GuestBookRepository gRepo;
	
	/* 이렇게 쓰는 대신 @RequiredArgsConstructor로 작성
	@Autowired
	GuestBookRepository gRepo;
	GuestBookServiceImpl(GuestBookRepository gRepo){
		this.gRepo = gRepo
	}
	*/
	
	@Override
	public void create(GuestBookDTO dto) {
		GuestBookEntity entity = dtoToEntity(dto);
		gRepo.save(entity); //엔티티를 레파지토리에 저장하는 것이므로 dto를 entity로 변환!
		
	}

	@Override
	public List<GuestBookDTO> readAll() {
		//Type mismatch: cannot convert from Iterable<GuestBookEntity> to List<GuestBookEntity>
		//형변환 필요
		List<GuestBookEntity> entityList = (List<GuestBookEntity>) gRepo.findAll();
		List<GuestBookDTO> dtoList = entityList.stream().map(entity -> entityToDTO(entity))
														.collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public GuestBookDTO readById(Long gno) {
		GuestBookEntity entity = gRepo.findById(gno).orElse(null);
		return entityToDTO(entity);
	}

	@Override
	public void update(GuestBookDTO dto) {
		//GuestBookEntity entity = dtoToEntity(dto);
		
		gRepo.findById(dto.getGno()).ifPresent(book -> {
			book.setContent(dto.getContent());
			book.setTitle(dto.getTitle());
			book.setWriter(dto.getWriter());
			gRepo.save(book);
		});
		
		gRepo.save(dtoToEntity(dto));
		
	}

	@Override
	public void delete(Long gno) {
		gRepo.deleteById(gno);
	}
	
	
	public List<GuestBookDTO> getSearch(String type, String keyword) {//type과 keyword가 가변으로 들어옴.
		//t->title, c->content, w->writer, tc->title,content, tcw ->title,content,writer
		
		BooleanBuilder builder = new BooleanBuilder(); 
		QGuestBookEntity entity = QGuestBookEntity.guestBookEntity;
		BooleanExpression expression = entity.gno.gt(0L); //gno > 0
		builder.and(expression); //select b from GuestBookEntity b where b.gno > 0
		//builder.and = where
		
		
		BooleanBuilder builder2 = new BooleanBuilder();
		//포함된 문자를 찾아서 builder에 더한다.
		if(type.contains("t")) {
			builder2.or(entity.title.contains(keyword));
		}
		if(type.contains("c")) {
			builder2.or(entity.title.contains(keyword));
		}
		if(type.contains("w")) {
			builder2.or(entity.title.contains(keyword));
		}
		builder.and(builder2); //and (title like ? or content like ? or writer like ?)
	
		//findAll() : Predicate predicate => builder를 넣어준다.
		List<GuestBookEntity> entityList = (List<GuestBookEntity>) gRepo.findAll(builder);
		List<GuestBookDTO> dtoList = entityList.stream().map(e -> entityToDTO(e))
														.collect(Collectors.toList());
		return dtoList;
	}
	
	
}

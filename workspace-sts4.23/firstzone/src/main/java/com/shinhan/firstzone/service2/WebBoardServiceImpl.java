package com.shinhan.firstzone.service2;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shinhan.firstzone.paging.PageRequestDTO;
import com.shinhan.firstzone.paging.PageResultDTO;
import com.shinhan.firstzone.repository4.WebBoardRepository;
import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo4.WebBoardDTO;
import com.shinhan.firstzone.vo4.WebBoardEntity;

@Service
public class WebBoardServiceImpl implements WebBoardService {

	@Autowired
	WebBoardRepository boardRepo;
	
	@Override
	public Long register(WebBoardDTO dto) {
		//dto를 entity로 바꿔서 DB에 넣어준다.
		WebBoardEntity newEntity = boardRepo.save(dtoToEntity(dto));
		return newEntity.getBno(); //입력한 보드번호만 리턴
	}

	@Override
	public List<WebBoardDTO> getList() { //리스트로 만들기
		//stream에 map이 들어있음.
		List<WebBoardDTO> blist = boardRepo.findAll().stream().map(en -> entityToDTO(en)).collect(Collectors.toList());
		return blist;
	}
	
	//페이징 처리
	public PageResultDTO<WebBoardDTO, WebBoardEntity> getList(PageRequestDTO pageRequestDTO){
		//querydsl제공 메서드 => findAll(Predicate,Pageable)
		//Predicate: 데이터의 값이 조건을 만족하는지 검사한다는 의미
		 Page<WebBoardEntity> result = boardRepo.findAll(
				 				//타입과 키워드로 검사
				 				makePredicate(pageRequestDTO.getType(), pageRequestDTO.getKeyword()), //Predicate
				 				pageRequestDTO.getPageable(Sort.by("bno").descending())); //Pageable sort 
		Function<WebBoardEntity, WebBoardDTO> fn = en -> entityToDTO(en); 
		PageResultDTO<WebBoardDTO, WebBoardEntity> result2 = new PageResultDTO<>(result, fn);
//		<A,B> A가 들어오면 B로 바꾼다.
		return result2;
	};
	

	@Override
	public WebBoardDTO selectById(Long bno) { 
		//ifPresentOrElse : 있을때,없을때
		/*Optional<WebBoardEntity> en = boardRepo.findById(bno);
		if(en.isPresent()) { //있다면
			return entityToDTO(en.get());
		}else {
			return null;
		}*/
		WebBoardEntity en = boardRepo.findById(bno).orElse(null);
		if(en==null) return null;
		return entityToDTO(en);
	}

	@Override
	public void modify(WebBoardDTO dto) { //수정
		boardRepo.findById(dto.getBno()).ifPresent(en->{
			en.setContent(dto.getContent());
			en.setTitle(dto.getTitle());
			MemberEntity member = MemberEntity.builder().mid(dto.getMid()).build();
			en.setWriter(member);
			boardRepo.save(en);
		});
		
	}

	@Override
	public void delete(Long bno) {
		boardRepo.deleteById(bno);
	}
}

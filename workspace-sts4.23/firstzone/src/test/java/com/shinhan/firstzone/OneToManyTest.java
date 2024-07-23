package com.shinhan.firstzone;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.repository.PDSBoardRepository;
import com.shinhan.firstzone.repository.PDSFileRepository;
import com.shinhan.firstzone.vo2.PDSBoardEntity;
import com.shinhan.firstzone.vo2.PDSFileEntity;

@SpringBootTest
public class OneToManyTest {
	
	@Autowired
	PDSFileRepository fileRepo;
	
	@Autowired
	PDSBoardRepository boardRepo;
	
	
	
	//조회
	@Test
	void selectBoard() {
		Long pid = 1L;
		
		PDSBoardEntity board = boardRepo.findById(pid).orElse(null);
		if(board == null) return;
		//board전체를 읽어올 때 List<PDSFileEntity> files때문에 오류발생할 수 있어서 Lazy패치타입 + ToString(exclude)
		System.out.println(board); 
		System.out.println(board.getPname());
		System.out.println(board.getWriter());
		//System.out.println(board.getFiles().size());
		//System.out.println(board.getFiles());
	}
	 
	
	//@Test
	void selectFile() {
		Long fno = 10L; //fno=10
		PDSFileEntity file = fileRepo.findById(fno).orElse(null);
		System.out.println(file);
	}
	
	//@Test
	void insert2() {		
		//board:10건
		IntStream.rangeClosed(1, 10).forEach(j -> {
			//file:5건 * 10
			List<PDSFileEntity> files = new ArrayList<>();
			IntStream.rangeClosed(1, 5).forEach(i -> {
				PDSFileEntity file = PDSFileEntity.builder()
						.pdsfilename("스프링Book-"+ j + "-" + i + ".ppt")				
						.build();
				files.add(file); //위에서 반복문 돌면서 생성된 값을 array에 add한다.
			});
			PDSBoardEntity board = PDSBoardEntity.builder()
									.pname("파이썬교육")
									.writer("수영")
									.files(files)
									.build();
			boardRepo.save(board);
		});
	
	}
	
	//@Test
	void insert() {
		List<PDSFileEntity> files = new ArrayList<>();
		IntStream.rangeClosed(1, 5).forEach(i -> {
			PDSFileEntity file = PDSFileEntity.builder()
					.pdsfilename("스프링Book-"+   i + ".ppt")				
					.build();
			files.add(file); //위에서 반복문 돌면서 생성된 값을 array에 add한다.
		});

	}
}
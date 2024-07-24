package com.shinhan.firstzone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.shinhan.firstzone.repository.PDSBoardRepository;
import com.shinhan.firstzone.repository.PDSFileRepository;
import com.shinhan.firstzone.vo2.PDSBoardEntity;
import com.shinhan.firstzone.vo2.PDSFileEntity;

import jakarta.transaction.Transactional;

@Commit
@SpringBootTest
public class OneToManyTest {   //1:N관계
	
	@Autowired
	PDSFileRepository fileRepo;  //N
	
	@Autowired
	PDSBoardRepository boardRepo;  
	
	
	//삭제
	@Test
	void deleteBoard() {
		//부모키인 pno=9를 삭제하면 자식(참조)인 file의 psdno=9가 전부 삭제됨 => 1:N
		boardRepo.deleteById(9L); 
	}
	
	//@Test
	void deleteFile() {
		fileRepo.deleteById(49L);
		
	}
	
	//boardRepo에서 PDSFile를 수정하기, 직접 SQL작성
	//@Transactional추가하기, 실행 후 test환경은 rollback처리된다. class LEVEL에서 commit추가
//	@Transactional
//	@Test
	void updateFile() {
		boardRepo.updatePDSFile(50L, "파일이름수정");
	}
	
	//PDSBoard의 이름변경 : 본래이름뒤에 pid붙이기
	//@Test
	void update() {
		boardRepo.findAll().forEach(board->{
		 String name =  board.getPname() + "-" + board.getPid();
		 board.setPname(name);
		 boardRepo.save(board);
		});
	}
	
	//@Test
	void selectBoard6() {
		boardRepo.getFilesCount2().forEach(arr->{
			System.out.println(Arrays.toString(arr));
		});
	}
	
	//@Test
	void selectBoard5() {
		long pid = 4L;
		List<Object[]> blist = boardRepo.getFilesInfo2(pid);
		blist.forEach(arr -> System.out.println(Arrays.toString(arr)));
	}
	
	
	//입력....board의 file추가
	//@Test
	void insertBoard() {
		//pdsno=4로 파일 insert + psdno로 연결된 pid=4의 writer(작성자) 업데이트 
		PDSBoardEntity board = boardRepo.findById(4L).orElse(null);
		if(board == null) return;
		List<PDSFileEntity> flist = board.getFiles();
		PDSFileEntity file1 = PDSFileEntity.builder().pdsfilename("파일추가1.jpg").build();
		PDSFileEntity file2 = PDSFileEntity.builder().pdsfilename("파일추가2.jpg").build();
		flist.add(file1);
		flist.add(file2);
		board.setWriter("작성자 수정"); //board업데이트
		boardRepo.save(board);
		
	}
	
	
	//조회
	//@Transactional  //FetchType.EAGER일 때는 선언 필요 없음.
	//@Test
	void selectBoard4() {
		PDSBoardEntity board = boardRepo.findById(4L).orElse(null); //get은 잘 안씀. 대신 orElse
		if(board == null) return;
			System.out.println(board);
			System.out.println(board.getPname());
			System.out.println(board.getFiles().size() + "건");
	}
	
	//@Test
	void selectBoard3() {
		String writer = "수영";
		List<PDSBoardEntity> blist = boardRepo.findByWriter(writer);
		for(PDSBoardEntity board : blist) {
			System.out.println(board);
			System.out.println(board.getPname());
			System.out.println(board.getFiles().size() + "건");
		}
	}
	
	
	//PDSFileEntity의 pdsfilename으로 조회  !!
	//@Test
	void selectFile2() {
		String pdsfilename = "스프링Book-8-4.ppt";
		PDSFileEntity file = fileRepo.findByPdsfilename(pdsfilename);
		System.out.println(file);
	}
	
	//
	
	//@Test
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

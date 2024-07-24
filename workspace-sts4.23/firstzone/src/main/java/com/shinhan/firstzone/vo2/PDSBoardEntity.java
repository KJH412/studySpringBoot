package com.shinhan.firstzone.vo2;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//하나의 Board에 첨부File이 여러개있다.
@Table(name = "t_pdsboard")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString//(exclude = {"files"}) //ToString에서 특정 필드를 제외한다. files는 fetch타입이 LAZY이기 때문에 로딩되지 않음. -> Test 시 오류 발생
public class PDSBoardEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long pid;
	String pname;
	String writer;
	
	 //수정,삭제...을 수행하면 PDSFileEntity에 영향을 미친다.
	//cascade : 연쇄작용
	//EAGER : 조회할 때 PDSFileEntity도 부름
	//LAZY : 조회할 때 PDSFileEntity 부르지 않는다.
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)//default fetch = LAZY   
	@JoinColumn(name = "pdsno") //PDSFileEntity의 테이블에 pdsno칼럼이 생성된다.
	//@JoinColumn(조인칼럼)이 생략되면? 중간테이블이 생성된다. PDSBoard키, PDSFileEntity키를 칼럼으로 갖는 중간테이블만들어짐.
	List<PDSFileEntity> files;
}

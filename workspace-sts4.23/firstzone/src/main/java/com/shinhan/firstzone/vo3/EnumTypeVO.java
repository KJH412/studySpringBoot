package com.shinhan.firstzone.vo3;
import java.util.Set;

import com.shinhan.firstzone.vo2.MemberRole;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name="t_enum")
public class EnumTypeVO {

	@Id
	private String mid;
	private String mpassword;
	private String mname;
	
	//@ElementCollection:기본값이 아닌 값 타입 컬렉션을 매핑할 때 사용
	//@CollectionTable: 값 타입 컬렉션이 저장될 테이블을 지정
	//joinColumns: tbl_enum_mroles 테이블의 mid 컬럼을 사용하여 조인
	@ElementCollection(targetClass = MemberRole.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "tbl_enum_mroles", 
    	joinColumns = @JoinColumn(name = "mid")) //tbl_enum_mroles테이블의 조인칼럼
	private Set<MemberRole> mrole;
}
	
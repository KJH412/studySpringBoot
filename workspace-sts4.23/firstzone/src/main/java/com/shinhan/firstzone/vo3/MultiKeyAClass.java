package com.shinhan.firstzone.vo3;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "t_child1")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(MultiKeyA.class)//복합 키(Composite Key)를 사용하여 엔티티를 식별할 때 사용
public class MultiKeyAClass {
	@Id
	Integer id1;
	@Id
	Integer id2;
	String productName;
	int price;
}
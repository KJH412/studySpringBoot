package com.shinhan.firstzone.vo3;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable //복합키로 사용하겠다는 의미
public class MultiKeyB implements Serializable{
	private static final long serialVershionUID = 1L;
	Integer id1;
	Integer id2;
}

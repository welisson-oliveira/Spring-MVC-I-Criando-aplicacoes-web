package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;

public class DadosDePagamento {
	
	private BigDecimal value;

	public DadosDePagamento(BigDecimal value){
		this.value = value;
	}
	
	public DadosDePagamento( ){
		
	}

	public BigDecimal getValue() {
		return value;
	}
	

}

package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;


// quando marcarmos a classe CarrinhoCompras com a anotação @Component, 
// transformando nossa classe em um Bean do Spring estamos também configurando que este objeto será Singleton(unico para todos os usuarios da applicação). 
// Por padrão, o Spring tem esta configuração para os seus componentes.
@Component
// com esse parametro 'proxyMode=ScopedProxyMode.TARGET_CLASS' o Spring criará um proxy envolvendo o objeto alvo (TARGET_CLASS) 
// afim de possibilitar que as informações possam ser transitadas de um escopo para o outro.
// dessa forma não precisamos mudar mais o escopo dos controllers que utilizam essa classe.
@Scope(value=WebApplicationContext.SCOPE_SESSION, proxyMode=ScopedProxyMode.TARGET_CLASS)
public class CarrinhoCompras {
	
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<>();
	
	public void add(CarrinhoItem item){
		itens.put(item, getQuantidade(item) + 1);
	}
	
	public int getQuantidade(){
	    return itens.values().stream().reduce(0, (proximo, acumulador) -> (proximo + acumulador));
	}

	public int getQuantidade(CarrinhoItem item) {
		if(!itens.containsKey(item)){
	        itens.put(item, 0);
	    }
	    return itens.get(item);
	}
	
	public Collection<CarrinhoItem> getItens(){
		return itens.keySet();
	}
	
	public BigDecimal getTotal(CarrinhoItem item){
        return item.getTotal(getQuantidade(item));
    }
	
	public BigDecimal getTotal(){
	    BigDecimal total = BigDecimal.ZERO;
	    for (CarrinhoItem item : itens.keySet()) {
	        total = total.add(getTotal(item));
	    }
	    return total;
	}

	public void remover(Integer produtoId, TipoPreco tipoPreco) {
		Produto produto = new Produto();
		produto.setId(produtoId);
		itens.remove(new CarrinhoItem(produto, tipoPreco));
	}
    
}

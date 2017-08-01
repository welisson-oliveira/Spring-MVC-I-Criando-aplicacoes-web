package br.com.casadocodigo.loja.controllers;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.DadosDePagamento;
import br.com.casadocodigo.loja.models.TipoPreco;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {
	
	// Utilizamos o @AutoWired para indicar ao Spring que o objeto anotado é um Bean 
	// dele e que queremos que ele nos dê uma instância por meio do recurso de injeção 
	// de dependência.
	@Autowired
	private CarrinhoCompras carrinho;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value="/finalizar", method=RequestMethod.POST)
	public Callable<ModelAndView> finalizar(RedirectAttributes model){
		
		return () -> {
			
			final String uri = "http://book-payment.herokuapp.com/payment";
			
			try {
				String response = restTemplate.postForObject(uri, new DadosDePagamento(carrinho.getTotal()), String.class);
				
				System.out.println(response);
				
				model.addFlashAttribute("sucesso", response);
				
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				model.addFlashAttribute("falha", "Valor maior que o permitido");
			}
			return new ModelAndView("redirect:/produtos");
		};
				
	}

}

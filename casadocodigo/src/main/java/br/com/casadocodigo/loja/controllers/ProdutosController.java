package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {

	@Autowired // faz com que o spring instancie o objeto desde que ProdutoDao
				// seja gerenciado pelo Spring
	private ProdutoDAO produtoDao;
	
	@Autowired
	private FileSaver fileSaver;

	/**
	 * Recebe um objeto do tipo {@link WebDataBinder} que possui um metodo
	 * addValidators que recebe uma instancia de uma classe que implemente a
	 * interface {@link Validator} do pacote org.springframework.validation
	 * 
	 * @param binder
	 *            - responsavel por conectar duas coisas, no nosso caso o
	 *            formulario com o objeto da classe {@link Produto} passado pelo
	 *            Spring
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
	}

	@RequestMapping("/form")
	public ModelAndView form(Produto produto) {
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());

		return modelAndView;
	}

	// Note que o BindingResult vem logo após o atributo que está assinado com a
	// anotação @Valid, essa ordem não é por acaso, precisa ser desta forma para
	// que o Spring consiga fazer as validações da forma correta
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {// Flash Scoped
		
		
		if (result.hasErrors()) {
			return form(produto);
		}
		
		//salva o arquivo na pasta 'arquivos-sumario' no servidor e retorna o path de onde o arquivo foi salvo
		String path = fileSaver.write("arquivos-sumario", sumario);
		
		produto.setSumarioPath(path);
		
		produtoDao.gravar(produto);

		// RedirectAttributes utilizado para enviar os atributos de um request
		// para o outro atraves do addFlashAttribute
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");

		// "redirect:produtos" evita que a requisição permaneça ativa, após o
		// post será feito um get em /produtos
		return new ModelAndView("redirect:produtos");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar() {
		List<Produto> produtos = produtoDao.listar();
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", produtos);
		return modelAndView;
	}
	
	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") int id){
	    ModelAndView modelAndView = new ModelAndView("/produtos/detalhe");
	    Produto produto = produtoDao.find(id);
	    modelAndView.addObject("produto", produto);
	    return modelAndView;
	}

}

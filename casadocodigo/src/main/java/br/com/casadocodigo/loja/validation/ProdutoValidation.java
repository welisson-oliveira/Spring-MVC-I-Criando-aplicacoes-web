package br.com.casadocodigo.loja.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.models.Produto;
/**
 * 
 * @author welisson oliveira
 * Classe utilizada para validar um objeto da classe {@link Produto}
 * 
 *
 */
public class ProdutoValidation implements Validator {
	
	/**
	 * Indica a qual classe a validação dará suporte, verificando se o objeto recebido para a validação tem uma assinatura da classe {@link Produto}
	 * 
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Produto.class.isAssignableFrom(clazz);
	}
	
	/**
	 * Método que realiza a validação
	 */
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "titulo", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "descricao", "field.required");
		Produto produto = (Produto) target;
		if(produto.getPaginas() <= 0){
			errors.rejectValue("paginas", "field.required");
		}
		
	}
	
}

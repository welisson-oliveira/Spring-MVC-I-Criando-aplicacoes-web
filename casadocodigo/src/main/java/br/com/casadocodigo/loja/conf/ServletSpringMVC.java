package br.com.casadocodigo.loja.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


/**
 * 
 * @author welisson oliveira
 * A classe {@link ServletSpringMVC} serve para que o servidor(tomcat) passe a responsabilidade de atender as requisições ao Spring MVC.\n
 * Com essas configurações estamos definindo que o servlet do SpringMVC atenderá as requisições a partir da raiz do nosso projeto (/) \n
 * e que a classe AppWebConfiguration será usada como classe de configuração do servlet do SpringMVC.
 * Aqui configuramos tbm o encoding da aplicação.
 * Aqui configuramos tbm o método para que o spring possa receber arquivos em requisições
 *
 */
public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}
	
	/**
	 * Pede um array de classes de configurações que serao disponibilizados ao Spring .
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {AppWebConfiguration.class, JPAConfiguration.class, ResourcesConfig.class};
	}

	/**
	 * Pede um array de mapeamentos que queremos que nosso servlet atenda.
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	/**
	 * Pede um array de filtros no qual definimos o encoding da nossa aplicação.
	 */
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		
		return new Filter[]{encodingFilter};
	}
	
	/**
	 *  Esse método é necessario para que o Spring consiga fazer a conversão dos dados recebidos em uma <br />
	 *  requisição multpart/form-data. <br />
	 *  O método setMultipartConfig requer um objeto do tipo MultipartConfigElement. <br />
	 *  O MultipartConfigElement espera receber uma String que configure o arquivo. <br />
	 *  Não usaremos nenhuma configuração para o arquivo, queremos receber este do jeito que vier.<br /> 
	 *  Passamos então uma String vazia.
	 */
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
	

}

package br.com.casadocodigo.loja.conf;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.CarrinhoCompras;
/**
 * 
 * @author welisson oliveira
 * A classe {@link AppWebConfiguration} é utilizada como classe de configuração do servlet do SpringMVC.
 *
 */
@EnableWebMvc // anotação utilizada para ativar o recurso de Web MVC do SpringMVC
@ComponentScan(basePackageClasses={HomeController.class, ProdutoDAO.class, FileSaver.class, CarrinhoCompras.class}) //anotação utilizada para configurar o caminho(pacote) onde o SpringMVC irá encontrar nossas classes e assim gerencia-las
public class AppWebConfiguration {
	
	/**
	 * 	
	 * @return retorna um objeto do tipo {@link InternalResourceViewResolver} (Resolvedor Interno de Recursos de View) que ajuda o SpringMVC a 
	 * encontrar as views.
	 */
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		
		//configuração que permite com que as views acessem todas as classes gerenciadas pelo spring 
		//resolver.setExposeContextBeansAsAttributes(true);
		
		//configuração que permite com que as views acessem um componente(classe com @Component) 
		// gerenciado pelo Spring especifico, nesse caso o bean carrinhoCompras
		resolver.setExposedContextBeanNames("carrinhoCompras");
				
		return resolver;
	}
	
	/**
	 * Responsavel por carregar nossos arquivos de mensagens<br />
	 * No objeto retornado estão definidas 3 propriedades:<br />
	 * <i><b>setBaseName</b></i> - com o valor /WEB-INF/message que terá o nome base dos nossos resources<br />
	 * <i><b>setDefaultEncoding</b></i> - com o valor UTF-8<br />
	 * <i><b>setCacheSeconds</b></i> - para que o Spring recarregue o arquivo de tempos em tempos com o valor 1<br />
	 * @return retorna um objeto do tipo {@link MessageSource} 
	 */
	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("WEB-INF/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);
		return messageSource;
	}

	/**
	 * 
	 * @return retorna um objeto do tipo {@link FormattingConversionService} 
	 * Cria um objeto um objeto do tipo {@link DefaultFormattingConversionService} que será o responsável pelo serviço de conversão de formato.
	 * Cria um objeto do tipo {@linkDateFormatterRegistrar} que fará o registro do formato de data usado para a conversão.
	 * Assim não precisaremos mais informar o pattern na anotação {@link @DateTimeFormat}
	 */
	@Bean
	private FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		DateFormatterRegistrar formatterRegistrar = new DateFormatterRegistrar();
		formatterRegistrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		formatterRegistrar.registerFormatters(conversionService);
		
		return conversionService;
	}
	
	/**
	 * Configura um {@link MultipartResolver} que é um resolvedor de dados multimidia. <br />
	 * Quando tempos texto e arquivos é esse ojbeto que identifica cada um dos recursos enviados e nos fornece uma forma<br />
	 * mais simples de manipulalos.<br /> 
	 * Este objeto será instanciado da classe StandardServletMultipartResolver e retornado.
	 */
	@Bean
	public MultipartResolver multipartResolver(){
		return new StandardServletMultipartResolver();
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
}

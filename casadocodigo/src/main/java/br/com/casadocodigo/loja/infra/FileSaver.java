package br.com.casadocodigo.loja.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author welisson oliveira
 * Classe utilizada para salvar um arquivo em uma pasta do servidor.
 *
 */
@Component
public class FileSaver {
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * Método utilizado para fazer a transferencia do arquivo.<br />
	 * Monta uma String que indica o caminho do arquivo a ser salvo<br />
	 * Com a String montada, cria um novo objeto do tipo {@link File} que irá<br />
	 * representar o arquivo a ser salvo no servidor.<br />
	 * O arquivo é passado para o método transferTo que é responsavel <br />
	 * por transferir o arquivo para o servidor.
	 * @param baseFolder - local onde o arquivo será salvo
	 * @param file - nome do arquivo
	 * @return retorna o path onde o arquivo foi salvo.
	 */
	public String write(String baseFolder, MultipartFile file){
		try {
			//retorna o caminho completo de onde está determinada pasta dentro do servidor
			String realPath = request.getServletContext().getRealPath("/"+baseFolder);
            String path = realPath + "/" + file.getOriginalFilename();
            file.transferTo(new File(path));
            return baseFolder + "/" + file.getOriginalFilename();

        } catch (IllegalStateException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

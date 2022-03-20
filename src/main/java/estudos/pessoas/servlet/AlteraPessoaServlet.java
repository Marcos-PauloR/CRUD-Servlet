package estudos.pessoas.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import estudos.pessoas.control.PessoaControle;
import estudos.pessoas.dao.PessoaDao;
import estudos.pessoas.model.Pessoa;

/**
 * Servlet implementation class AlteraPessoaServlet
 */
@WebServlet("/crud")
public class AlteraPessoaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private PessoaControle pessoaControle = new PessoaControle();
	private Gson gson;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		gson = new GsonBuilder().create();
		gson = new GsonBuilder().setPrettyPrinting().create();
		String codigo = req.getParameter("codigo");
		int codInt = Integer.parseInt(codigo);
		
		Pessoa pessoa = new Pessoa();
		pessoa = pessoaControle.getPessoaPorId(codInt);
		
		String pessoaJsonString = this.gson.toJson(pessoa);
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		out.print(pessoaJsonString);
		out.flush();
	}

	 @Override
	 protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	 	gson = new GsonBuilder().create();
	 	gson = new GsonBuilder().setPrettyPrinting().create();
	 	String codigo = req.getParameter("codigo");
	 	int codInt = Integer.parseInt(codigo);
	 	
	 
	 	boolean retorno = true;
	 	try {
	 		retorno = pessoaControle.excluir(codInt);
	 		
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		retorno = false;
	 	}
	 	
	 	
	 	String pessoaJsonString = this.gson.toJson(retorno);
	 	
	 	PrintWriter out = resp.getWriter();
	 	resp.setContentType("application/json");
	 	resp.setCharacterEncoding("UTF-8");
	 	out.print(pessoaJsonString);
	 	out.flush();
	 }
	 
	 
	 
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			gson = new GsonBuilder().create();
			gson = new GsonBuilder().setPrettyPrinting().create();
			String nome = request.getParameter("nome");
			String idade = request.getParameter("idade");
			String Cidade = request.getParameter("cidade");	
			Pessoa pessoa = new Pessoa();
			
			int idadeInt = Integer.parseInt(idade);
			
			pessoa.setId(0);
			pessoa.setNome(nome);
			pessoa.setIdade(idadeInt);
			pessoa.setCidade(Cidade);
			PessoaDao pd = new PessoaDao();
			boolean retorno = true;
			try {
				pd.salvarPessoa(pessoa);
				retorno = true;
			} catch (Exception e) {
				retorno= false;
			}
			
			//pessoaControle = new PessoaControle();
			String pessoaJsonString = this.gson.toJson(retorno);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(pessoaJsonString);
			out.flush();
				
			}
	
	
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		gson = new GsonBuilder().create();
		gson = new GsonBuilder().setPrettyPrinting().create();
		String codigo = req.getParameter("id");
		String nome = req.getParameter("nome");
		
		int codInt = Integer.parseInt(codigo);
		
		PessoaDao pd = new PessoaDao();
		Pessoa pessoa = new Pessoa();
		pessoa = pessoaControle.getPessoaPorId(codInt);
		pessoa.setNome(nome);
		boolean retorno = true;
		try {
			retorno = pd.alterar(pessoa);
		} catch (SQLException e) {
			e.printStackTrace();
			retorno = false;
		}
		
		String pessoaJsonString = this.gson.toJson(pessoa);
		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		out.print(pessoaJsonString);
		out.flush();
	}

}

package estudos.pessoas.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import estudos.pessoas.control.PessoaControle;
import estudos.pessoas.model.Pessoa;

/**
 * Servlet implementation class PessoasServlet
 */
@WebServlet("/lista")
public class PessoasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private PessoaControle pessoaControle = new PessoaControle();
	private Gson gson;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Pessoa> pessoas = pessoaControle.getPessoas();
			String pessoaString = pessoas.toString();
			response.getWriter().write(pessoaString);
			response.getWriter().write("<br>");
			response.getWriter().write("<br>");
			
			gson = new GsonBuilder().create();
			gson = new GsonBuilder().setPrettyPrinting().create();
			
			JsonArray jarray = gson.toJsonTree(pessoas).getAsJsonArray();
			JsonObject jsonObject = new  JsonObject();
			jsonObject.add("pessoas", jarray);
			
			
			response.getWriter().write(jsonObject.toString());
			PrintWriter pw = response.getWriter();
			pw.print(" ");
			pw.print(gson.toJson(pessoas));
		} catch (Exception e) {
			e.getMessage();
		}
	}

}

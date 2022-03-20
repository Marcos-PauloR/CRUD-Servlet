package estudos.pessoas.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import estudos.pessoas.dao.PessoaDao;
import estudos.pessoas.model.Pessoa;


public class PessoaControle {
	
	private PessoaDao pd = new PessoaDao();
	
	public List<Pessoa> getPessoas() throws SQLException{
		try {
			List<Pessoa> pessoas = pd.obtemTodos();
			return pessoas;
		} catch (SQLException e) {
			System.out.print("xablau");
			return new ArrayList<Pessoa>();
		}
	}
	
	public Pessoa getPessoaPorId(int id) {
		try {
			return pd.obtemPessoaViaId(id);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public boolean excluir(int id) {
		try {
			return pd.excluir(id);
		} catch (SQLException e) {
			return false;
		}
	}
	
	public boolean salvar(Pessoa pessoa) {
		try {
			pd.salvarPessoa(pessoa);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public List<Pessoa> buscaPessoaPorNome(String nome){
		try {
			return pd.obtemPessoaPorNome(nome);
		} catch (SQLException e) {
			return null;
		}
	}
	
}

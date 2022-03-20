package estudos.pessoas.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import estudos.pessoas.model.Pessoa;

public class PessoaDao {
	
	BaseDao bd = new BaseDao();
	
	public Pessoa obtemPessoaViaId(int id) throws SQLException{
		Pessoa pessoa = new Pessoa();
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = bd.getConnection();
			pstmt = conn.prepareStatement("select * from pessoa where id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				pessoa=this.criaPessoa(rs);
			}
		} finally {
			if(pstmt !=null) {
				pstmt.close();
				conn.close();
			}
		}
		
		return pessoa;
	}
	
	public Pessoa criaPessoa(ResultSet rs) throws SQLException {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(rs.getString("nome"));
		pessoa.setId(rs.getInt("id"));
		pessoa.setIdade(rs.getInt("idade"));
		pessoa.setCidade(rs.getString("cidade"));
		return pessoa;
	}
	
	public List<Pessoa> obtemPessoaPorNome(String nome) throws SQLException{

		List<Pessoa>pessoas = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Pessoa pessoa = null;
		try {
			conn = bd.getConnection();
			pstmt = conn.prepareStatement("select * from pessoa where lower(nome) like ?");
			pstmt.setString(1, "%"+nome.toLowerCase()+"%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				pessoa = this.criaPessoa(rs);
				pessoas.add(pessoa);
			}
		} finally {
			if(pstmt != null) {
				pstmt.close();
				conn.close();
			}
		}
		
		return pessoas;
	}

	public List<Pessoa> obtemTodos() throws SQLException{

		List<Pessoa> pessoas = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Pessoa pessoa = new Pessoa();
		
		try {
			conn = bd.getConnection();
			pstmt = conn.prepareStatement("select * from pessoa");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				pessoa = this.criaPessoa(rs);
				pessoas.add(pessoa);			
			}
		} finally {
			if(pstmt != null) {
				pstmt.close();
				conn.close();
			}
		}
		
		return pessoas;
	}
	
	public void salvarPessoa(Pessoa pessoa) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			conn =bd.getConnection();
			
			if(pessoa.getId() == 0) {
				sql = "insert into pessoa (nome, idade, cidade) values (?, ?, ?)";
				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			}else {
				sql = "update pessoa set nome = ?, idade = ?, cidade = ? where id = ?";
				pstmt = conn.prepareStatement(sql);
			}
			pstmt.setString(1, "'"+pessoa.getNome()+"'");
			pstmt.setInt(2, pessoa.getIdade());
			pstmt.setString(3, "'"+pessoa.getCidade()+"'");
			if(pessoa.getId() != 0) {
				int idAux =  pstmt.executeUpdate();
				if(idAux == 0) {
					throw new SQLException("Erro ao atualizar pessoa!");
				}
				if(pessoa.getId() == 0) {
					Integer idInserir = getGeneratedId(pstmt);
					pessoa.setId(idInserir);
				}
			}
		} finally {
			if(pstmt != null) {
				pstmt.close();
				conn.close();
			}
		}
	}

	public static Integer getGeneratedId(Statement stmt) throws SQLException{
		ResultSet rs = stmt.getGeneratedKeys();
		if(rs.next()) {
			Integer id = rs.getInt(1);
			return id;
		}
		return 0;
	}
	
	public boolean alterar(Pessoa e ) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean retorno = true;
		String sql = "update pessoa set nome = ? where id = ?";
		try {
			conn = bd.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, e.getNome());
			pstmt.setInt(2, e.getId());
			
			int conta = pstmt.executeUpdate();
			retorno = conta > 0;
		}catch(SQLException eee) {
			retorno = false;
		}
		finally {
			if(pstmt != null) {
				pstmt.close();
				conn.close();
			}
		}
		return retorno;
	}

	public boolean excluir(int id) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = bd.getConnection();
			pstmt = conn.prepareStatement("delete from pessoa where id = ?");
			
			pstmt.setInt(1, id);
			int conta = pstmt.executeUpdate();
			boolean retorno = conta > 0;
			return retorno;
			
		} finally {
			if(pstmt != null) {
				pstmt.close();
				conn.close();
			}
		}
	}
}

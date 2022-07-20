package dao;

import entity.Aluno;
import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class AlunoDao {

    private Connection con;
    private Statement stmt;
    private Statement stmtNavegar;
    private ResultSet rsNavegar;

    public AlunoDao() throws ClassNotFoundException, SQLException {

        con = ConnectionFactory.getConnection();
        stmt = con.createStatement();
        stmtNavegar = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        rsNavegar = stmtNavegar.executeQuery("select * from ALUNO");

    }

    public Aluno primeiro() throws SQLException {

        if (rsNavegar.first()) {
            Aluno aluno = new Aluno();
            aluno.setCod(rsNavegar.getInt("cod"));
            aluno.setNome(rsNavegar.getString("nome"));
            aluno.setSobrenome(rsNavegar.getString("sobrenome"));
            aluno.setCodCidade(rsNavegar.getInt("codCidade"));
            return aluno;

        } else {
            return null;
        }

    }

    public Aluno anterior() throws SQLException {

        if (!rsNavegar.isFirst()) {
            rsNavegar.previous();
            Aluno aluno = new Aluno();
            aluno.setCod(rsNavegar.getInt("cod"));
            aluno.setNome(rsNavegar.getString("nome"));
            aluno.setSobrenome(rsNavegar.getString("sobrenome"));
            aluno.setCodCidade(rsNavegar.getInt("codCidade"));
            return aluno;

        } else {
            return null;
        }

    }

    public Aluno proximo() throws SQLException {

        if (!rsNavegar.isLast()) {
            rsNavegar.next();
            Aluno aluno = new Aluno();
            aluno.setCod(rsNavegar.getInt("cod"));
            aluno.setNome(rsNavegar.getString("nome"));
            aluno.setSobrenome(rsNavegar.getString("sobrenome"));
            aluno.setCodCidade(rsNavegar.getInt("codCidade"));
            return aluno;

        } else {
            return null;
        }
    }

    public Aluno ultimo() throws SQLException {

        if (rsNavegar.last()) {
            Aluno aluno = new Aluno();
            aluno.setCod(rsNavegar.getInt("cod"));
            aluno.setNome(rsNavegar.getString("nome"));
            aluno.setSobrenome(rsNavegar.getString("sobrenome"));
            aluno.setCodCidade(rsNavegar.getInt("codCidade"));
            return aluno;
        } else {
            return null;
        }

    }

    public Aluno pesquisarAluno(int cod) throws SQLException {

        ResultSet rs = stmt.executeQuery("select * from ALUNO where cod = " + cod);

        if (rs.next()) {
            Aluno aluno = new Aluno();
            aluno.setCod(rs.getInt("cod"));
            aluno.setNome(rs.getString("nome"));
            aluno.setSobrenome(rs.getString("sobrenome"));
            aluno.setCodCidade(rs.getInt("codCidade"));
            return aluno;
        } else {
            return null;
        }
    }

    public boolean excluirRegistro(int cod) throws SQLException {

        stmt.executeUpdate("delete from ALUNO where cod = " + cod);
        rsNavegar = stmtNavegar.executeQuery("select * from ALUNO");
        return true;
    }

    public boolean salvarRegistro(Aluno aluno) throws SQLException {

        int cod = aluno.getCod();
        String nome = aluno.getNome();
        String sobrenome = aluno.getSobrenome();
        int codCidade = aluno.getCodCidade();

        try {
            stmt.executeUpdate("insert into ALUNO values ( " + cod + ", '" + nome + "', '" + sobrenome + "', " + codCidade + " )");
            rsNavegar = stmtNavegar.executeQuery("select * from ALUNO");
            return true;

        } catch (SQLException e) {

           JOptionPane.showMessageDialog(null, "Falha ao inserir registro. " + e.getMessage());
            return false;
        }

    }

    public ArrayList listarAluno() throws SQLException {

        Statement stmtListar = con.createStatement();
        ResultSet rsListar = stmtListar.executeQuery("select * from ALUNO");

        ArrayList<Aluno> retorno = new ArrayList<Aluno>();

        while (rsListar.next()) {
            int cod = rsListar.getInt("cod");
            String nome = rsListar.getString("nome");
            String sobrenome = rsListar.getString("sobrenome");
            int codCidade = rsListar.getInt("codCidade");

            Aluno aluno = new Aluno();
            aluno.setCod(cod);
            aluno.setNome(nome);
            aluno.setSobrenome(sobrenome);
            aluno.setCodCidade(codCidade);

            retorno.add(aluno);
        }

        return retorno;
    }

}

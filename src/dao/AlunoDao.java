package dao;

import entity.Aluno;
import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class AlunoDao {

    private Connection con;
    private Statement stmtNavegar;
    private ResultSet rsNavegar;
    private PreparedStatement psAluno = null;
    private ResultSet rsAluno = null;

    public AlunoDao() {
        try {
            con = ConnectionFactory.getConnection();
            stmtNavegar = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rsNavegar = stmtNavegar.executeQuery("select * from ALUNO");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public Aluno primeiro() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Aluno anterior() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Aluno proximo() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Aluno ultimo() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Aluno pesquisarAluno(int cod) {
        try {
            StringBuilder sbSelect = new StringBuilder();
            sbSelect.setLength(0);
            sbSelect.append(" SELECT cod, nome, sobrenome, codCidade ");
            sbSelect.append(" FROM aluno ");
            sbSelect.append(" WHERE cod = ? ");

            psAluno = con.prepareStatement(sbSelect.toString());
            psAluno.clearParameters();
            psAluno.setInt(1, cod);
            rsAluno = psAluno.executeQuery();

            if (rsAluno.next()) {
                Aluno aluno = new Aluno();
                aluno.setCod(rsAluno.getInt("cod"));
                aluno.setNome(rsAluno.getString("nome"));
                aluno.setSobrenome(rsAluno.getString("sobrenome"));
                aluno.setCodCidade(rsAluno.getInt("codCidade"));
                return aluno;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean excluirRegistro(int cod) throws SQLException {
        try {
            StringBuilder sbSelect = new StringBuilder();

            sbSelect.setLength(0);
            sbSelect.append(" DELETE FROM aluno ");
            sbSelect.append(" WHERE cod = ? ");

            psAluno = con.prepareStatement(sbSelect.toString());
            psAluno.clearParameters();
            psAluno.setInt(1, cod);
            psAluno.execute();

            psAluno.close();

            sbSelect.setLength(0);
            sbSelect.append(" SELECT * ");
            sbSelect.append(" FROM aluno ");

            psAluno = con.prepareStatement(sbSelect.toString());
            psAluno.clearParameters();
            rsNavegar = psAluno.executeQuery();

            psAluno.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean salvarRegistro(Aluno aluno) {

        try {

            StringBuilder sbSelect = new StringBuilder();
            sbSelect.setLength(0);

            sbSelect.append(" INSERT INTO aluno  ");
            sbSelect.append(" (cod, nome, sobrenome, codCidade)  ");
            sbSelect.append(" VALUES(?,?,?,?) ");

            psAluno = con.prepareStatement(sbSelect.toString());
            psAluno.clearParameters();
            psAluno.setInt(1, aluno.getCod());
            psAluno.setString(2, aluno.getNome());
            psAluno.setString(3, aluno.getSobrenome());
            psAluno.setInt(4, aluno.getCodCidade());

            psAluno.execute();

            sbSelect.setLength(0);
            sbSelect.append(" SELECT * ");
            sbSelect.append(" FROM aluno ");

            psAluno = con.prepareStatement(sbSelect.toString());
            psAluno.clearParameters();
            rsNavegar = psAluno.executeQuery();

            return true;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Falha ao inserir registro. " + e.getMessage());
            return false;
        }

    }

    public ArrayList listarAluno() {
        try {

            StringBuilder sbSelect = new StringBuilder();

            ArrayList<Aluno> retorno = new ArrayList<Aluno>();

            sbSelect.setLength(0);
            sbSelect.append(" SELECT cod, nome, sobrenome, codCidade");
            sbSelect.append(" FROM aluno ");
            sbSelect.append(" order by cod ");

            psAluno = con.prepareStatement(sbSelect.toString());
            rsAluno = psAluno.executeQuery();

            while (rsAluno.next()) {

                Aluno aluno = new Aluno();
                aluno.setCod(rsAluno.getInt("cod"));
                aluno.setNome(rsAluno.getString("nome"));
                aluno.setSobrenome(rsAluno.getString("sobrenome"));
                aluno.setCodCidade(rsAluno.getInt("codCidade"));

                retorno.add(aluno);
            }

            return retorno;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}

package dao;

import entity.Cidade;
import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CidadeDao {

    private static Connection con = null;
    private Statement stmt = null;
    private Statement stmtNavegar = null;
    private ResultSet rsNavegar = null;
    PreparedStatement psCid = null;

    public CidadeDao() throws ClassNotFoundException, SQLException {

        con = ConnectionFactory.getConnection();
        stmt = con.createStatement();
        stmtNavegar = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        rsNavegar = stmtNavegar.executeQuery("select * from cidade");

    }

    public Cidade primeiro() throws SQLException {

        if (rsNavegar.first()) {
            Cidade Cidade = new Cidade();
            Cidade.setCod(rsNavegar.getInt("cod"));
            Cidade.setNomeCidade(rsNavegar.getString("nomeCidade"));
            Cidade.setEstado(rsNavegar.getString("estado"));
            return Cidade;

        } else {
            return null;
        }

    }

    public Cidade anterior() throws SQLException {

        if (!rsNavegar.isFirst()) {
            rsNavegar.previous();
            Cidade Cidade = new Cidade();
            Cidade.setCod(rsNavegar.getInt("cod"));
            Cidade.setNomeCidade(rsNavegar.getString("nomeCidade"));
            Cidade.setEstado(rsNavegar.getString("Estado"));
            return Cidade;

        } else {
            return null;
        }

    }

    public Cidade proximo() throws SQLException {

        if (!rsNavegar.isLast()) {
            rsNavegar.next();
            Cidade Cidade = new Cidade();
            Cidade.setCod(rsNavegar.getInt("cod"));
            Cidade.setNomeCidade(rsNavegar.getString("nomeCidade"));
            Cidade.setEstado(rsNavegar.getString("Estado"));
            return Cidade;

        } else {
            return null;
        }
    }

    public Cidade ultimo() throws SQLException {

        if (rsNavegar.last()) {
            Cidade Cidade = new Cidade();
            Cidade.setCod(rsNavegar.getInt("cod"));
            Cidade.setNomeCidade(rsNavegar.getString("nomeCidade"));
            Cidade.setEstado(rsNavegar.getString("Estado"));
            return Cidade;
        } else {
            return null;
        }

    }

    public Cidade pesquisarCidade(int cod) throws SQLException {

        StringBuilder sbSelect = new StringBuilder();
        sbSelect.setLength(0);
        sbSelect.append(" SELECT * ");
        sbSelect.append(" FROM cidade ");
        sbSelect.append(" WHERE cod = ? ");
                
        psCid = con.prepareStatement(sbSelect.toString());
        psCid.clearParameters();
        psCid.setInt(1, cod);
        ResultSet rs = psCid.executeQuery();

        if (rs.next()) {
            Cidade Cidade = new Cidade();
            Cidade.setCod(rs.getInt("cod"));
            Cidade.setNomeCidade(rs.getString("nomeCidade"));
            Cidade.setEstado(rs.getString("Estado"));
            return Cidade;
        } else {
            return null;
        }
    }

    public boolean excluirRegistro(int cod) throws SQLException {

        stmt.executeUpdate("delete from cidade where cod = " + cod);
        rsNavegar = stmtNavegar.executeQuery("select * from cidade");
        return true;
    }

    public boolean salvarRegistro(Cidade aluno) throws SQLException {

        int cod = aluno.getCod();
        String nomeCidade = aluno.getNomeCidade();
        String estado = aluno.getEstado();

        try {
            stmt.executeUpdate("insert into cidade values ( " + cod + ", '" + nomeCidade + "', '" + estado + "')");
            rsNavegar = stmtNavegar.executeQuery("select * from cidade");

            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao inserir registro. " + e.getMessage());
            return false;
        }

    }

    public List<Cidade> montarListaCidade() throws SQLException {

        ArrayList<Cidade> lista = new ArrayList<Cidade>();

        ResultSet rsCidade = stmt.executeQuery("select * from cidade");

        while (rsCidade.next()) {
            Cidade cidade = new Cidade();
            cidade.setCod(rsCidade.getInt("cod"));
            cidade.setNomeCidade(rsCidade.getString("nomeCidade"));
            cidade.setEstado(rsCidade.getString("estado"));
            lista.add(cidade);
        }

        return lista;

    }

    public int getCodCidadeByNome(String nomeCidade) throws SQLException {

        ResultSet rsCidade = stmt.executeQuery("select * from cidade where nomeCidade = '" + nomeCidade + "'");

        if (rsCidade.next()) {
            return rsCidade.getInt("cod");
        } else {
            return -1;
        }

    }

    public String getNomeCidadeByCod(int cod) throws SQLException {

        ResultSet rsCidade = stmt.executeQuery("select * from cidade where cod = " + cod);

        if (rsCidade.next()) {
            return rsCidade.getString("nomeCidade");
        } else {
            return "";
        }

    }

    public ArrayList ListarCidade() throws SQLException {

        Statement stmtListar = con.createStatement();
        ResultSet rsListar = stmtListar.executeQuery("select * from cidade");

        ArrayList<Cidade> retorno = new ArrayList<Cidade>();

        while (rsListar.next()) {
            int cod = rsListar.getInt("cod");
            String nome = rsListar.getString("nomeCidade");
            String estado = rsListar.getString("estado");

            Cidade cidade = new Cidade();
            cidade.setCod(cod);
            cidade.setNomeCidade(nome);
            cidade.setEstado(estado);

            retorno.add(cidade);
        }

        return retorno;
    }
}

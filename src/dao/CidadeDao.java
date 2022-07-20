package dao;

import entity.Cidade;
import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class CidadeDao {

    private static Connection con = null;
    private Statement stmtNavegar = null;
    private ResultSet rsNavegar = null;
    private PreparedStatement psCid = null;
    private ResultSet rsCid = null;

    public CidadeDao() {
        try {
            con = ConnectionFactory.getConnection();
            stmtNavegar = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rsNavegar = stmtNavegar.executeQuery("select * from cidade");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public Cidade primeiro() {
        try {
            if (rsNavegar.first()) {
                Cidade Cidade = new Cidade();
                Cidade.setCod(rsNavegar.getInt("cod"));
                Cidade.setNomeCidade(rsNavegar.getString("nomeCidade"));
                Cidade.setEstado(rsNavegar.getString("estado"));
                return Cidade;

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Cidade anterior() {
        try {
            if (!rsNavegar.isFirst()) {
                rsNavegar.previous();
                Cidade Cidade = new Cidade();
                Cidade.setCod(rsNavegar.getInt("cod"));
                Cidade.setNomeCidade(rsNavegar.getString("nomeCidade"));
                Cidade.setEstado(rsNavegar.getString("estado"));
                return Cidade;

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Cidade proximo() {
        try {
            if (!rsNavegar.isLast()) {
                rsNavegar.next();
                Cidade Cidade = new Cidade();
                Cidade.setCod(rsNavegar.getInt("cod"));
                Cidade.setNomeCidade(rsNavegar.getString("nomeCidade"));
                Cidade.setEstado(rsNavegar.getString("estado"));
                return Cidade;

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Cidade ultimo() {
        try {
            if (rsNavegar.last()) {
                Cidade Cidade = new Cidade();
                Cidade.setCod(rsNavegar.getInt("cod"));
                Cidade.setNomeCidade(rsNavegar.getString("nomeCidade"));
                Cidade.setEstado(rsNavegar.getString("estado"));
                return Cidade;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Cidade pesquisarCidade(int cod) {
        try {
            StringBuilder sbSelect = new StringBuilder();
            sbSelect.setLength(0);
            sbSelect.append(" SELECT cod, nomeCidade, estado ");
            sbSelect.append(" FROM cidade ");
            sbSelect.append(" WHERE cod = ? ");

            psCid = con.prepareStatement(sbSelect.toString());
            psCid.clearParameters();
            psCid.setInt(1, cod);
            rsCid = psCid.executeQuery();

            if (rsCid.next()) {
                Cidade Cidade = new Cidade();
                Cidade.setCod(rsCid.getInt("cod"));
                Cidade.setNomeCidade(rsCid.getString("nomeCidade"));
                Cidade.setEstado(rsCid.getString("estado"));
                return Cidade;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean excluirRegistro(int cod) {
        try {
            StringBuilder sbSelect = new StringBuilder();

            sbSelect.setLength(0);
            sbSelect.append(" DELETE FROM cidade ");
            sbSelect.append(" WHERE cod = ? ");

            psCid = con.prepareStatement(sbSelect.toString());
            psCid.clearParameters();
            psCid.setInt(1, cod);
            psCid.execute();

            psCid.close();

            sbSelect.setLength(0);
            sbSelect.append(" SELECT * ");
            sbSelect.append(" FROM cidade ");

            psCid = con.prepareStatement(sbSelect.toString());
            psCid.clearParameters();
            rsNavegar = psCid.executeQuery();
            
            psCid.close();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    public boolean salvarRegistro(Cidade cidade)  {

        try {
            StringBuilder sbSelect = new StringBuilder();
            sbSelect.setLength(0);

            sbSelect.append(" INSERT INTO cidade  ");
            sbSelect.append(" (cod, nomeCidade, estado)  ");
            sbSelect.append(" VALUES(?,?,?) ");

            psCid = con.prepareStatement(sbSelect.toString());
            psCid.clearParameters();
            psCid.setInt(1, cidade.getCod());
            psCid.setString(2, cidade.getNomeCidade());
            psCid.setString(3, cidade.getEstado());

            psCid.execute();

            sbSelect.setLength(0);
            sbSelect.append(" SELECT * ");
            sbSelect.append(" FROM cidade ");

            psCid = con.prepareStatement(sbSelect.toString());
            psCid.clearParameters();
            rsNavegar = psCid.executeQuery();

            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao inserir registro. " + e.getMessage());
            return false;
        }

    }

    public int getCodCidadeByNome(String nomeCidade) {
        try {
            StringBuilder sbSelect = new StringBuilder();
            sbSelect.setLength(0);
            sbSelect.append(" SELECT cod ");
            sbSelect.append(" FROM cidade ");
            sbSelect.append(" WHERE nomeCidade = ? ");

            psCid = con.prepareStatement(sbSelect.toString());
            psCid.clearParameters();
            psCid.setString(1, nomeCidade);
            rsCid = psCid.executeQuery();

            if (rsCid.next()) {
                return rsCid.getInt("cod");
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    public String getNomeCidadeByCod(int cod) {
        try {
            StringBuilder sbSelect = new StringBuilder();
            sbSelect.setLength(0);
            sbSelect.append(" SELECT nomeCidade ");
            sbSelect.append(" FROM cidade ");
            sbSelect.append(" WHERE cod = ? ");

            psCid = con.prepareStatement(sbSelect.toString());
            psCid.clearParameters();
            psCid.setInt(1, cod);
            rsCid = psCid.executeQuery();

            if (rsCid.next()) {
                return rsCid.getString("nomeCidade");
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public ArrayList ListarCidade() {

        try {
            ArrayList<Cidade> retorno = new ArrayList<Cidade>();
            StringBuilder sbSelect = new StringBuilder();

            sbSelect.setLength(0);
            sbSelect.append(" SELECT cod, nomeCidade, estado ");
            sbSelect.append(" FROM cidade ");
            sbSelect.append(" order by cod ");

            psCid = con.prepareStatement(sbSelect.toString());
            rsCid = psCid.executeQuery();

            while (rsCid.next()) {

                Cidade cidade = new Cidade();
                cidade.setCod(rsCid.getInt("cod"));
                cidade.setNomeCidade(rsCid.getString("nomeCidade"));
                cidade.setEstado(rsCid.getString("estado"));

                retorno.add(cidade);
            }
            rsCid.close();
            psCid.close();

            return retorno;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}


package entity;

import java.util.Objects;


public class Cidade {
    
    private int cod;
    private String nomeCidade;
    private String estado;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.cod;
        hash = 97 * hash + Objects.hashCode(this.nomeCidade);
        hash = 97 * hash + Objects.hashCode(this.estado);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cidade other = (Cidade) obj;
        if (this.cod != other.cod) {
            return false;
        }
        if (!Objects.equals(this.nomeCidade, other.nomeCidade)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        return true;
    }
    
    
}

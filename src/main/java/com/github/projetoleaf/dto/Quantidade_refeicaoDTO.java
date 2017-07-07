package com.github.projetoleaf.dto;

import java.util.Objects;

public class Quantidade_refeicaoDTO {

    private Integer id;

    private Integer subsidiada;
    
    private Integer custo;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSubsidiada() {
		return subsidiada;
	}

	public void setSubsidiada(Integer subsidiada) {
		this.subsidiada = subsidiada;
	}

	public Integer getCusto() {
		return custo;
	}

	public void setCusto(Integer custo) {
		this.custo = custo;
	}

	@Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Quantidade_refeicaoDTO other = (Quantidade_refeicaoDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}

package com.github.projetoleaf.dto;

import java.util.Objects;

public class ReservaItemDTO {

    private Integer id;

    private ReservaDTO reserva;
    
    private CardapioDTO cardapio;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ReservaDTO getReserva() {
		return reserva;
	}

	public void setReserva(ReservaDTO reserva) {
		this.reserva = reserva;
	}

	public CardapioDTO getCardapio() {
		return cardapio;
	}

	public void setCardapio(CardapioDTO cardapio) {
		this.cardapio = cardapio;
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
        final ReservaItemDTO other = (ReservaItemDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}

package knight.nameless.starter.models.hmi;

import lombok.Data;

@Data
public class RangoTurno {

    private boolean activa;
    private boolean inactiva;

    public RangoTurno(boolean dentroRango) {
        this.activa = dentroRango;
        this.inactiva = !dentroRango;
    }
}

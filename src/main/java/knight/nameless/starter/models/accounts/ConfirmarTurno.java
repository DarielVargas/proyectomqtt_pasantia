package knight.nameless.starter.models.accounts;

import lombok.Data;

@Data
public class ConfirmarTurno {

    private boolean correcto;
    private boolean incorrecto;
    private String pin;
}

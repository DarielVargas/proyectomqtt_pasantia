package knight.nameless.starter.models.accounts;

import lombok.Data;

@Data
public class CurrentAccount {

    private String usuario;

    public CurrentAccount() {
        this.usuario = "";
    }

    public CurrentAccount(String usuario) {
        this.usuario = usuario;
    }
}

package knight.nameless.starter.models.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponse {

    @JsonIgnore
    private String firstName;
    @JsonIgnore
    private String lastName;
    private boolean correcto;
    private boolean incorrecto;

    @JsonProperty(value = "nombre", access = JsonProperty.Access.READ_ONLY)
    public String getName() {
        if (firstName != null)
            return (firstName + " " + lastName);
        return "";
    }

}

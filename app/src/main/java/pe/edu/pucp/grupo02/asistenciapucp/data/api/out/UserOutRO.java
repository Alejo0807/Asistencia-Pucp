package pe.edu.pucp.grupo02.asistenciapucp.data.api.out;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import pe.edu.pucp.grupo02.asistenciapucp.data.api.base.BaseOutRO;

@JsonRootName("userOutRO")
public class UserOutRO extends BaseOutRO {

    private int userId;
    private String fullName;
    private String email;
    private String servidorID;
    private String keyAutorizacion;
    private String usuarioAutorizacion;
    private String rolUsuario;

    @JsonCreator
    public UserOutRO(@JsonProperty("errorCode") int errorCode,
                     @JsonProperty("message") String message,
                     @JsonProperty("userId") int userId,
                     @JsonProperty("fullName") String fullName,
                     @JsonProperty("email") String email,
                     @JsonProperty("servidorID") String servidorID,
                     @JsonProperty("keyAutorizacion") String keyAutorizacion,
                     @JsonProperty("usuarioAutorizacion") String usuarioAutorizacion,
                     @JsonProperty("rolUsuario") String rolUsuario
    ) {
        super(errorCode, message);
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.servidorID = servidorID;
        this.keyAutorizacion = keyAutorizacion;
        this.usuarioAutorizacion = usuarioAutorizacion;
        this.rolUsuario = rolUsuario;
    }

    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getServidorID() { return servidorID; }

    public String getKeyAutorizacion() { return keyAutorizacion; }

    public String getUsuarioAutorizacion() { return usuarioAutorizacion; }

    public String getRolUsuario() { return rolUsuario; }
}
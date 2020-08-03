package pe.edu.pucp.grupo02.asistenciapucp.data.db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "USER")
public class User {

    @PrimaryKey
    @ColumnInfo(name = "USER_ID")
    private int userId;

    @ColumnInfo(name = "FULL_NAME")
    private String fullName;

    @ColumnInfo(name = "EMAIL")
    private String email;

    @ColumnInfo(name = "USERNAME")
    private String username;

    @ColumnInfo(name = "HASH")
    private String hash;

    @ColumnInfo(name = "SERVIDOR_ID")
    private String servidorID;

    @ColumnInfo(name = "KEY_AUTORIZACION")
    private String keyAutorizacion;

    @ColumnInfo(name = "USUARIO_AUTORIZACION")
    private String usuarioAutorizacion;

    @ColumnInfo(name = "ROL_USUARIO")
    private String rolUsuario;

    public User(int userId, String fullName, String email, String username, String hash, String servidorID, String keyAutorizacion, String usuarioAutorizacion, String rolUsuario) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.hash = hash;
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

    public String getUsername() {
        return username;
    }

    public String getHash() {
        return hash;
    }

    public String getServidorID() {
        return servidorID;
    }

    public String getKeyAutorizacion() {
        return keyAutorizacion;
    }

    public String getUsuarioAutorizacion() {
        return usuarioAutorizacion;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }
}
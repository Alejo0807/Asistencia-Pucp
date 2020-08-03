package pe.edu.pucp.grupo02.asistenciapucp.features.login;

import pe.edu.pucp.grupo02.asistenciapucp.features.base.IPresenter;

public interface ILoginPresenter extends IPresenter {

    boolean verifyLoginData(String username, String password);

    void loginRest(String username, String password);

    void loginOffline(String username, String password, String servidorId, String keyAutorizacion, String usuarioAutorizacion, String rolUsuario);
}
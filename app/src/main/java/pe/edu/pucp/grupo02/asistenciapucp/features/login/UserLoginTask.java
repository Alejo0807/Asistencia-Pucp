package pe.edu.pucp.grupo02.asistenciapucp.features.login;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import de.rtner.security.auth.spi.SimplePBKDF2;
import pe.edu.pucp.grupo02.asistenciapucp.R;
import pe.edu.pucp.grupo02.asistenciapucp.data.db.AppDatabase;
import pe.edu.pucp.grupo02.asistenciapucp.data.db.entities.User;
import pe.edu.pucp.grupo02.asistenciapucp.utils.Utilities;

public class UserLoginTask extends AsyncTask<Void, Void, User> {

    private final static String TAG = "MFL_LOGIN_LOGINTASK";
    private WeakReference<ILoginView> view;
    private String username;
    private String password;
    private String servidorID;
    private String keyAutorization;
    private String ususarioAutorization;
    private String rolUsuario;

    protected UserLoginTask(ILoginView view, String username, String password, String servidorID, String keyAutorization, String ususarioAutorization, String rolUsuario ) {
        this.view = new WeakReference<>(view);
        this.username = username;
        this.password = password;
        this.servidorID = servidorID;
        this.keyAutorization = keyAutorization;
        this.ususarioAutorization = ususarioAutorization;
        this.rolUsuario = rolUsuario;
    }

    @Override
    protected User doInBackground(Void... voids) {
        // Verificar que la vista todavía está disponible
        ILoginView view = this.view.get();
        if (view == null) return null;
        // Iniciar la base de datos, si es que aún no se hizo
        AppDatabase database = AppDatabase.getInstance(view.getContext());
        if (database == null) {
            Log.d(TAG, "La base de datos no se inicializó, ¿quizás terminó el Activity?");
            return null;
        }
        // Buscar el usuario con el nombre de usuario proporcionado
        User user = database.userDao().findByUsername(username);
        if (user == null) return null;
        // Evaluar la contraseña
        boolean isValid = new SimplePBKDF2().verifyKeyFormatted(user.getHash(), password);
        return isValid ? user : null;
    }

    @Override
    protected void onPostExecute(User user) {
        // Verificar que la vista todavía está disponible
        ILoginView view = this.view.get();
        if (view == null) return;
        // Verificar si el usuario fue encontrado
        if (user != null) {
            view.goToHomePage(user.getFullName(), user.getEmail());
        } else {
            String message = Utilities.formatString(view.getContext(),
                    R.string.login_dlg_error_msg_not_found);
            view.showErrorDialog(message);
        }
    }
}
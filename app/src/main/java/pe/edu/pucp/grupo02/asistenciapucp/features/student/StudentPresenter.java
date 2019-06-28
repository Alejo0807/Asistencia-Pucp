package pe.edu.pucp.grupo02.asistenciapucp.features.student;

import android.content.Context;
import android.support.v4.util.Pair;

import java.net.UnknownHostException;

import pe.edu.pucp.grupo02.asistenciapucp.R;
import pe.edu.pucp.grupo02.asistenciapucp.data.api.ApiAdapter;
import pe.edu.pucp.grupo02.asistenciapucp.data.api.in.LoginInRO;
import pe.edu.pucp.grupo02.asistenciapucp.data.api.out.StudentMessagesRO;
import pe.edu.pucp.grupo02.asistenciapucp.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentPresenter implements IStudentPresenter{

    private final static String TAG = "MFL_STUDENT_PRESENTER";
    private IStudentView view;

    public StudentPresenter(IStudentView view) {
        this.view = view;
    }

    public boolean verifyMessagesData() {
        return true;
    }

    public void anunciosRest() {
        LoginInRO loginInRO = new LoginInRO(ApiAdapter.APPLICATION_NAME, "o", "o");
        Call<StudentMessagesRO> call = ApiAdapter.getInstance().announcements(loginInRO);
        call.enqueue(new Callback<StudentMessagesRO>() {
            @Override
            public void onResponse(Call<StudentMessagesRO> call, Response<StudentMessagesRO> response) {
                processUserResponse(response);
            }

            @Override
            public void onFailure(Call<StudentMessagesRO> call, Throwable t) {
                if (t instanceof UnknownHostException) {
                    // No se encontró la URL, preguntar si se desea iniciar sesión
                    // sin conexión
                    view.askForMessagesOffline();
                } else {
                    // Mostrar mensaje de error en el logcat y en un cuadro de diálogo
                    t.printStackTrace();
                    view.showErrorDialog(t.getMessage());
                }
            }
        });
    }

    private void processUserResponse(Response<StudentMessagesRO> response) {
        // Verificar respuesta del servidor REST
        Pair<StudentMessagesRO, String> result = validateResponse(response);
        if (result.first == null) {
            // Mostrar mensaje de error
            view.showErrorDialog(result.second);
        } else {
            // Obtener el objeto JSON
            StudentMessagesRO studentMessagesRO = result.first;
            // Ir a la pantalla de mensajes
            view.gotoStudentMessages(studentMessagesRO.getMessage1(), studentMessagesRO.getMessage2(), studentMessagesRO.getMessage3());
        }
    }

    private Pair<StudentMessagesRO, String> validateResponse(Response<StudentMessagesRO> response) {
        Context context = view.getContext();
        // Verificar que la respuesta es satisfactoria
        if (!response.isSuccessful()) {
            String message = Utilities.formatString(context, R.string.api_dlg_error_msg_http, response.code());
            return new Pair<>(null, message);
        }
        // Verificar el contenido de la respuesta en JSON
        StudentMessagesRO studentMessagesRO = response.body();
        if (studentMessagesRO == null) {
            String message = Utilities.formatString(context, R.string.api_dlg_error_msg_empty);
            return new Pair<>(null, message);
        }
        // Verificar que la respuesta no indique un error
        int errorCode = studentMessagesRO.getErrorCode();
        String message = studentMessagesRO.getMessage();
        if (errorCode == 0) {
            return new Pair<>(studentMessagesRO, message); // Respuesta sin errores
        }
        // Verificar que el mensaje de error no está vacío
        if (message == null || message.isEmpty()) {
            message = Utilities.formatString(context, R.string.api_dlg_error_msg_rest, errorCode);
        }
        return new Pair<>(null, message);
    }


    @Override
    public void messagesOffline() {

    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
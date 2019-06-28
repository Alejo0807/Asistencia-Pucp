package pe.edu.pucp.grupo02.asistenciapucp.features.teacher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.View;

import java.net.UnknownHostException;

import pe.edu.pucp.grupo02.asistenciapucp.R;
import pe.edu.pucp.grupo02.asistenciapucp.data.api.ApiAdapter;
import pe.edu.pucp.grupo02.asistenciapucp.data.api.in.TeacherTokenInRO;
import pe.edu.pucp.grupo02.asistenciapucp.data.api.out.TeacherTokenOutRO;
import pe.edu.pucp.grupo02.asistenciapucp.data.api.out.UserOutRO;
import pe.edu.pucp.grupo02.asistenciapucp.features.login.UserSaveTask;
import pe.edu.pucp.grupo02.asistenciapucp.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherPresenter implements ITeacherPresenter {

    private final static String TAG = "AP_TEACHER_PRESENTER";
    private ITeacherView view;

    public TeacherPresenter(ITeacherView view){this.view = view; }

    public boolean verifyAttendaceData(){
        return true;
    }

    public boolean verifyTokenData(String s){
        return true;
    }

    public boolean verifyMessagesData(){
        return true;
    }

    public void tokenRest(final String date) {
        TeacherTokenInRO teacherTokenInRO = new TeacherTokenInRO(ApiAdapter.APPLICATION_NAME, date);
        Call<TeacherTokenOutRO> call = ApiAdapter.getInstance().token(teacherTokenInRO);
        call.enqueue(new Callback<TeacherTokenOutRO>() {
            @Override
            public void onResponse(@NonNull Call<TeacherTokenOutRO> call, @NonNull Response<TeacherTokenOutRO> response) {
                processTeacherTokenResponse(response, date);
            }

            @Override
            public void onFailure( @NonNull Call<TeacherTokenOutRO> call, @NonNull  Throwable t) {
                if (t instanceof UnknownHostException) {
                    // No se encontró la URL, preguntar si se desea iniciar sesión
                    // sin conexión
                    //view.askForLoginOffline();
                } else {
                    // Mostrar mensaje de error en el logcat y en un cuadro de diálogo
                    t.printStackTrace();
                    view.showErrorDialog(t.getMessage());
                }
            }
        });
    }

    private void processTeacherTokenResponse(Response<TeacherTokenOutRO> response, String date) {
            // Verificar respuesta del servidor REST
        Pair<TeacherTokenOutRO, String> result = validateResponse(response);
        if (result.first == null) {
            // Mostrar mensaje de error
            view.showErrorDialog(result.second);
        } else {
            // Obtener el objeto JSON
            TeacherTokenOutRO teacherTokenOutRO = result.first;
            // Guardar los datos del usuario en la base de datos
            //new UserSaveTask(view, date, teacherTokenOutRO).execute();
            // Ir a la pantalla de bienvenida
            view.MoverAGenerarToken(teacherTokenOutRO.getCourseName(), teacherTokenOutRO.getCourseSchedule(),
                                    teacherTokenOutRO.getCourseTime());
        }
    }

    private Pair<TeacherTokenOutRO, String> validateResponse(Response<TeacherTokenOutRO> response) {
        Context context = view.getContext();
            // Verificar que la respuesta es satisfactoria
        if (!response.isSuccessful()) {
            String message = Utilities.formatString(context,
                    R.string.api_dlg_error_msg_http, response.code());
            return new Pair<>(null, message);
        }
            // Verificar el contenido de la respuesta en JSON
        TeacherTokenOutRO teacherTokenOutRO = response.body();
        if (teacherTokenOutRO == null) {
            String message = Utilities.formatString(context,
                    R.string.api_dlg_error_msg_empty);
            return new Pair<>(null, message);
        }
            // Verificar que la respuesta no indique un error
        int errorCode = teacherTokenOutRO.getErrorCode();
        String message = teacherTokenOutRO.getMessage();
        if (errorCode == 0) {
            return new Pair<>(teacherTokenOutRO, message); // Respuesta sin errores
        }
            // Verificar que el mensaje de error no está vacío
        if (message == null || message.isEmpty()) {
            message = Utilities.formatString(context, R.string.api_dlg_error_msg_rest,
                    errorCode);
        }
        return new Pair<>(null, message);
    }


    public void messagesRest() {
        view.MoverATeacherAMessages();
    }


    public void attendanceRest(){
        view.MoverATeacherAttendance();
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}

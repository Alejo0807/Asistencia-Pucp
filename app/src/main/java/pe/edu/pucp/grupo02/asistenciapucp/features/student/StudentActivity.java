package pe.edu.pucp.grupo02.asistenciapucp.features.student;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import pe.edu.pucp.grupo02.asistenciapucp.R;
import pe.edu.pucp.grupo02.asistenciapucp.features.principal.PrincipalActivity;
import pe.edu.pucp.grupo02.asistenciapucp.features.student.attendance.StudentAttendanceActivity;
import pe.edu.pucp.grupo02.asistenciapucp.features.student.messages.StudentMessagesActivity;
import pe.edu.pucp.grupo02.asistenciapucp.features.student.token.StudentTokenActivity;

public class StudentActivity extends AppCompatActivity implements IStudentView {

    //TAG = AsistenciaPucp_STUDENT ....
    private final static String TAG = "AP_STUDENT_VIEW";

    public final static String STUDENT_TOKEN_EXTRA_COURSENAME = "STUDENT_EXTRA_COURSENAME";
    public final static String STUDENT_TOKEN_EXTRA_COURSESCH = "STUDENT_EXTRA_COURSESCH";
    public final static String STUDENT_TOKEN_EXTRA_COURSETIME = "STUDENT_EXTRA_COURSETIME";

    public final static String STUDENT_MSJE1 = "STUDENT_MSJE1";
    public final static String STUDENT_MSJE2 = "STUDENT_MSJE2";
    public final static String STUDENT_MSJE3 = "STUDENT_MSJE3";

    public final static String STUDENT_USERID = "STUDENT_USERID";
    public final static String STUDENT_PORC1 = "STUDENT_PORC1";
    public final static String STUDENT_PORC2 = "STUDENT_PORC2";
    public final static String STUDENT_PORC3 = "STUDENT_PORC3";

    private IStudentPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        mPresenter = new StudentPresenter(this);
    }

    public void retroceder(View view) {
        Intent anterior = new Intent(this, PrincipalActivity.class);
        startActivity(anterior);
    }

    public void token(View view) {
        // Validar datos del usuario
        if (mPresenter.verifyTokenData()) {
            // Mostrar mensajes
            mPresenter.tokenRest();
        }
    }

    public void attendance(View view) {
        // Validar datos del usuario
        if (mPresenter.verifyAttendanceData()) {
            // Mostrar mensajes
            mPresenter.asistenciaRest();
        }
    }

    public void messages(View view) {
        // Validar datos del usuario
        if (mPresenter.verifyMessagesData()) {
            // Mostrar mensajes
            mPresenter.anunciosRest();
        }
    }

    public void gotoIngresarToken(String name, String sch, String time){
        Intent siguiente = new Intent(this, StudentTokenActivity.class);
        siguiente.putExtra(STUDENT_TOKEN_EXTRA_COURSENAME, name);
        siguiente.putExtra(STUDENT_TOKEN_EXTRA_COURSESCH, sch);
        siguiente.putExtra(STUDENT_TOKEN_EXTRA_COURSETIME, time);
        startActivity(siguiente);
    }

    public void gotoStudentAttendance(int userId, String porce1, String porce2, String porce3){
        // Iniciar la actividad de porcentaje de asistencias
        Intent siguiente = new Intent(this, StudentAttendanceActivity.class);
        siguiente.putExtra(STUDENT_USERID, userId);
        siguiente.putExtra(STUDENT_PORC1, porce1);
        siguiente.putExtra(STUDENT_PORC2, porce2);
        siguiente.putExtra(STUDENT_PORC3, porce3);
        startActivity(siguiente);
    }

    public void gotoStudentMessages(String msje1, String msje2, String msje3){
        // Iniciar la actividad de mensajes de estudiantes
        Intent siguiente = new Intent(this, StudentMessagesActivity.class);
        siguiente.putExtra(STUDENT_MSJE1, msje1);
        siguiente.putExtra(STUDENT_MSJE2, msje2);
        siguiente.putExtra(STUDENT_MSJE3, msje3);
        startActivity(siguiente);
    }


    public void askForMessagesOffline() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.login_dlg_offline_title)
                .setMessage(R.string.login_dlg_offline_msg)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Obtener mensajes sin conexión
                                mPresenter.messagesOffline();
                            }
                        })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    public void askForAttendanceOffline() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.teacher_messages_offline1)
                .setMessage(R.string.teacher_messages_offline2)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Obtener cursos y horarios sin conexión
                                //Bundle extras = getIntent().getExtras();
                                //int msjeId = extras.getInt(TeacherMessagesActivity.TEACHER_MSJEID);

                                mPresenter.attendanceOffline(1);
                            }
                        })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }


    public void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.login_dlg_error_title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }


    @Override
    public Context getContext() { return this; }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
}

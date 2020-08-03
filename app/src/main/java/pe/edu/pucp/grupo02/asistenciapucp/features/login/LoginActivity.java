package pe.edu.pucp.grupo02.asistenciapucp.features.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.Key;

import pe.edu.pucp.grupo02.asistenciapucp.R;
import pe.edu.pucp.grupo02.asistenciapucp.features.principal.PrincipalActivity;
import pe.edu.pucp.grupo02.asistenciapucp.features.teacher.TeacherActivity;
import pe.edu.pucp.grupo02.asistenciapucp.utils.Utilities;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    private final static String TAG = "MFL_LOGIN_VIEW";
    public final static String LOGIN_EXTRA_FULLNAME = "LOGIN_EXTRA_FULLNAME";
    public final static String LOGIN_EXTRA_EMAIL = "LOGIN_EXTRA_EMAIL";
    public final static String LOGIN_EXTRA_SERVIDORID = "LOGIN_EXTRA_SERVIDORID";
    public final static String LOGIN_EXTRA_KEY_AUTORIZACION = "LOGIN_EXTRA_KEY_AUTORIZACION";
    public final static String LOGIN_EXTRA_USUARIO_AUTORIZACION = "LOGIN_EXTRA_USUARIO_AUTORIZACION";
    public final static String LOGIN_EXTRA_ROL_USUARIO = "LOGIN_EXTRA_ROL_USUARIO";

    private EditText mUsername;
    private EditText mPassword;
    private Button mSubmit;
    private ILoginPresenter mPresenter;

    private TextView ServidorID;
    private TextView KeyAutorizacion;
    private TextView UsuarioAutorizacion;
    private TextView RolUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = findViewById(R.id.login_ipt_username);
        mPassword = findViewById(R.id.login_ipt_password);
        mSubmit = findViewById(R.id.login_btn_submit);
        ServidorID = super.findViewById(R.id.login_txt_servidorID);
        KeyAutorizacion = super.findViewById(R.id.login_txt_keyAutorization);
        UsuarioAutorizacion = super.findViewById(R.id.login_txt_usuarioAutorization);
        RolUsuario = super.findViewById(R.id.login_txt_rolUsuario);

        mPresenter = new LoginPresenter(this);

        //Trae los datos del REST
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Obtener los Id y porcentajes enviados por el Intent
            String servidorID = extras.getString(LoginActivity.LOGIN_EXTRA_SERVIDORID);
            String keyAutoriazation = extras.getString(LoginActivity.LOGIN_EXTRA_KEY_AUTORIZACION);
            String usuarioAutorization = extras.getString(LoginActivity.LOGIN_EXTRA_USUARIO_AUTORIZACION);
            String rolUsuario = extras.getString(LoginActivity.LOGIN_EXTRA_ROL_USUARIO);

            ServidorID.setText(servidorID);
            KeyAutorizacion.setText(keyAutoriazation);
            UsuarioAutorizacion.setText(usuarioAutorization);
            RolUsuario.setText(rolUsuario);
        }

        initButtons();
    }

    private void initButtons() {
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
    }

    public void login(View v) {
        // Esconder el teclado
        Utilities.hideKeyboard(this);
        // Obtener datos de usuario
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        // Validar datos del usuario
        if (mPresenter.verifyLoginData(username, password)) {
            // Iniciar sesión y notificar al usuario que se está iniciando sesión
            mPresenter.loginRest(username, password);
            Utilities.showMessage(this, R.string.login_msg_loading);
        }
    }

    public void askForLoginOffline() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.login_dlg_offline_title)
                .setMessage(R.string.login_dlg_offline_msg)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Obtener datos de usuario
                                String username = mUsername.getText().toString();
                                String password = mPassword.getText().toString();
                                String servidorID = ServidorID.getText().toString();
                                String keyAutorization = KeyAutorizacion.getText().toString();
                                String usuarioAutorization = UsuarioAutorizacion.getText().toString();
                                String rolUsuario = RolUsuario.getText().toString();
                                // Iniciar sesión sin conexión
                                mPresenter.loginOffline(username, password, servidorID, keyAutorization, usuarioAutorization, rolUsuario);
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

    public void goToHomePage(String fullName, String email) {
        // Iniciar la actividad principal
        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.putExtra(LOGIN_EXTRA_FULLNAME, fullName);
        intent.putExtra(LOGIN_EXTRA_EMAIL, email);
        startActivity(intent);
        // Cerrar esta actividad
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
}
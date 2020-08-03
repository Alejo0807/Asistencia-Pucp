package pe.edu.pucp.grupo02.asistenciapucp.features.teacher.attendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import pe.edu.pucp.grupo02.asistenciapucp.R;
import pe.edu.pucp.grupo02.asistenciapucp.features.teacher.TeacherActivity;

public class TeacherAttendanceActivity extends AppCompatActivity {

    private TextView percent1;
    private TextView percent2;
    private TextView percent3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance);

        percent1 = super.findViewById(R.id.principal_lbl_asis1);
        percent2 = super.findViewById(R.id.principal_lbl_asis2);
        percent3 = super.findViewById(R.id.principal_lbl_asis3);
        showUserDetails();
    }

    private void showUserDetails() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Obtener los Id y porcentajes enviados por el Intent
            int attId = extras.getInt(TeacherActivity.TEACHER_ATTENDANCE_EXTRA_ATTID);
            String porc1 = extras.getString(TeacherActivity.TEACHER_ATTENDANCE_EXTRA_P1);
            String porc2 = extras.getString(TeacherActivity.TEACHER_ATTENDANCE_EXTRA_P2);
            String porc3 = extras.getString(TeacherActivity.TEACHER_ATTENDANCE_EXTRA_P3);
            // Colocar los valores en el TextView
            percent1.setText(porc1);
            percent2.setText(porc2);
            percent3.setText(porc3);
        }
    }

    public void retroceder(View view){
        Intent anterior = new Intent(this, TeacherActivity.class);
        startActivity(anterior);
    }
}

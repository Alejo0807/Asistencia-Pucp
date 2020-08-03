package pe.edu.pucp.grupo02.asistenciapucp.features.student.attendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pe.edu.pucp.grupo02.asistenciapucp.R;
import pe.edu.pucp.grupo02.asistenciapucp.features.student.StudentActivity;

public class StudentAttendanceActivity extends AppCompatActivity {

    private TextView percent1;
    private TextView percent2;
    private TextView percent3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);

        percent1 = super.findViewById(R.id.principal_lbl_asis1);
        percent2 = super.findViewById(R.id.principal_lbl_asis2);
        percent3 = super.findViewById(R.id.principal_lbl_asis3);
        showUserDetails();
    }

    private void showUserDetails() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Obtener los porcentajes enviados por el Intent
            String porc1 = extras.getString(StudentActivity.STUDENT_PORC1);
            String porc2 = extras.getString(StudentActivity.STUDENT_PORC2);
            String porc3 = extras.getString(StudentActivity.STUDENT_PORC3);
            // Colocar los valores en el TextView
            percent1.setText(porc1);
            percent2.setText(porc2);
            percent3.setText(porc3);
        }
    }

    public void retroceder(View view){
        Intent anterior = new Intent(this, StudentActivity.class);
        startActivity(anterior);
    }
}

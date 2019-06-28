package pe.edu.pucp.grupo02.asistenciapucp.features.teacher.token;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Random;

import pe.edu.pucp.grupo02.asistenciapucp.R;
import pe.edu.pucp.grupo02.asistenciapucp.features.student.messages.StudentMessagesActivity;
import pe.edu.pucp.grupo02.asistenciapucp.features.teacher.TeacherActivity;


public class TeacherTokenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_token);
    }

    Switch swt = (Switch) findViewById(R.id.teacherToken_swt_enable);

    public void generateToken(View view){

        int random = new  Random().nextInt(10000) + 10000;
        ((TextView) findViewById(R.id.teacherToken_txt_token)).setText(String.valueOf(random));
    }

    public  void swtOnClick(View view){
        if (! swt.isChecked())
        {
            //falta
        }
    }

    private void showInfo(){
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String courseName = extras.getString(TeacherActivity.TEACHER_EXTRA_COURSENAME);
            String courseSch = extras.getString(TeacherActivity.TEACHER_EXTRA_COURSESCH);
            String courseTime = extras.getString(TeacherActivity.TEACHER_EXTRA_COURSETIME);
        }


    }
}

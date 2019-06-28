package pe.edu.pucp.grupo02.asistenciapucp.data.api;

import pe.edu.pucp.grupo02.asistenciapucp.data.api.in.GenerateTokenInRO;
import pe.edu.pucp.grupo02.asistenciapucp.data.api.in.LoginInRO;
import pe.edu.pucp.grupo02.asistenciapucp.data.api.in.TeacherTokenInRO;
import pe.edu.pucp.grupo02.asistenciapucp.data.api.out.GenerateTokenOutRO;
import pe.edu.pucp.grupo02.asistenciapucp.data.api.out.StudentMessagesOutRO;
import pe.edu.pucp.grupo02.asistenciapucp.data.api.out.TeacherTokenOutRO;
import pe.edu.pucp.grupo02.asistenciapucp.data.api.out.UserOutRO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("user/login")
    Call<UserOutRO> login(@Body LoginInRO user);

    @POST("user/studentmsjes")
    Call<StudentMessagesOutRO> announcements(@Body LoginInRO user);

    @POST("user/teachertoken")
    Call<TeacherTokenOutRO> token(@Body TeacherTokenInRO user);

    @POST("user/generatetoken")
    Call<GenerateTokenOutRO> gToken(@Body GenerateTokenInRO user);

}

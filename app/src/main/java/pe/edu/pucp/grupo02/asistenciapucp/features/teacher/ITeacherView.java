package pe.edu.pucp.grupo02.asistenciapucp.features.teacher;

import pe.edu.pucp.grupo02.asistenciapucp.features.base.IView;

public interface ITeacherView extends IView {

    void MoverAGenerarToken(String name, String sch, String time); //listo datos de entrada

    void MoverATeacherAttendance(int attId, String porc1, String porc2, String porc3);//String course1, String course2, String course3); //usar lista

    //void MoverATeacherAMessages();

    void gotoTeacherMessages(int msjeId, String cur1, String cur2, String cur3, String[] hor1, String[] hor2, String[] hor3);

    void showErrorDialog(String message);

    void askForMessagesOffline();

    void askForAttendanceOffline();

}

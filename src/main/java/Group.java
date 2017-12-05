
import java.util.ArrayList;


public class Group {
    private String groupName;
    private String professorUsername;
    private ArrayList<String> studentUsername;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getProfessorUsername() {
        return professorUsername;
    }

    public void setProfessorUsername(String professorUsername) {
        this.professorUsername = professorUsername;
    }

    public ArrayList<String> getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(ArrayList<String> studentUsername) {
        this.studentUsername = studentUsername;
    }
    
    
}
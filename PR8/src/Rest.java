import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Rest {
    private String query;
    private String func;

    private static StudentController studentController = new StudentController();

    public Rest(String request) {
        String query = request.substring(request.indexOf("?") + 1);
        this.query = query;
        if(query.contains("&")){
            this.func = query.substring(query.indexOf("func") + "func".length() + 1, query.indexOf("&"));
        }else {
            this.func = query.substring(query.indexOf("func") + "func".length() + 1);
        }
    }

    //http://localhost:8080/api/?func=mtrcombine&matrix1=1,2,3,4;1,2,3,4;1,2,3,4;1,2,3,4;&matrix2=5,6,7,8;5,6,7,8;5,6,7,8;5,6,7,8;
    public String getMatrixCombineResult() {
        String matrix1 = getQueryVar("matrix1");
        String matrix2 = getQueryVar("matrix2");
        MatrixCombiner matrixCombiner = new MatrixCombiner(matrix1, matrix2);
        matrixCombiner.combine();
        return matrixCombiner.getResult();
    }

    public String getQueryVar(String varKey) {
        String resultVar = "";
        if(this.query.contains(varKey)) {
            if(this.query.indexOf("&", this.query.indexOf(varKey)) != -1){
                resultVar = this.query.substring(this.query.indexOf(varKey) + varKey.length() + 1, this.query.indexOf("&", this.query.indexOf(varKey)));
            }
            else {
                resultVar = this.query.substring(this.query.indexOf(varKey) + varKey.length() + 1);
            }
        }
        return resultVar;
    }

    //http://localhost:8080/api/?func=table&mode=add&name=Ivan&lname=Ivanov&mname=Ivanovich&yob=2000
    //http://localhost:8080/api/?func=table&mode=delete&id=1
    //http://localhost:8080/api/?func=table&mode=update&id=1&name=Ivan&lname=Ivanov&mname=Ivanovich&yob=2000
    //http://localhost:8080/api/?func=table&mode=get
    public String studentsControl(String queryUrl) {
        String mode;

        mode = getQueryVar("mode");
        String response = "";

        int studentID = 0;
        String studentName = "";
        String studentLname = "";
        String studentMname = "";
        String studentCourse = "";

        if(this.query.contains("id")) {
            studentID = Integer.parseInt(getQueryVar("id"));
        }
        if(this.query.contains("fname")) {
            studentName = getQueryVar("fname");
        }
        if(this.query.contains("lname")) {
            studentLname = getQueryVar("lname");
        }
        if(this.query.contains("mname")) {
            studentMname = getQueryVar("mname");
        }
        if(this.query.contains("course")) {
            studentCourse = getQueryVar("course");
        }

        switch (mode) {
            case "get":
                response = studentController.getStudents();
                break;
            case "delete":
                response = studentController.deleteStudent(studentID);
                break;
            case "update":
                response = studentController.updateStudent(
                        studentID,
                        studentName,
                        studentLname,
                        studentMname,
                        studentCourse
                );
                break;
            case "add":
                response = studentController.addStudent(
                        studentName,
                        studentLname,
                        studentMname,
                        studentCourse
                );
                break;
        }
        return response;
    }

    public String getResponse() {
        try {
            this.query = URLDecoder.decode(this.query, StandardCharsets.UTF_8.name());
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        String response = "";
        if(this.func.equals("mtrcombine")){
            response = getMatrixCombineResult();
        }
        else if(this.func.equals("table")){
            response = studentsControl(this.query);
        }
        return response;
    }
}

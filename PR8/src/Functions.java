import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Functions {
    private String query;
    private String func;

    private static RobotsTable robotsTable = new RobotsTable();

    public Functions(String request) {
        String query = request.substring(request.indexOf("?") + 1);
        this.query = query;
        if(query.contains("&")){
            this.func = query.substring(query.indexOf("func") + 5, query.indexOf("&"));
        }else {
            this.func = query.substring(query.indexOf("func") + 5);
        }
    }

    public String getVar(String varKey) {
        String resultVar = "";
        if(this.query.contains(varKey)) {
            if (this.query.indexOf("&", this.query.indexOf(varKey)) == -1) {
                resultVar = this.query.substring(this.query.indexOf(varKey) + varKey.length() + 1);
            } else {
                resultVar = this.query.substring(this.query.indexOf(varKey) + varKey.length() + 1, this.query.indexOf("&", this.query.indexOf(varKey)));
            }
        }
        return resultVar;
    }

    public String getCalcResult() {
        try {
            int a = Integer.parseInt(getVar("calc-a"));
            int b = Integer.parseInt(getVar("calc-b"));
            String sigh = getVar("calc-s");
            switch (sigh) {
                case "+":
                    return String.valueOf(a + b);
                case "*":
                    return String.valueOf(a * b);
                case "/":
                    return String.valueOf(a / b);
                case "-":
                    return String.valueOf(a - b);
                default:
                    return "Неизвестный знак";
            }

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String robotsFun(String queryUrl) {
        String mode;

        mode = getVar("mode");
        String response = "";

        int studentID = 0;
        String name = "";
        String desc = "";
        String skill1 = "";
        String skill2 = "";

        if(this.query.contains("id")) {
            studentID = Integer.parseInt(getVar("id"));
        }
        if(this.query.contains("name")) {
            name = getVar("name");
        }
        if(this.query.contains("description")) {
            desc = getVar("description");
        }
        if(this.query.contains("skill1")) {
            skill1 = getVar("skill1");
        }
        if(this.query.contains("skill2")) {
            skill2 = getVar("skill2");
        }

        switch (mode) {
            case "get":
                response = robotsTable.getRobots();
                break;
            case "delete":
                response = robotsTable.deleteRobot(studentID);
                break;
            case "update":
                response = robotsTable.updateRobot(
                        studentID,
                        name,
                        desc,
                        skill1,
                        skill2
                );
                break;
            case "add":
                response = robotsTable.addRobot(
                        name,
                        desc,
                        skill1,
                        skill2
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
        if(this.func.equals("calc")){
            response = getCalcResult();
        }
        else if(this.func.equals("table")){
            response = robotsFun(this.query);
        }
        return response;
    }
}

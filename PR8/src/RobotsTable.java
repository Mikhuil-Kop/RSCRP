import java.util.ArrayList;

public class RobotsTable {
    private ArrayList<Robot> robots = new ArrayList<>();
    private int studentCount = 0;

    public String getRobots() {
        String response = "[";
        for (Robot robot : robots) {
            if (robot.equals(robots.get(robots.size() - 1))) {
                response += "{" +
                        "\"ID\": \"" + robot.getID() + "\"," +
                        "\"name\": \"" + robot.getName() + "\"," +
                        "\"description\": \"" + robot.getDescription() + "\"," +
                        "\"skill1\": \"" + robot.getSkillName1() + "\"," +
                        "\"skill2\": \"" + robot.getSkillName2() + "\"" +
                        "}";
            } else {
                response += "{" +
                        "\"ID\": \"" + robot.getID() + "\"," +
                        "\"name\": \"" + robot.getName() + "\"," +
                        "\"description\": \"" + robot.getDescription() + "\"," +
                        "\"skill1\": \"" + robot.getSkillName1() + "\"," +
                        "\"skill2\": \"" + robot.getSkillName2() + "\"" +
                        "},";
            }

        }
        response += "]";
        return response;
    }

    public String addRobot(String name, String lastName, String middleName, String course) {
        robots.add(new Robot(studentCount, name, lastName, middleName, course));
        studentCount++;
        return "{\"Message\": \"Robot Added\"}";
    }

    public String updateRobot(int ID, String name, String description, String skill1, String skill2) {
        for (Robot robot : robots) {
            if (robot.getID() == ID) {
                robot.setName(name);
                robot.setDescription(description);
                robot.setSkillName1(skill1);
                robot.setSkillName2(skill2);
            }
        }
        return "{\"Message\": \"Robot with ID=" + ID + " Updated\"}";
    }

    public String deleteRobot(int ID) {
        robots.removeIf(robot -> robot.getID() == ID);
        return "{\"Message\": \"Robot with ID=" + ID + " Deleted\"}";
    }
}

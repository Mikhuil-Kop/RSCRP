public class RobotsTable {

    public String getRobots() {
        String response = "[";
        var robots = Robot.getAllArr();
        for (Robot robot : robots) {
            if (robot.equals(robots.get(robots.size() - 1))) {
                response += "{" +
                        "\"ID\": \"" + robot.getId() + "\"," +
                        "\"name\": \"" + robot.getName() + "\"," +
                        "\"description\": \"" + robot.getDescription() + "\"," +
                        "\"skill1\": \"" + robot.getSkillName1() + "\"," +
                        "\"skill2\": \"" + robot.getSkillName2() + "\"" +
                        "}";
            } else {
                response += "{" +
                        "\"ID\": \"" + robot.getId() + "\"," +
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

    public String addRobot(String name, String description, String skill1, String skill2) {
        Robot.save(new Robot(name, description, skill1, skill2));
        return "{\"Message\": \"Robot Added\"}";
    }

    public String updateRobot(int ID, String name, String description, String skill1, String skill2) {
        Robot.update(new Robot(ID, name, description, skill1, skill2));
        return "{\"Message\": \"Robot with ID=" + ID + " Updated\"}";
    }

    public String deleteRobot(int ID) {
        Robot.delete(Robot.findById(ID));
        return "{\"Message\": \"Robot with ID=" + ID + " Deleted\"}";
    }
}

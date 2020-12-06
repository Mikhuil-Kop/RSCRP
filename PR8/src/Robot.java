public class Robot {

    private int ID;
    private String name;
    private String description;
    private String skillName1;
    private String skillName2;

    public Robot(int ID, String name, String description, String skillName1, String skillName2) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.skillName1 = skillName1;
        this.skillName2 = skillName2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkillName1() {
        return skillName1;
    }

    public void setSkillName1(String skillName1) {
        this.skillName1 = skillName1;
    }

    public String getSkillName2() {
        return skillName2;
    }

    public void setSkillName2(String skillName2) {
        this.skillName2 = skillName2;
    }

    public int getID() {
        return ID;
    }
}

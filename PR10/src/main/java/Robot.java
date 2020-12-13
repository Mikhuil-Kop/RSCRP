import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table (name = "robots")
public class Robot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column (name = "description")
    private String description;
    @Column (name = "skill_name_1")
    private String skillName1;
    @Column (name = "skill_name_2")
    private String skillName2;

    public Robot(){}

    public Robot(int id, String name, String description, String skillName1, String skillName2) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.skillName1 = skillName1;
        this.skillName2 = skillName2;
    }

    public Robot(String name, String description, String skillName1, String skillName2) {
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

    public int getId() {
        return id;
    }


    //Взаимодействие с БД
    public static Robot findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Robot robot = session.get(Robot.class, id);
        session.close();
        return robot;
    }

    public static void save(Robot robot) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(robot);
        tx1.commit();
        session.close();
    }

    public static void update(Robot robot) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(robot);
        tx1.commit();
        session.close();
    }

    public static void delete(Robot robot) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(robot);
        tx1.commit();
        session.close();
    }

    public static ArrayList<Robot> getAllArr() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        ArrayList<Robot> robots = (ArrayList<Robot>) session.createQuery("from Robot").list();
        session.close();
        return robots;
    }
}

package com.kozlachkov.dao;

import com.kozlachkov.models.Person;
import com.kozlachkov.models.Roles;
import com.kozlachkov.models.UserDB;
import com.kozlachkov.models.WebPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class PersonDao {
    private JdbcTemplate jdbcTemplate;


    public static final String USER_NAME = "roman";
    public static final String PASSWORD = "DBsurleversant";
    public static final String URL = "jdbc:mysql://localhost:3306/javastudy";
    public static Statement statement;
    public static Connection connection2;

    static{
        try {Class.forName("com.mysql.cj.jdbc.Driver");   }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    static{
        try{ connection2=DriverManager.getConnection(URL, USER_NAME, PASSWORD);}
        catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }



    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index (){
        return jdbcTemplate.query("SELECT* FROM person", new PersonMapper());
    }

    public List<UserDB> indexUserDB (){
        return jdbcTemplate.query("SELECT* FROM usr", new UsrMapper());
    }

    public List<WebPost> getAllPosts (int id){
        return jdbcTemplate.query("SELECT* FROM blog WHERE id=?", new Object[]{id}, new BlogMapper());
    }

    public UserDB createUser (UserDB userDB){
        int id1;
        UserDB userDB1 = getMaxIdFromUsr();
        if(userDB1==null) id1=0;
        else id1=userDB1.getId()+1;
        jdbcTemplate.update("INSERT INTO usr VALUE(?,?,?,?,?,?)",
                id1, userDB.getUsername(), userDB.getPassword(), userDB.getCheck_pass(), Roles.USER.name(), true);
        return getUsrById(id1);
    }

    public UserDB getMaxIdFromUsr (){
        return jdbcTemplate.query("SELECT * FROM usr WHERE id = ( SELECT MAX(id) FROM usr )", new UsrMapper())
                .stream().findFirst().orElse(null);
    }

    public UserDB getUsrByName (String name){
        return jdbcTemplate.query("SELECT* FROM usr WHERE username=?", new Object[]{name}, new UsrMapper())
                .stream().findFirst().orElse(null);
    }
    public UserDB getUsrById (int id){
        return jdbcTemplate.query("SELECT* FROM usr WHERE id=?", new Object[]{id}, new UsrMapper())
                .stream().findFirst().orElse(null);
    }

    public Person getPersonById (int id){
        return jdbcTemplate.query("SELECT* FROM person WHERE id=?", new Object[]{id}, new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public boolean PersonIsActive(UserDB userDB) {
        Person person;
        try {
            person = getPersonById(userDB.getId());
        }
        catch (Exception e) {
            return false;
        }
        if (person==null) return false;
        if (person.isAvtive()) return  true;
        else return false;
    }

    public void save(Person person, UserDB userDB){
         jdbcTemplate.update("INSERT INTO person VALUE(?,?,?,?,?)",
                 userDB.getId(), person.getName(), person.getAge(), person.getEmail(), true);
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET name=?, age=?, email=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void updateNik(int id, UserDB updatedUser) {
        jdbcTemplate.update("UPDATE usr SET username=?, password=?, check_pass=? WHERE id=?",
                updatedUser.getUsername(), updatedUser.getPassword(), updatedUser.getCheck_pass(), id);
    }

    public void delete (int id){
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
        jdbcTemplate.update("DELETE FROM usr WHERE id=?", id);
        jdbcTemplate.update("DELETE FROM blog WHERE id=?", id);
    }

    public void deleteRegisteredNik (int id){
        jdbcTemplate.update("DELETE FROM usr WHERE id=?", id);
    }

    public boolean blogTableEmpty (int id){
        boolean hasRecord = jdbcTemplate.query("SELECT 1 FROM blog WHERE id=?",
                    new Object[]{id}, (ResultSet rs) -> {
                                    if (rs.next()) {
                                        return true;
                                    }
                                    return false;
                                }
                        );
        return hasRecord;
    }

    public int getMaxNoteFromUsr (int id){
        try {
            PreparedStatement preparedStatement = connection2.prepareStatement("SELECT MAX(id_note) AS num1 FROM blog WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            int num1= -1;
            if (resultSet.next()) {
                String temp = resultSet.getString("num1"); // "quantity" - псевдоним из запроса
                num1 = Integer.parseInt(temp);
            }
            return num1;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void recordNote (WebPost webPost){
        jdbcTemplate.update("INSERT INTO blog VALUE(?,?,?,?,?)",
                webPost.getId(), webPost.getId_note(), webPost.getTitle(), webPost.getText(), webPost.getData_pub());
    }

}

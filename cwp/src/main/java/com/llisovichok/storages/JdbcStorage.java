package com.llisovichok.storages;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.User;
import com.llisovichok.service.Settings;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO rewrite method addUser (change it to 'returning boolean')
 * Created by ALEKSANDR KUDIN on 12.04.2017.
 */

@Repository
public class JdbcStorage implements Storage {

    private final static JdbcStorage INSTANCE = new JdbcStorage();
    private Connection connection = null;

    private JdbcStorage() {
        Settings settings = Settings.getInstance();
        try {

            Class.forName(settings.getValue("jdbc.driver_class"));
            this.connection = DriverManager.getConnection(settings.getValue("jdbc.url"),
                    settings.getValue("jdbc.username"), settings.getValue("jdbc.password"));
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    public static JdbcStorage getINSTANCE() {
        return INSTANCE;
    }


    private User createUser(final ResultSet rs){
        try{
            String petKind = rs.getString("kind");
            String petName = rs.getString("nickname");
            int petAge = rs.getInt("age");
            String userFName = rs.getString("first_name");
            String userLName = rs.getString("last_name");
            String userAddress = rs.getString("address");
            long userPhone = rs.getLong("phone");

            Pet pet = new Pet(petName, petKind, petAge);

            return new User(userFName, userLName, userAddress, userPhone, pet);

        } catch (SQLException ex){
            ex.printStackTrace();
            // code to handle the exception ?
        }
        throw new IllegalStateException("Couldn't create user!");
    }

    @Override
    public Collection<User> values() {
        final List<User> users = new ArrayList<>();
        try (final Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM clients, pets WHERE clients.uid = pets.client_id ORDER BY clients.uid")) {

            while (rs.next()) {
                User user = createUser(rs);
                user.setId(rs.getInt("uid"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return users;
    }

    @Override
    public int addUser(final User user) {

        int generatedId = -1;

        try (final PreparedStatement clientStatement = this.connection.prepareStatement("INSERT INTO clients(first_name, last_name, address, phone) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            final PreparedStatement petStatement= this.connection.prepareStatement("INSERT INTO pets(client_id, nickname, kind, age) VALUES (?,?,?,?)")){

            clientStatement.setString(1, user.getFirstName());
            clientStatement.setString(2, user.getLastName());
            clientStatement.setString(3, user.getAddress());
            clientStatement.setLong(4, user.getPhoneNumber());
            clientStatement.executeUpdate();

            try(ResultSet generatedKeys = clientStatement.getGeneratedKeys()){
                if(generatedKeys.next()){
                     generatedId = generatedKeys.getInt(1);
                }
            }

            petStatement.setInt(1, generatedId);
            petStatement.setString(2, user.getPet().getName());
            petStatement.setString(3, user.getPet().getKind());
            petStatement.setInt(4, user.getPet().getAge());
            petStatement.executeUpdate();

        } catch(SQLException e) {
            throw new IllegalStateException(e);
          }

        //throw new IllegalStateException("Could not create client!");
        return  generatedId;
    }

    @Override
    public boolean editUser(final Integer id, final User user) {

        try (final PreparedStatement clientStatement = this.connection.prepareStatement("UPDATE clients SET first_name=?, last_name=?, address=?, phone=? WHERE public.clients.uid=?");
             final PreparedStatement petStatement= this.connection.prepareStatement("UPDATE pets SET nickname=?, kind=?, age=? WHERE public.pets.client_id=?;")){

            clientStatement.setString(1, user.getFirstName());
            clientStatement.setString(2, user.getLastName());
            clientStatement.setString(3, user.getAddress());
            clientStatement.setLong(4, user.getPhoneNumber());
            clientStatement.setInt(5, id);
            int clistat = clientStatement.executeUpdate();

            petStatement.setString(1, user.getPet().getName());
            petStatement.setString(2, user.getPet().getKind());
            petStatement.setInt(3,user.getPet().getAge());
            petStatement.setInt(4, id);
            int petstat = petStatement.executeUpdate();
            if(clistat > 0 && petstat > 0)return true;
            else return false;

        } catch(SQLException e) {
            e.getSQLState();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public ConcurrentHashMap<Integer, User> getUsers() {
        return null;
    }

    @Override
    public User getUser(final Integer id) {
        try(PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM clients INNER JOIN pets on clients.uid=client_id WHERE clients.uid = ?")){

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            ArrayList<User> users = new ArrayList<>();
            while(rs.next()){
                User user = createUser(rs);
                user.setId(id);
                users.add(user);
            }

            rs.close();
            return users.get(0);

        } catch (SQLException ex){
            ex.printStackTrace();
            //code to handle the exception
        }

        throw new IllegalStateException("Cannot find the client with such ID!");
    }

    @Override
    public void removeUser(final Integer userId) {
        try (PreparedStatement clientStatement = this.connection.prepareStatement("DELETE FROM clients WHERE uid = ?");
            PreparedStatement petStatement = this.connection.prepareStatement("DELETE FROM pets WHERE pets.client_id = ?");
            PreparedStatement additionalInfo = this.connection.prepareStatement("DELETE FROM additional_pets_info WHERE additional_pets_info.client_id = ?")){

            additionalInfo.setInt(1, userId);
            additionalInfo.executeUpdate();

            petStatement.setInt(1, userId);
            petStatement.executeUpdate();

            clientStatement.setInt(1, userId);
            clientStatement.executeUpdate();

        } catch(SQLException ex){
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public void addUser(Integer id, User user) {

    }

    @Override
    public void addPhoto(Integer userId, ByteArrayInputStream photoBytes, int streamSize){

        try(PreparedStatement loadPhoto = this.connection.prepareStatement("INSERT INTO additional_pets_info (pet_id, photo) VALUES (?, ?)")){
            loadPhoto.setInt(1 , userId);
            loadPhoto.setBinaryStream(2, photoBytes, streamSize);
            loadPhoto.executeUpdate();
        } catch(SQLException e){
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Collection<User> findUsers(final String input, final boolean lookInFirstName,
                                      final boolean lookInLastName, final boolean lookInPetName) {

        ArrayList<User> result = new ArrayList<>();
        ArrayList<User> currentUsers = new ArrayList<>(values());

        boolean first = lookInFirstName; //should we look coincidences in first names
        boolean last = lookInLastName; //should we look coincidences in last names
        boolean pet = lookInPetName; //should we look coincidences in pet's names

        if(first && last && pet){
            for(User user : currentUsers){
                if(user.getFirstName().contains(input) || user.getLastName().contains(input) || user.getPet().getName().contains(input)) result.add(user);
            }
        }
        else if(first && last){
            for(User user : currentUsers){
                if(user.getFirstName().contains(input) || user.getLastName().contains(input)) result.add(user);
            }
        }

        else if(first && pet){
            for(User user : currentUsers){
                if(user.getFirstName().contains(input) || user.getPet().getName().contains(input)) result.add(user);
            }
        }

        else if(last && pet){
            for(User user : currentUsers){
                if(user.getLastName().contains(input) || user.getPet().getName().contains(input)) result.add(user);
            }
        }

        else if(first){
            for(User user : currentUsers){
                if(user.getFirstName().contains(input)) result.add(user);
            }
        }

        else if(last){
            for(User user : currentUsers){
                if(user.getLastName().contains(input)) result.add(user);
            }
        }

        else if(pet){
            for(User user : currentUsers){
                if(user.getPet().getName().contains(input)) result.add(user);
            }
        }

        else {
            for (User user : currentUsers) {
                if (user.getFirstName().contains(input) || user.getLastName().contains(input) || user.getPet().getName().contains(input))
                    result.add(user);
            }
        }

        return result;
    }

    @Override
    public void close(){
        try{
            this.connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}

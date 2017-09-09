package com.llisovichok.storages;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.Role;
import com.llisovichok.models.User;

/**
 * Created by KUDIN ALEKSANDR on 09.09.2017.
 *
 * This class is a helper class for creation a user
 * for the tests running
 */
public class UserCreationHelper {

    private final static UserCreationHelper CREATION_HELPER = new UserCreationHelper();
    private final static HibernateStorage H_STORAGE = HibernateStorage.getInstance();

    private static Integer userId;

    private static Integer petId;

    /**
     * This static block creates a user with  a pet
     * and assigns their ids to the fields 'userId' and 'petId'
     * respectively
     */
    static{
        User user = new User("Test", "Test", "Test", 1201251455454L,
                new Pet("Test", "test", 3));
        user.setRole(new Role("user"));
        userId = H_STORAGE.addUser(user);
        petId = H_STORAGE.getUser(userId).getPet().getId();
    }

    public static UserCreationHelper getInstance(){
        return CREATION_HELPER;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getPetId() {
        return petId;
    }
}

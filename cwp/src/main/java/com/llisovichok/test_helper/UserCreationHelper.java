package com.llisovichok.test_helper;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.lessons.clinic.PetPhoto;
import com.llisovichok.models.Message;
import com.llisovichok.models.Role;
import com.llisovichok.models.User;
import com.llisovichok.storages.HibernateStorage;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by KUDIN ALEKSANDR on 09.09.2017.
 *
 * This class is a test_helper class for creation a user
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
        user.getPet().setPhoto(new PetPhoto(new byte[] { (byte)0xe0, 0x4f, (byte)0xd0, 0x20,  (byte)0x9d }));
        user.setRole(new Role("user"));

        userId = H_STORAGE.addUser(user);
        petId = H_STORAGE.getUser(userId).getPet().getId();

        User retrieved = H_STORAGE.getUser(userId);
        Set<Message> messages = new HashSet<>();
        Message messageOne = new Message("first");
        messageOne.setUser(retrieved);
        Message messageTwo = new Message("second");
        messageTwo.setUser(retrieved);
        messages.add(messageOne);
        messages.add(messageTwo);
        retrieved.setMessages(messages);
        H_STORAGE.editUser(userId, retrieved);
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

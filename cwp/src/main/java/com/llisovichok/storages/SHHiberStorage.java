package com.llisovichok.storages;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.User;

/**
 * Created by KUDIN ALEKSANDR on 08.10.2017.
 */
public interface SHHiberStorage extends SHStorages<User> {

    boolean addPhotoWithHibernate(Integer petId, byte [] photoBytes);

    Pet getPetById(Integer petId);

    void addMessage(Integer id, String message);
}

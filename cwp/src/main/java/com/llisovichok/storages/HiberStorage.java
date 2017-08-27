package com.llisovichok.storages;

import com.llisovichok.lessons.clinic.Pet;

/**
 * Created by KUDIN ALEKSANDR on 03.08.2017.
 */
public interface HiberStorage extends Storage {

    boolean addPhotoWithHibernate(Integer userId, byte [] photoBytes);

    Pet getPetById(Integer petId);

}

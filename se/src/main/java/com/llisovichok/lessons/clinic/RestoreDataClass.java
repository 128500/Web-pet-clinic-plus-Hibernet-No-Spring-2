package com.llisovichok.lessons.clinic;

import java.io.*;

/**
 * This class restores saved data if there is any
 * Created by ALEKSANDR KUDIN on 23.01.2017.
 */
public class RestoreDataClass {

    static Clinic restoredClinic = null;
    RestoreDataClass(){
        try{
            FileInputStream fis = new FileInputStream("Clinic.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            restoredClinic = (Clinic) ois.readObject();
            ois.close();
            new ClinicMessageWindow("Данные о клиентах восстановлены :)!",1);
        } catch(FileNotFoundException e){
            new ClinicMessageWindow("Отсутствует файл для восстановления данных\n"+ e.getLocalizedMessage(), 2);
        }catch (StreamCorruptedException exc){
            new ClinicMessageWindow("Чтение данных для восстановления прервано\n" + exc.getMessage(), 2);
        } catch(IOException ex){
            new ClinicMessageWindow("Ошибка чтения данных\n"+ ex.getMessage(), 2);
        } catch (SecurityException exce){
                new ClinicMessageWindow("Ошибка безопасности\n" + exce.getMessage(), 2);
        } catch (NullPointerException excep){
            new ClinicMessageWindow("Ошибка \n" + excep.getMessage(), 2);
        } catch (ClassNotFoundException exception){
            new ClinicMessageWindow("Чтот то пошло совсем неправильно\n Не удалось восстановить данные", 3);
        } catch (ClassCastException except){
            new ClinicMessageWindow("Чтот то пошло совсем неправильно\n Не удалось восстановить данные\n" + except.getMessage(), 3);
        }

    }

}

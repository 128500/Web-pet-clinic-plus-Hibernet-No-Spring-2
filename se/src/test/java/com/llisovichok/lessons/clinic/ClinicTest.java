package com.llisovichok.lessons.clinic;

/**
 * Created by ALEKSANDER KUDIN on 04.02.2017.
 */
//@Ignore
/**public class ClinicTest {
    @Test
    public void getClientsList() throws Exception {

    }

    @Test
    public void findClientByPetName() throws Exception {

    }

    @Test
    public void findPetByClientName() throws Exception {
        Clinic clin = new Clinic();
        clin.addClient(new Client("0", new Pet("0")));
        clin.addClient(new Client("1", new Pet("1")));
        clin.addClient(new Client("2", new Pet("2")));
        clin.addClient(new Client("3", new Pet("3")));
        clin.addClient(new Client("4", new Pet("4")));
        clin.addClient(new Client("5", new Pet("5")));
        clin.addClient(new Client("6", new Pet("6")));
        clin.addClient(new Client("7", new Pet("7")));
        clin.addClient(new Client("8", new Pet("8")));

        ArrayList<User> users = new ArrayList<User>();
        for(int i = 0; i < clin.getClientsList().size()*10; i++){
            users.add(new User(clin));
        }

        UserQueue userQueue = new UserQueue();

        Admin admin = new Admin(clin);

        admin.start();
        admin.setPriority(5);
        userQueue.push(admin);
        userQueue.poll();

        for (User u : users){

            u.start();
            u.setPriority(7);
            userQueue.push(u);
            userQueue.poll();
        }

        Thread.currentThread().join(4000);
    }

    @Test
    public void resetClientName() throws Exception {
        Clinic clin = new Clinic();
        clin.addClient(new Client("0", new Pet("0")));
        clin.addClient(new Client("1", new Pet("1")));
        clin.addClient(new Client("2", new Pet("2")));
        clin.addClient(new Client("3", new Pet("3")));
        clin.addClient(new Client("4", new Pet("4")));
        clin.addClient(new Client("5", new Pet("5")));
        clin.addClient(new Client("6", new Pet("6")));
        clin.addClient(new Client("7", new Pet("7")));
        clin.addClient(new Client("8", new Pet("8")));

        ArrayList<User> users = new ArrayList<User>();
        for(int i = 0; i < clin.getClientsList().size(); i++){
            users.add(new User(clin));
        }

        Admin admin = new Admin(clin);
        admin.setPriority(10);
        admin.start();
        admin.join();

        for (User u : users){
            u.setPriority(5);
            u.start();
            //u.join();
        }
        Thread.currentThread().join(1000);

        assertEquals("100", clin.getClientsList().get(0).getName());
        assertEquals("101", clin.getClientsList().get(1).getName());
        assertEquals("102", clin.getClientsList().get(2).getName());
        assertEquals("103", clin.getClientsList().get(3).getName());
        assertEquals("104", clin.getClientsList().get(4).getName());
        assertEquals("105", clin.getClientsList().get(5).getName());
        assertEquals("106", clin.getClientsList().get(6).getName());
        assertEquals("107", clin.getClientsList().get(7).getName());
        assertEquals("108", clin.getClientsList().get(8).getName());

    }*/

   /** private static final class User extends Thread{

        private final Clinic clinic;

        User(Clinic clinic){
            this.clinic = clinic;
        }

        public void seePetName(){
            System.out.println(String.format("%s : '%s'", Thread.currentThread().getId(), clinic.getClientsList().get((int)(Math.random()* 8)).getName()));
        }

        public void findClient(){
            int name = (int)(Math.random() * 8);
            String s = Integer.valueOf(name).toString();
            Client temp = null;
            try{
               temp =  clinic.findClientByPetName(s);
            } catch (UserException e){
                e.printStackTrace();
            }
            System.out.println(String.format("%s : '%s'", Thread.currentThread().getId(), temp.getName()));
        }


        public void run(){
            seePetName();
            findClient();
        }
    }

    private static final class Admin extends Thread{

        private final Clinic clinic;

        Admin(Clinic clinic){
            this.clinic = clinic;
        }

       public void renameClient(final String oldName, final String newName ) throws UserException{
           clinic.resetClientName(oldName, newName);
           System.out.println(String.format("Name was changed from '%s' to '%s'", oldName, newName));
        }

        public void run(){
            try {
                for(int i = 0; i < clinic.getClientsList().size(); i++){
                String oldOne = ""+i;
                String newOne = ""+(i+100);
                renameClient(oldOne, newOne);
                //Thread.sleep(2000);}
            } catch (UserException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException ex){
                ex.printStackTrace();
            } catch (InterruptedException exc){
                exc.printStackTrace();
            }
        }
    }

    private static final class UserQueue {

        public final LinkedList<Thread> queue = new LinkedList<Thread>();

        public void push(final Thread t){
            synchronized (this.queue){
                queue.add(t);
                queue.notifyAll();
            }
        }

        public Thread poll(){
            synchronized (this.queue){
                while (this.queue.isEmpty()){
                    try{
                        queue.wait();
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                return this.queue.remove();
            }
        }
    }
}*/
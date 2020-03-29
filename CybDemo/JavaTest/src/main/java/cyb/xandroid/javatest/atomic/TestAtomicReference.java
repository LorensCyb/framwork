package cyb.xandroid.javatest.atomic;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class TestAtomicReference {


    public static void main(String[] arg) {
        AtomicReference<Userinfo> atomic = new AtomicReference();
    }

    static class Userinfo{
        private String username;
        private String passowrd;

        public Userinfo(){

        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassowrd() {
            return passowrd;
        }

        public void setPassowrd(String passowrd) {
            this.passowrd = passowrd;
        }
    }
}

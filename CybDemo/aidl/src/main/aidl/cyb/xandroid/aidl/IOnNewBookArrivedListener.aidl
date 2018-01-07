// IOnNewBookArrivedListener.aidl
package cyb.xandroid.aidl;

// Declare any non-default types here with import statements
import cyb.xandroid.aidl.Book;

interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onNewBookArriver(in Book newBook);
}

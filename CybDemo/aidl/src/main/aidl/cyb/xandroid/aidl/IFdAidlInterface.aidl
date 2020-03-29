// IFdAidlInterface.aidl
package cyb.xandroid.aidl;

// Declare any non-default types here with import statements

import cyb.xandroid.aidl.IFdCallback;
import android.os.ParcelFileDescriptor;

interface IFdAidlInterface {

    void publish(in ParcelFileDescriptor fd,IFdCallback callback);

}

package cyb.xandroid.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenyangbo on 2017/9/8.
 */

public class Book implements Parcelable {


    private int num;

    private String name;

    public Book(int num, String name) {
        this.num = num;
        this.name = name;
    }

    protected Book(Parcel in) {
        num = in.readInt();
        name = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(num);
        parcel.writeString(name);
    }
}

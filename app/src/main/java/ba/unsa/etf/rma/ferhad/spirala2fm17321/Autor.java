package ba.unsa.etf.rma.ferhad.spirala2fm17321;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Autor implements Parcelable{
    private String imeiPrezime;
    private ArrayList<String> knjige;

    public Autor(String imeiPrezime, String id)
    {
        this.imeiPrezime = imeiPrezime;
        this.knjige = new ArrayList<String>();
        knjige.add(id);
    }

    protected Autor(Parcel in) {
        imeiPrezime = in.readString();
        if (in.readByte() == 0x01) {
            knjige = new ArrayList<String>();
            in.readList(knjige, String.class.getClassLoader());
        } else {
            knjige = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imeiPrezime);
        if (knjige == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(knjige);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Autor> CREATOR = new Parcelable.Creator<Autor>() {
        @Override
        public Autor createFromParcel(Parcel in) {
            return new Autor(in);
        }

        @Override
        public Autor[] newArray(int size) {
            return new Autor[size];
        }
    };

    public String getImeiPrezime() {
        return imeiPrezime;
    }

    public void setImeiPrezime(String imeiPrezime) {
        this.imeiPrezime = imeiPrezime;
    }

    public ArrayList<String> getKnjige(){
        return knjige;
    }

    public void setKnjige(ArrayList<String> knjige){
        this.knjige = knjige;
    }

    public void dodajKnjigu(String id){
        if(!knjige.contains(id))
            knjige.add(id);
    }

}

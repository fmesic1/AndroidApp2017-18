package ba.unsa.etf.rma.ferhad.spirala2fm17321;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class MojResulReceiver extends ResultReceiver {

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    private Receiver mReceiver;

    public void setmReceiver(Receiver mReceiver) {
        this.mReceiver = mReceiver;
    }

    public MojResulReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if(mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}

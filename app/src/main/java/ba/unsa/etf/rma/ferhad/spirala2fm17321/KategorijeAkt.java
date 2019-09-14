package ba.unsa.etf.rma.ferhad.spirala2fm17321;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class KategorijeAkt extends AppCompatActivity {
    public static Kolekcija moja;
    private Boolean siriL;
    private Button dodajOnline;
    private FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorije_akt);

        moja = new Kolekcija();
        dodajOnline = (Button)findViewById(R.id.dDodajOnline);
        frame = (FrameLayout)findViewById(R.id.fragmentContainer);

        ListeFragment novi = new ListeFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragmentContainer, novi);
        transaction.addToBackStack(null);
        transaction.commit();

        dodajOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentOnline novi = new FragmentOnline();

                FragmentManager manager = getSupportFragmentManager();
                manager.popBackStack();
                manager.popBackStack();

                FragmentTransaction tr = manager.beginTransaction();
                tr.add(R.id.fragmentContainer, novi);
                tr.addToBackStack(null);
                tr.commit();
            }
        });

    }


}
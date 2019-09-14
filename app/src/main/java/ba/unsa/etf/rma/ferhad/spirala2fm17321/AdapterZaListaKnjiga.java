package ba.unsa.etf.rma.ferhad.spirala2fm17321;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class AdapterZaListaKnjigaAkt extends ArrayAdapter<Knjiga> {

    public AdapterZaListaKnjigaAkt(Context context, ArrayList<Knjiga> knjige) {
        super(context, R.layout.red_adaptera, knjige);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.red_adaptera, parent, false);

        Knjiga k = getItem(position);
        TextView eAutor = (TextView)customView.findViewById(R.id.eAutor);
        TextView eNaziv = (TextView)customView.findViewById(R.id.eNaziv);
        ImageView eNaslovna = (ImageView)customView.findViewById(R.id.eNaslovna);

        String autori = new String();
        for(int i = 0; i < k.getAutori().size(); i++)
            autori += k.getAutori().get(i).getImeiPrezime() + "\n";

        eAutor.setText(autori);
        eNaziv.setText(k.getNaziv());

        if(k.getNaslovna() != null)
            eNaslovna.setImageBitmap(k.getNaslovna());

        if(k.daLiJeSelektovana())
            customView.setBackgroundColor(0xffaabbed);

        return customView;
    }

}

package fr.joffre.tpe.joffrecourse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ActiviteAdapter extends BaseAdapter{
    // Une liste de personnes
    private List<ActivityHistorique> mListP;
    //Le contexte dans lequel est présent notre adapter
    private Context mContext;
    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;
    //Contient la liste des listeners
    private ArrayList<ActiviteAdapterListener> mListListener = new ArrayList<ActiviteAdapterListener>();

    public ActiviteAdapter(Context context, List<ActivityHistorique> aListP) {
        mContext = context;
        mListP = aListP;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListP.size();
    }

    @Override
    public Object getItem(int position) {
        return mListP.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem;
        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML
            layoutItem = (RelativeLayout) mInflater.inflate(R.layout.histo, parent, false);
        } else {
            layoutItem = (RelativeLayout) convertView;
        }
            //(2) : Récupération des TextView de notre layout
            TextView tv_Date = (TextView)layoutItem.findViewById(R.id.Date);
            TextView tv_Temps = (TextView)layoutItem.findViewById(R.id.Temps);
            TextView tv_Distance = (TextView)layoutItem.findViewById(R.id.Distance);

            //(3) : Renseignement des valeurs
            tv_Date.setText(mListP.get(position).getDate());
            int Valeur[] = Timer.convert((int) mListP.get(position).getTemps());
            String str = String.valueOf(Valeur[0])+"h"+String.valueOf(Valeur[1])+"m"+String.valueOf(Valeur[2])+"s";
            tv_Temps.setText(str);
            tv_Distance.setText(String.valueOf(mListP.get(position).getDistance()));

            layoutItem.setTag(position);
            layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Lorsque l'on clique sur le nom, on récupère la position de la "Personne"
                    Integer position = (Integer) v.getTag();

                    //On prévient les listeners qu'il y a eu un clic sur le TextView "TV_Nom".
                    sendListener(mListP.get(position), position);
                }
            });
            //On retourne l'item créé.
            return layoutItem;
    }

    /**
     * Pour ajouter un listener sur notre adapter
     */
    public void addListener(ActiviteAdapterListener aListener) {
        mListListener.add(aListener);
    }

    private void sendListener(ActivityHistorique item, int position) {
        for(int i = mListListener.size()-1; i >= 0; i--) {
            mListListener.get(i).onClickActivite(item, position);
        }
    }
    /**
     * Interface pour écouter les évènements sur le nom d'une personne
     */
    public interface ActiviteAdapterListener {
        public void onClickActivite(ActivityHistorique item, int position);
    }
}


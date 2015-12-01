package com.travismosley.armadafleetcommander;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.travismosley.armadafleetcommander.data.helpers.ArmadaDatabaseHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SquadronListFragment extends Fragment {

    private SquadronResultsAdapter mResultsAdapter;

    public SquadronListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View squadronView = inflater.inflate(R.layout.fragment_squadron_list, container, false);
        ListView squadronResultView = (ListView) squadronView.findViewById(R.id.listView_squadrons);

        ArmadaDatabaseHelper armadaDbHelper = new ArmadaDatabaseHelper(getActivity());
        try { armadaDbHelper.createDataBase();
        } catch (IOException e) { throw new Error("Error creating the database;"); }
        try {
            armadaDbHelper.openDataBase();
        } catch (SQLException e) { throw new Error("Error loading the database!");}

        SquadronResultsAdapter squadronAdapter = new SquadronResultsAdapter(getActivity(),
                                                                            armadaDbHelper.fetchSquadronTitles());
        squadronResultView.setAdapter(squadronAdapter);

        return squadronView;
    }

    private class SquadronResultsAdapter extends CursorAdapter {

        public SquadronResultsAdapter(Context context, Cursor cursor){
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.list_item_squadron, parent, false);
        }

        public void bindView(View view, Context context, Cursor cursor){

            // Set the squadron name
            TextView nameView = (TextView) view.findViewById(R.id.list_item_squadron_title);
            String nameText = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            nameView.setText(nameText);

            TextView classView = (TextView) view.findViewById(R.id.list_item_squadron_class);
            String classText = cursor.getString(cursor.getColumnIndexOrThrow("class_title"));
            classView.setText(classText);

            TextView hullView = (TextView) view.findViewById(R.id.list_item_squadron_hull);
            String hullText = cursor.getString(cursor.getColumnIndexOrThrow("hull"));
            hullView.setText(hullText);

            TextView speedView = (TextView) view.findViewById(R.id.list_item_squadron_speed);
            String speedText = cursor.getString(cursor.getColumnIndexOrThrow("speed"));
            speedView.setText(speedText);

            TextView pointsView = (TextView) view.findViewById(R.id.list_item_squadron_points);
            String pointsText = cursor.getString(cursor.getColumnIndexOrThrow("point_cost"));
            pointsView.setText(pointsText);


        }
    }
}

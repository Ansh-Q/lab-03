package com.example.listycitylab3;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    public interface EditCityDialogListener {
        void onCityEdited(int position, City updated);
    }

    private static final String ARG_CITY   = "arg_city";
    private static final String ARG_INDEX  = "arg_index";

    public static EditCityFragment newInstance(City city, int index) {
        EditCityFragment f = new EditCityFragment();
        Bundle b = new Bundle();
        b.putSerializable(ARG_CITY, city);
        b.putInt(ARG_INDEX, index);
        f.setArguments(b);
        return f;
    }

    private EditCityDialogListener listener;

    public void setListener(EditCityDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_edit_city, null, false);

        EditText cityEt = view.findViewById(R.id.edit_text_city_text);
        EditText provEt = view.findViewById(R.id.edit_text_province_text);

        Bundle args = getArguments();
        City city = null;
        int index = -1;
        if (args != null) {
            city = (City) args.getSerializable(ARG_CITY);
            index = args.getInt(ARG_INDEX, -1);
        }
        if (city != null) {
            cityEt.setText(city.getName());
            provEt.setText(city.getProvince());
        }

        final int pos = index;
        final City original = city;

        return new AlertDialog.Builder(requireContext())
                .setTitle("Edit City")
                .setView(view)
                .setNegativeButton("Cancel", (d, w) -> d.dismiss())
                .setPositiveButton("Save", (d, w) -> {
                    String newName = cityEt.getText().toString().trim();
                    String newProv = provEt.getText().toString().trim();
                    if (listener != null && original != null && pos >= 0) {
                        City updated = new City(
                                newName.isEmpty() ? original.getName() : newName,
                                newProv.isEmpty() ? original.getProvince() : newProv
                        );
                        listener.onCityEdited(pos, updated);
                    }
                })
                .create();
    }
}

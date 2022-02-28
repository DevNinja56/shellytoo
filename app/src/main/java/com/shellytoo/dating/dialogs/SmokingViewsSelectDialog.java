package com.shellytoo.dating.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.shellytoo.dating.R;
import com.shellytoo.dating.constants.Constants;

public class SmokingViewsSelectDialog extends DialogFragment implements Constants {

    private int position = 0;

    /** Declaring the interface, to invoke a callback function in the implementing activity class */
    AlertPositiveListener alertPositiveListener;

    /** An interface to be implemented in the hosting activity for "OK" button click listener */
    public interface AlertPositiveListener {

        void onSmokingViewsSelect(int position);
    }

    /** This is a callback method executed when this fragment is attached to an activity.
     *  This function ensures that, the hosting activity implements the interface AlertPositiveListener
     * */
    public void onAttach(android.app.Activity activity) {

        super.onAttach(activity);

        try {

            alertPositiveListener = (AlertPositiveListener) activity;

        } catch(ClassCastException e){

            // The hosting activity does not implemented the interface AlertPositiveListener
            throw new ClassCastException(activity.toString() + " must implement AlertPositiveListener");
        }
    }

    /** This is the OK button listener for the alert dialog,
     *  which in turn invokes the method onPositiveClick(position)
     *  of the hosting activity which is supposed to implement it
     */
    OnClickListener positiveListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            AlertDialog alert = (AlertDialog)dialog;
            int position = alert.getListView().getCheckedItemPosition();

            alertPositiveListener.onSmokingViewsSelect(position);
        }
    };

    /** This is a callback method which will be executed
     *  on creating this fragment
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String[] smoking_views_strings = new String[] {

                getText(R.string.smoking_views_0).toString(),
                getText(R.string.smoking_views_1).toString(),
                getText(R.string.smoking_views_2).toString(),
                getText(R.string.smoking_views_3).toString(),
                getText(R.string.smoking_views_4).toString(),
                getText(R.string.smoking_views_5).toString(),

        };

        /** Getting the arguments passed to this fragment */
        Bundle bundle = getArguments();

        position = bundle.getInt("position");

        /** Creating a builder for the alert dialog window */
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

        /** Setting a title for the window */
        b.setTitle(getText(R.string.account_smoking_views));

        /** Setting items to the alert dialog */
        b.setSingleChoiceItems(smoking_views_strings, position, null);

        /** Setting a positive button and its listener */
        b.setPositiveButton(getText(R.string.action_ok), positiveListener);

        /** Setting a positive button and its listener */
        b.setNegativeButton(getText(R.string.action_cancel), null);

        /** Creating the alert dialog window using the builder class */
        AlertDialog d = b.create();

        /** Return the alert dialog window */
        return d;
    }
}
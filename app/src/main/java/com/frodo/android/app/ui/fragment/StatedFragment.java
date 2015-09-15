package com.frodo.android.app.ui.fragment;

import android.os.Bundle;

import com.frodo.android.app.core.UIView;
import com.frodo.android.app.framework.controller.IModel;

/**
 * Created by frodo on 2015/9/15.
 */
public abstract class StatedFragment<V extends UIView, M extends IModel> extends AbstractBaseFragment<V, M> {
    private Bundle savedState;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Restore State Here
        if (!restoreStateFromArguments()) {
            // First Time, Initialize something here
            onFirstTimeLaunched();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save State Here
        saveStateToArguments();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Save State Here
        saveStateToArguments();
    }

    protected void onFirstTimeLaunched() {
    }

    /**
     * Like this : state.putString(text, tv1.getText().toString());
     *
     * @param outState
     */
    protected void onSaveState(Bundle outState) {
    }

    /**
     * Like this: tv1.setText(savedState.getString(text));
     *
     * @param savedInstanceState
     */
    protected void onRestoreState(Bundle savedInstanceState) {
    }

    private void saveStateToArguments() {
        if (getView() != null)
            savedState = saveState();
        if (savedState != null) {
            Bundle b = getArguments();
            b.putBundle(tag(), savedState);
        }
    }

    private boolean restoreStateFromArguments() {
        Bundle b = getArguments();
        savedState = b.getBundle(tag());
        if (savedState != null) {
            restoreState();
            return true;
        }

        return false;
    }

    private void restoreState() {
        if (savedState != null) {
            onRestoreState(savedState);
        }
    }

    private Bundle saveState() {
        Bundle state = new Bundle();
        onSaveState(state);
        return state;
    }
}

package com.android.app.ui;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * A class that manages a stack of {@link Fragment}s in a single container.
 * Created by frodo on 2015/1/27.
 */
public class FragmentStack {

    private static final String STATE_STACK = "net.simonvt.util.FragmentStack.stack";
    private LinkedList<Fragment> stack = new LinkedList<Fragment>();
    private Set<String> topLevelTags = new HashSet<String>();
    private Activity activity;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private int containerId;
    private Callback callback;
    private final Runnable execPendingTransactions = new Runnable() {
        @Override
        public void run() {
            if (fragmentTransaction != null) {
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
                fragmentTransaction = null;

                dispatchOnStackChangedEvent();
            }
        }
    };
    private Handler handler;
    private int enterAnimation;
    private int exitAnimation;
    private int popStackEnterAnimation;
    private int popStackExitAnimation;

    private FragmentStack(FragmentActivity activity, int containerId, Callback callback) {
        this.activity = activity;
        fragmentManager = activity.getSupportFragmentManager();
        this.containerId = containerId;
        this.callback = callback;

        handler = new Handler();
    }

    /**
     * Create an instance for a specific container.
     */
    public static FragmentStack forContainer(FragmentActivity activity, int containerId, Callback callback) {
        return new FragmentStack(activity, containerId, callback);
    }

    /**
     * Removes all added fragments and clears the stack.
     */
    public void destroy() {
        ensureTransaction();
        fragmentTransaction.setCustomAnimations(enterAnimation, exitAnimation);

        final Fragment topFragment = stack.peekFirst();
        for (Fragment f : stack) {
            if (f != topFragment) {
                removeFragment(f);
            }
        }
        stack.clear();

        for (String tag : topLevelTags) {
            removeFragment(fragmentManager.findFragmentByTag(tag));
        }

        fragmentTransaction.commit();
        fragmentTransaction = null;
    }

    public void saveState(Bundle outState) {
        executePendingTransactions();

        final int stackSize = stack.size();
        String[] stackTags = new String[stackSize];

        int i = 0;
        for (Fragment f : stack) {
            stackTags[i++] = f.getTag();
        }

        outState.putStringArray(STATE_STACK, stackTags);
    }

    public void restoreState(Bundle state) {
        String[] stackTags = state.getStringArray(STATE_STACK);
        for (String tag : stackTags) {
            Fragment f = fragmentManager.findFragmentByTag(tag);
            stack.add(f);
        }
        dispatchOnStackChangedEvent();
    }

    public int size() {
        return stack.size();
    }

    public Fragment peek() {
        return stack.peekLast();
    }

    /**
     * Replaces the entire stack with this fragment.
     */
    public void replace(Class<?> fragment, String tag) {
        replace(fragment, tag, null);
    }

    /**
     * Replaces the entire stack with this fragment.
     *
     * @param args Arguments to be set on the fragment using
     *             {@link Fragment#setArguments(android.os.Bundle)}.
     */
    public void replace(Class<?> fragment, String tag, Bundle args) {
        Fragment first = stack.peekFirst();
        if (first != null && tag.equals(first.getTag())) {
            if (stack.size() > 1) {
                ensureTransaction();
                fragmentTransaction.setCustomAnimations(popStackEnterAnimation, popStackExitAnimation);
                while (stack.size() > 1) {
                    removeFragment(stack.pollLast());
                }

                attachFragment(stack.peek(), tag);
            }
            return;
        }

        Fragment f = fragmentManager.findFragmentByTag(tag);
        if (f == null) {
            f = Fragment.instantiate(activity, fragment.getName(), args);
        }

        ensureTransaction();
        fragmentTransaction.setCustomAnimations(enterAnimation, exitAnimation);
        clear();
        attachFragment(f, tag);
        stack.add(f);

        topLevelTags.add(tag);
    }

    public void push(Class<?> fragment, String tag) {
        push(fragment, tag, null);
    }

    /**
     * Adds a new fragment to the stack and displays it.
     */
    public void push(Class<?> fragment, String tag, Bundle args) {
        ensureTransaction();
        fragmentTransaction.setCustomAnimations(enterAnimation, exitAnimation);
        detachTop();

        Fragment f = fragmentManager.findFragmentByTag(tag);

        if (f == null) {
            f = Fragment.instantiate(activity, fragment.getName(), args);
        }

        attachFragment(f, tag);
        stack.add(f);
    }

    /**
     * Removes the fragment at the top of the stack and displays the previous
     * one. This will not do anything if there is only one fragment in the
     * stack.
     *
     * @return Whether a transaction has been enqueued.
     */
    public boolean pop() {
        return pop(false);
    }

    /**
     * Removes the fragment at the top of the stack and displays the previous
     * one. This will not do anything if there is only one fragment in the
     * stack.
     *
     * @param commit Whether the transaction should be committed.
     *
     * @return Whether a transaction has been enqueued.
     */
    public boolean pop(boolean commit) {
        if (stack.size() > 1) {
            ensureTransaction();
            fragmentTransaction.setCustomAnimations(popStackEnterAnimation, popStackExitAnimation);
            removeFragment(stack.pollLast());
            Fragment f = stack.peekLast();
            attachFragment(f, f.getTag());

            if (commit) {
                commit();
            }

            return true;
        }

        return false;
    }

    private void detachTop() {
        Fragment f = stack.peekLast();
        detachFragment(f);
    }

    public void clear() {
        Fragment first = stack.peekFirst();
        for (Fragment f : stack) {
            if (f == first) {
                detachFragment(f);
            } else {
                removeFragment(f);
            }
        }

        stack.clear();
    }

    private void dispatchOnStackChangedEvent() {
        if (callback != null && stack.size() > 0) {
            callback.onStackChanged(stack.size(), stack.peekLast());
        }
    }

    private FragmentTransaction ensureTransaction() {
        if (fragmentTransaction == null) {
            fragmentTransaction = fragmentManager.beginTransaction();
        }
        handler.removeCallbacks(execPendingTransactions);
        return fragmentTransaction;
    }

    private void attachFragment(Fragment fragment, String tag) {
        if (fragment != null) {
            if (fragment.isDetached()) {
                ensureTransaction();

                fragmentTransaction.attach(fragment);
            } else if (!fragment.isAdded()) {
                ensureTransaction();

                fragmentTransaction.add(containerId, fragment, tag);
            }
        }
    }

    private void detachFragment(Fragment fragment) {
        if (fragment != null && !fragment.isDetached()) {
            ensureTransaction();
            fragmentTransaction.detach(fragment);
        }
    }

    private void removeFragment(Fragment fragment) {
        if (fragment != null && fragment.isAdded()) {
            ensureTransaction();
            fragmentTransaction.remove(fragment);
        }
    }

    public void setDefaultAnimation(int enter, int exit, int popEnter, int popExit) {
        enterAnimation = enter;
        exitAnimation = exit;
        popStackEnterAnimation = popEnter;
        popStackExitAnimation = popExit;
    }

    /**
     * Commit pending transactions. This will be posted, not executed
     * immediately.
     *
     * @return Whether there were any transactions to commit.
     */
    public boolean commit() {
        if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
            handler.removeCallbacks(execPendingTransactions);
            handler.post(execPendingTransactions);
            return true;
        }

        return false;
    }

    public boolean executePendingTransactions() {
        if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
            handler.removeCallbacks(execPendingTransactions);
            fragmentTransaction.commit();
            fragmentTransaction = null;
            boolean result = fragmentManager.executePendingTransactions();
            if (result) {
                dispatchOnStackChangedEvent();
                return true;
            }
        }

        return false;
    }

    public interface Callback {
        void onStackChanged(int stackSize, Fragment topFragment);
    }
}


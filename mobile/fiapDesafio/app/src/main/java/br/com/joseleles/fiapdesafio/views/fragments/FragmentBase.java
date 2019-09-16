package br.com.joseleles.fiapdesafio.views.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.joseleles.fiapdesafio.R;

public abstract class FragmentBase extends Fragment {

    String tag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setTagOfFragment();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void redirect(FragmentBase fragmentBase){
        if(getActivity() !=null && getContext()!=null && isAdded()){
            FragmentManager fm = getActivity().getSupportFragmentManager();
            if(fm != null){
                if(fm.getBackStackEntryCount() < 3){
                    fm.beginTransaction()
                            .replace(R.id.fundo_para_preencher,fragmentBase)
                            .addToBackStack(fragmentBase.getClass().getSimpleName())
                            .commit();
                }else{
                    fm.popBackStack();
                    fm.beginTransaction()
                            .replace(R.id.fundo_para_preencher,fragmentBase)
                            .addToBackStack(fragmentBase.getClass().getSimpleName())
                            .commit();
                }
            }
        }
    }

    public abstract void setTagOfFragment();

    @Nullable
    public String getTagFragment() {
        return tag;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BundleTags.TAG_FRAGMENT, tag);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
            tag = savedInstanceState.getString(BundleTags.TAG_FRAGMENT);
        }
    }

    public void safeShowAlertDialog(String title, String message){
        if(getContext()!=null && !isRemoving() && isAdded()){
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle(title);
            alert.setMessage(message);
            alert.setPositiveButton(getContext().getResources().getString(R.string.ok), (dialog,escolha)->{
                dialog.dismiss();
            });
            alert.show();
        }
    }

    public void popFragment(){
        if(getActivity() !=null && getContext()!=null && isAdded()){
            FragmentManager fm = getActivity().getSupportFragmentManager();
            if(fm != null){
                if(fm.getBackStackEntryCount() > 1){
                    fm.popBackStack();
                }
            }
        }
    }
}

package work.newproject.asus.as.swadeshiebazaar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import work.newproject.asus.as.swadeshiebazaar.adapter.ChildItemAdapter;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.DropDownCatMOdel;


public class ChildCategoryFrag extends Fragment {

    List<DropDownCatMOdel.ChildCat> childCat;
    RecyclerView recyclerView;
    ChildItemAdapter childItemAdapter;


    public ChildCategoryFrag() {
        // Required empty public constructor
    }
    public ChildCategoryFrag(List<DropDownCatMOdel.ChildCat> childCat){
        this.childCat = childCat;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_child_category,container,false);
        recyclerView = view.findViewById(R.id.childFrag);

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        childItemAdapter = new ChildItemAdapter(childCat, getContext());
        recyclerView.setAdapter(childItemAdapter);

        return  view;
    }
}
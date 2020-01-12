package com.expense.expenseadmin.view.fragments.requests;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

import com.expense.expenseadmin.R;
import com.expense.expenseadmin.pojo.Model.PlaceModel;
import com.expense.expenseadmin.view.activities.Home.HomeActivity;
import com.expense.expenseadmin.view.adapters.RequestsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment implements RequestsView{

    @BindView(R.id.fragment_requests_view_animator)
    ViewAnimator mViewAnimator;

    @BindView(R.id.requests_fragment_rv)
    RecyclerView mRecyclerView;

    private ArrayList<PlaceModel> requests;

    private Context mCurrent;
    private RequestsAdapter mRequestsAdapter;
    private RequestsPresenter mPresenter;
    private RequestToHomeCallback mHomeCallback;

    public RequestsFragment() {
        // Required empty public constructor
    }

    public RequestsFragment(RequestToHomeCallback callback) {
        mHomeCallback = callback;
    }
    public static RequestsFragment newInstance() {

        return new RequestsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View v) {
        try {
            ButterKnife.bind(this, v);
            mCurrent = getActivity();
            mPresenter = new RequestsPresenter(this,mCurrent);
            mViewAnimator.setDisplayedChild(0);
            initRecyclerView();

            mPresenter.readRequestsFromFirestore();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView() {
        try{
            requests = new ArrayList<>();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mCurrent));
            mRequestsAdapter = new RequestsAdapter(mCurrent,requests);
            mRecyclerView.setAdapter(mRequestsAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onReadRequests(ArrayList<PlaceModel> requests) {
        try {
            if (requests != null) {
                this.requests = new ArrayList<>();
                this.requests.addAll(requests);

                ((HomeActivity) mCurrent).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRequestsAdapter.notifyDataSetChanged();
                        mRequestsAdapter.changeData(requests);
                        mViewAnimator.setDisplayedChild(2);
                        mHomeCallback.setRequestsCount(requests.size());
                    }
                });
            } else {
                mViewAnimator.setDisplayedChild(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

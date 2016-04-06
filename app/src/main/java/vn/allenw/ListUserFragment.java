package vn.allenw;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.allenw.adapter.ListUserAdapter;
import vn.allenw.model.ListUserHelper;
import vn.allenw.model.User;
import vn.allenw.presenter.ListUserPresenter;
import vn.allenw.view.ListUserView;

public class ListUserFragment extends Fragment implements ListUserView, View.OnClickListener {
    private final String EXTRA_USER_LIST = "extra_user_list";
    //    private final String url = "https://api.myjson.com/bins/53x82"; // PJ null
    private final String url = "https://api.myjson.com/bins/3w62s"; // lot of data
//    private final String url = "https://api.myjson.com/bins/3t3fg"; // no data

    private ArrayList<User> listUser;
    private TextView tvMessage;
    private ProgressBar loading;
    private RecyclerView recyclerView;
    private Button btReload;
    private ListUserAdapter adapter;


    // MVP
    private ListUserPresenter presenter;
    private ListUserHelper listUserHelper;


    public ListUserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            listUser = savedInstanceState.getParcelableArrayList(EXTRA_USER_LIST);
        }
        if (presenter == null) {
            Log.d("PJ3", "Presenter Null");
            if (listUserHelper == null)
                listUserHelper = new ListUserHelper(url);
            listUserHelper.setList(listUser);
            presenter = new ListUserPresenter(this, listUserHelper);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_user_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvMessage = (TextView) view.findViewById(R.id.message);
        loading = (ProgressBar) view.findViewById(R.id.loading);
        btReload = (Button) view.findViewById(R.id.reload);
        btReload.setOnClickListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (presenter != null) presenter.getData(true); // Use cache
    }

    @Override
    public void reload() {
        loading.setVisibility(View.VISIBLE);
        tvMessage.setVisibility(View.GONE);
        btReload.setVisibility(View.GONE);
    }

    @Override
    public void showNoData() {
        loading.setVisibility(View.GONE);
        tvMessage.setVisibility(View.VISIBLE);
        btReload.setVisibility(View.VISIBLE);
        tvMessage.setText("No Data");
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        loading.setVisibility(View.GONE);
        tvMessage.setVisibility(View.VISIBLE);
        tvMessage.setText("Load Data Error");
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void displayListuser(List<User> listUser) {
        this.listUser = (ArrayList<User>) listUser;
        loading.setVisibility(View.GONE);
        tvMessage.setVisibility(View.GONE);
        adapter = new ListUserAdapter(listUser);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reload:
                presenter.reload();
                break;
        }
    }

    @Override
    public void onPause() {
        Log.d("PJ3", "On Pause");
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.d("PJ3", "On Destroy");
        super.onDestroy();
        presenter = null;
        listUserHelper = null;
        adapter = null;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("PJ3", "On save Instance State");
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_USER_LIST, listUser);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reload:
                presenter.getData(false); // Not use cache
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

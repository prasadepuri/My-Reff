package me.prasad.myreff;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Politics extends Fragment {
    public RecyclerView recyclerView;
    public ArrayList<AppData> appData = new ArrayList<>();
    //public ArrayList<NewData> newData=new ArrayList<>();
    RecyclerView.Adapter radapter;
    RecyclerView.LayoutManager layoutManager;
    Context contextt;
    public MyReffRoomRepository myReffRoomRepository;
    public Politics() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_politics, container, false);
        myReffRoomRepository = new MyReffRoomRepository(getActivity().getApplication());
        recyclerView = rootview.findViewById(R.id.politicsrecycleView);
        recyclerView.setHasFixedSize(true);
        appData.clear();
        final SwipeRefreshLayout pullToRefresh =rootview.findViewById(R.id.pullToRefreshOfPolitics);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int i=0;
                i++;
                Toast.makeText(getContext(),"pull count"+i,Toast.LENGTH_SHORT).show();
                // your code
                appData.clear();
                loadContentofPolitics();
                pullToRefresh.setRefreshing(false);
            }
        });
        int clicks=0;
        clicks++;
        if(clicks<=1) {
            appData.clear();
            loadContentofPolitics();
        }
        return rootview;
    }


    private void loadContentofPolitics(){
        readFromSqLiteDataBase();
    }
    public class GetPoliticsTask extends AsyncTask<Void, Void, List<MyReff>> {
        @Override
        protected List<MyReff> doInBackground(Void... url) {
            return myReffRoomRepository.getPoliticsData();

        }
    }
    private void readFromSqLiteDataBase() {
        List<MyReff> myReferences = new ArrayList<MyReff>();
        List<HashMap<String, Object>> myReferenceList = new ArrayList<>();
        try {
            myReferences = new GetPoliticsTask().execute().get();
            for (MyReff ref : myReferences) {
                String title = ref.getTitle();
                String link = ref.getUrl();
                String category = ref.getCategory();
                String date = (String) converToNormalTime(ref.getTimestamp());
                long timestamp = ref.getTimestamp();
                // Toast.makeText(getContext(), title + "sqlite", Toast.LENGTH_LONG).show();
                String imageurl = ref.getImageurl();
                /*Write data of sqlite database into RecycleView data content*/
                writeintoAppData(title, link, category, date, timestamp, imageurl);
            }


            //Finally we loaded the data into recycle view***


            loadRecycleView();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //return myReferenceList;
    }

    private void loadRecycleView() {
        layoutManager = new LinearLayoutManager(getActivity());
        radapter = new PoliticsAdapter(appData, getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(radapter);

    }

    private void writeintoAppData(String title, String link, String category, String date, long timestamp, String imageurl) {
        appData.add(new AppData(title, link, date, category, imageurl, timestamp));
    }

    private Object converToNormalTime(long timestamp) {
        //convert seconds to milliseconds
        Date date = new Date(timestamp);
        // format of the date
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        jdf.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        //jdf.setTimeZone(TimeZone.getDefault());
        String java_date = jdf.format(date);
        System.out.println("\n" + java_date + "\n");
        return java_date;
    }

}

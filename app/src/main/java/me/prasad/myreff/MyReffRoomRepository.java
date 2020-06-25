package me.prasad.myreff;

import android.app.Application;

import java.util.List;

public class MyReffRoomRepository {
    public List<MyReff> myReff;
    public MyReffDao myReffDao;

    MyReffRoomRepository(Application application)
    {
        MyReffRoomDatabase db= MyReffRoomDatabase.getDatabase(application);
        myReffDao=db.myReffDao();

    }
    void insert(final MyReff myReff)
    {
        MyReffRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                myReffDao.insert(myReff);
            }
        });
    }
    List<MyReff> getAllData()
    {
        myReff=myReffDao.getAllData();
        return myReff;
    }
    List<MyReff> getPoliticsData()
    {
        myReff=myReffDao.getPoliticsData();
        return myReff;
    }
}

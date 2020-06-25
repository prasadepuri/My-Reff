package me.prasad.myreff;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyReffDao {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    public void insert(MyReff myReff);

    @Query("select * from news_table order by timestamp desc")
    List<MyReff> getAllData();

    @Query("select * from news_table where category = 'politics' order by timestamp desc")
    List<MyReff> getPoliticsData();

}

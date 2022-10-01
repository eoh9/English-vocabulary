package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
//엑세스 오브젝트이다.
public interface UserProfileDao {
    //USERPROFILEDAO객체를 만들고
    @Insert
    void insert(UserProfile userProfile);
    //삽입 API

    @Update
    void update(UserProfile userProfile);
    //갱신 API

    @Delete
    void delete(UserProfile userProfile);
    //DELETE API

    @Query("SELECT * FROM UserProfile")
        //검색 API
    List<UserProfile> getAll();
    //전체목록을 얻어오기위한것
}
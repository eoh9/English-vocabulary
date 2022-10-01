package com.example.myapplication;


import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {UserProfile.class}, version = 1)
//USERPROFILE이라고 하는 엔티티가 있다.버전은 새롭게 업뎃하고자하는 형태가 다를떄 여부확인할떄 쓰는거다.
//일단 기본은 VERSION 1
public abstract class UserProfileDatabase extends RoomDatabase {
    public abstract UserProfileDao getUserProfileDao();
}
package com.example.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
//룸을 통해 사용하기 위해서 ANNOTATION ENTITY 를 추가시킨다.
//ENTITY란 데베에서 테이블에 해당하는거다 , 이 엔티티의 형태의 데이터가 여러개가 저장된다.

public class UserProfile {
    @PrimaryKey(autoGenerate = true)
    //primary키로 지정된 id로 그 정보를 수정삭제등을함,autoGenerate=자동으로id생성
    public  int id;
    public String name;
    public String phone;
    public String address;
    //NAME,PHONE,ADDRESS 를 만든다, 저장할수 있는 객체를 만들기 위해 .

}
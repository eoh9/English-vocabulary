package com.example.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.myapplication.databinding.ActivityWordBinding;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    public ActivityWordBinding binding;
    public UserProfileDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //oncreate 에서 GETROOT 뷰바인딩을 이용해서 화면을 구성한다.


        db = Room.databaseBuilder(this,UserProfileDatabase.class,"userProfile").allowMainThreadQueries().build();
        //db라는 Userprofiledatabase 객체를 만든다.
        fetchUserProfileList();
        //fetchUserprofilelist를 함으로써 앱이 실행됬을떄 기존에 저장되있는걸 화면에 표시하는 로직이다 .

    }

    public void fetchUserProfileList(){
        List<UserProfile> userProfileList = db.getUserProfileDao().getAll();
        //db안에 있는것이 모두 목록으로 전환되서 나오게된다.
        String userListText = "내단어 목록";
        for(UserProfile userProfile : userProfileList){
            userListText += "\n" + userProfile.id + "," + userProfile.name + "," + userProfile.phone + "," + userProfile.address;
        }
        //textview 에 출력하기 위해 arraylist만큼 루프를 돌면서 계속 줄바꿈을 하여 값들을 텍스트스트림에 저장하고 최종적으로 USERLIST에 SET해준다.

        binding.userList.setText(userListText);
        //최종적으로 settext를한다.
    }

    public void addUserProfile(View view){
        //추가 버튼을 눌렀을떄 실행이 될 ADDUSERPROFILE을 만든다.
        //여기서 ROOM API를 사용하기 위해서 ROOM API를 프로젝트에 포함시켜야한다.


        UserProfile userProfile = new UserProfile();
        userProfile.name = binding.name.getText().toString();
        userProfile.phone = binding.phone.getText().toString();
        userProfile.address = binding.address.getText().toString();
        //binding을 통해서 값을 가지고온다.,그 것을 객체에 설정을한다.

        db.getUserProfileDao().insert(userProfile);
        fetchUserProfileList();
        //계속 추가되게끔 해준다.
    }

}
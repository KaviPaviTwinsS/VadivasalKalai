package pasu.vadivasal;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pasu.vadivasal.News.NewsDetailActivity;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.tournament.TournamentMatchesActivity;
import pasu.vadivasal.videopackage.VideoActivityMain;

/**
 * Created by Admin on 06-01-2018.
 */

public class NotificationReceiverActivity extends AppCompatActivity {
    private int toOpen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
            toOpen =Integer.parseInt(i.getStringExtra("open"));
        System.out.println("MyFirebaseMsgServiceNotify"+"toopen"+toOpen+"__"+i.getStringExtra("open"));
        switch (toOpen) {
            case Appconstants.OPEN_DASHBOARD:
                Intent mainActivity = new Intent(NotificationReceiverActivity.this, MainActivity.class);
                if (i.getStringExtra("message") != null)
                    mainActivity.putExtra("message", i.getStringExtra("message"));
                startActivity(mainActivity);finish();
                break;
//            case Appconstants.OPEN_NEWS:
//                Intent mainActivity=new Intent(NotificationReceiverActivity.this,MainActivity.class);
//                if(i.getStringExtra("alertMessage")!=null)
//                    mainActivity.putExtra("alertMessage",i.getStringExtra("alertMessage"));
//                break;
//            case Appconstants.OPEN_TOURNAMENT:
//                Intent mainActivity=new Intent(NotificationReceiverActivity.this,MainActivity.class);
//                if(i.getStringExtra("alertMessage")!=null)
//                    mainActivity.putExtra("alertMessage",i.getStringExtra("alertMessage"));
//                break; case Appconstants.OPEN_TRENDING:
//                Intent mainActivity=new Intent(NotificationReceiverActivity.this,MainActivity.class);
//                if(i.getStringExtra("alertMessage")!=null)
//                    mainActivity.putExtra("alertMessage",i.getStringExtra("alertMessage"));
//                break;
            case Appconstants.OPEN_SINGLE_IMAGE:
                Intent shareImage = new Intent(NotificationReceiverActivity.this, OpenShareContent.class);
                if (i.getStringExtra("id") != null)
                    shareImage.putExtra("id", i.getStringExtra("id"));
                startActivity(shareImage);finish();
                break;
            case Appconstants.OPEN_SINGLE_NEWS:
                Intent Newscontent = new Intent(NotificationReceiverActivity.this, NewsDetailActivity.class);
                if (i.getStringExtra("id") != null)
                    Newscontent.putExtra("id", i.getStringExtra("id"));
                startActivity(Newscontent);finish();
                break;
            case Appconstants.OPEN_SINGLE_VIDEO:
                Intent videos = new Intent(NotificationReceiverActivity.this, VideoActivityMain.class);
                if (i.getStringExtra("id") != null)
                    videos.putExtra("id", i.getStringExtra("id"));
                startActivity(videos);finish();
                break;
            case Appconstants.OPEN_SINGLE_TOURNAMENT:
                Intent tournament = new Intent(NotificationReceiverActivity.this, TournamentMatchesActivity.class);
                if (i.getStringExtra("id") != null) {
                    tournament.putExtra(Appconstants.TourID, i.getStringExtra("id"));
                    //tournament.putExtra("about", i.getStringExtra("about"));
                }
                startActivity(tournament);finish();
                break;


        }
    }
}

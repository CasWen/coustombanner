package com.example.coustombanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.coustombanner.view.PhotoCarouselView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PhotoCarouselView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.CarouselView);
        setImgageData();
    }

    private void setImgageData(){
        List<String> list =new ArrayList<>();
//        list.add("https://foreo-app.oss-cn-beijing.aliyuncs.com/c3012bac-083b-45ae-9812-9fc2549a18d7.jpg");
//        list.add("https://foreo-app.oss-cn-beijing.aliyuncs.com/ba891021-b8bf-4b63-8c6c-50ce7e34b4b7.jpg");
//        list.add("https://upload-images.jianshu.io/upload_images/2333435-f6eb03c1a01f19e5.jpg");

        list.add("http://img4.makepolo.net/img4/520/055/100017252055_14510363639579.jpg");
        list.add("http://b-ssl.duitang.com/uploads/item/201805/08/20180508232258_cLn3j.jpeg");
        list.add("http://b-ssl.duitang.com/uploads/item/201801/26/20180126002130_3GUsn.jpeg");
        list.add("http://b-ssl.duitang.com/uploads/blog/201410/31/20141031232244_5YanA.thumb.1600_0.jpeg");
        list.add("http://b-ssl.duitang.com/uploads/item/201806/26/20180626224415_4SPG3.jpeg");
        list.add("http://img1.doubanio.com/pview/event_poster/raw/public/093afbefbaf2b0a.jpg");
        list.add("http://pic.rmb.bdstatic.com/583157d9ad66273931d12ee0d4003c32.jpeg");
        list.add("http://i3.sinaimg.cn/ent/v/2010-02-11/U4175P28T3D2874848F326DT20100211204411.JPG");
        list.add("http://img.bimg.126.net/photo/oWe6pqgG0xWoNFMdBgzRdQ==/5784310771404597029.jpg");
        list.add("http://s2.lvjs.com.cn/guide/uploadfile/2013/0523/201305231743436428.jpg");
        list.add("http://d167.g03.dbankcloud.com/dl/huafans/pic/2018/05/18/18e8217f676448a615a89038015245f8_IMG_20180518_181134.jpg?mode=download");
        list.add("http://pic.rmb.bdstatic.com/2200fad43a6a6e6103a36a9249799145.jpeg");
        list.add("http://img1.imgtn.bdimg.com/it/u=2229408021,745737892&fm=26&gp=0.jpg");
        list.add("http://5b0988e595225.cdn.sohucs.com/images/20180428/ab0360d20da641b89b35db86d55f4412.jpeg");
        list.add("http://photocdn.sohu.com/20120410/Img340176729.jpg");
        list.add("http://www.cnblogs.com/images/cnblogs_com/huangxiaotao/1297427/o_c3687d0802946fb016cd6d2124a85ffb.jpg");
        list.add("http://www.epiaogo.com/data/20120725134138_9547.jpg");
        list.add("http://attachments.gfan.com/forum/attachments2/day_120105/1201051738c8bb221693efaeaf.jpg");
        list.add("http://img6.ph.126.net/knqvr0ZHXUfNz632IQXHeA==/1304636517070913676.jpg");
        list.add("http://b-ssl.duitang.com/uploads/item/201805/08/20180508231219_X8r5R.jpeg");
        list.add("http://picture.ca800.com/picture/201512/01/201512011332140773.jpg");

        view.setImageRes(list);//设置数据源
        view.setCarouselLayout();//设置适配器
        view.setIsTouchScroll(true);//设置不能手动滑动
        view.startPhotoCarousel();

        view.setOnItemClickLinsener(new PhotoCarouselView.OnItemClickLinsener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(MainActivity.this, "huadong a ", Toast.LENGTH_LONG).show();
            }
        });
    }
}

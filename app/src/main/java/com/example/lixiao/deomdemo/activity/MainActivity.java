package com.example.lixiao.deomdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lixiao.deomdemo.R;
import com.example.lixiao.deomdemo.adapter.ListAdapter;
import com.example.lixiao.deomdemo.bean.DataBean;
import com.example.lixiao.deomdemo.view.SideBar;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mLv;// 显示所有数据的ListView
    private SideBar mIndexes;// 右边的索引条
    private TextView mTvIndexviewer;// 位于屏幕中间的当前选中的索引的放大
    private List<DataBean> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mLv = (ListView) findViewById(R.id.lv);
        mIndexes = (SideBar) findViewById(R.id.indexes);
        mTvIndexviewer = (TextView) findViewById(R.id.tv_indexviewer);
        //给SideBar设置文字
        mIndexes.setIndexViewer(mTvIndexviewer);
    }
    private void initData() {
        // 初始化存放所有信息的List并添加所有数据
         dataList = new ArrayList<DataBean>();
         String[] names = getResources().getStringArray(R.array.names);
         for (String name : names) {
             dataList.add(new DataBean(name, getPinYinHeadChar(name)));
         }
         // 对dataList进行排序：根据每条数据的indexName（字符串）属性进行排序
         Collections.sort(dataList, new Comparator<DataBean>() {
             @Override
             public int compare(DataBean model1, DataBean model2) {
                 return String.valueOf(model1.getIndexName()).compareTo(String.valueOf(model2.getIndexName()));
             }
         });
         ListAdapter adapter = new ListAdapter(dataList,MainActivity.this);
         mLv.setAdapter(adapter);
    }
    /**
     * 返回中文的首字母（大写字母），如果str不是汉字，则返回 “#”
     */
    private char getPinYinHeadChar(String str) {
         char result = str.charAt(0);

         String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(result);
         if (pinyinArray != null) {
             result = pinyinArray[0].charAt(0);
         }
         if (!(result >= 'A' && result <= 'Z' || result >= 'a' && result <= 'z')) {
             result = '#';
         }
         return String.valueOf(result).toUpperCase().charAt(0);
    }

    private void initEvent() {

        // 回调接口将ListView滑动到选中的索引指向的第一条数据
         // 如果选中的索引没有对应任何数据，则指向这个索引上面的最近的有数据的索引
        mIndexes.setOnIndexChoiceChangedListener(new SideBar.OnIndexChoiceChangedListener() {
             @Override
              public void onIndexChoiceChanged(String s) {
                 char indexName = s.charAt(0);
                 int lastIndex = 0;
                 for (int i = 1; i < dataList.size(); i++) {
                     char currentIndexName = dataList.get(i).getIndexName();
                     if (currentIndexName >= indexName) {
                         if (currentIndexName == indexName) {
                             mLv.setSelection(i);
                             if (currentIndexName == '#') {
                                 mLv.setSelection(0);
                             }
                         } else {
                             mLv.setSelection(lastIndex);
                         }
                         break;
                     }
                     if (dataList.get(i - 1).getIndexName() != currentIndexName) {
                         lastIndex = i;
                     }
                 }
             }
         });
    }


}

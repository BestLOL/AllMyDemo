package com.qiuyi.cn.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.qiuyi.cn.retrofit.bean.Translation;
import com.qiuyi.cn.retrofit.bean.Translation1;
import com.qiuyi.cn.retrofit.request.GetRequest_Interface;
import com.qiuyi.cn.retrofit.request.PostRequest_Interface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 在爱词霸翻译的基础上做的一个Retrofit模型
 * 第三步：创建Retrofit对象
 * 第四步：创建网络请求接口实例
 * 第五步：发送网络请求
 * 第六步：处理网络请求
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //使用Retrofit封装的方法
        request();

        //使用Retrofit封装的方法
        //requestpost();
    }


    //post
    private void requestpost() {
        //3创建对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        //4 创建接口实例
        PostRequest_Interface postRequest = retrofit.create(PostRequest_Interface.class);

        //5 发送请求
        Call<Translation1> call = postRequest.getCall("I Love you");
        call.enqueue(new Callback<Translation1>() {
            @Override
            public void onResponse(Call<Translation1> call, Response<Translation1> response) {
                Log.e("post", response.body().getTranslateResult().get(0).get(0).getTgt());
            }

            @Override
            public void onFailure(Call<Translation1> call, Throwable t) {

            }
        });
    }


    //get
    private void request() {
        //3 创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") //设置网络请求的url
                .addConverterFactory(GsonConverterFactory.create())//设置使用Gson解析（添加依赖）
                .build();

        //4 创建网络请求接口实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //5 发送网络请求
        Call<Translation> call = request.getCall();
        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                //6 处理返回结果
                Log.e("response",response.body().toString());
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                System.out.println("连接失败");
            }
        });

    }


}

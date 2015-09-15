package com.frodo.android.app.simple;

import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by frodo on 2015/8/10. for rxjava example
 */
public class RxJavaApi {
    private static final String LOG_TAG = RxJavaApi.class.getSimpleName();
    private static final String ENDPOINT = "http://api.openweathermap.org/data/2.5";
    private static final String[] CITIES = {"Budapest,hu"};
    private static final RestAdapter restAdapter =
            new RestAdapter.Builder().setEndpoint(ENDPOINT).setLogLevel(RestAdapter.LogLevel.FULL).build();
    private static final ApiManagerService apiManager = restAdapter.create(ApiManagerService.class);

    public static void test(final TextView textView) {
        /**
         * 多个 city 请求
         * map，flatMap 对 Observable进行变换
         */
        Observable.from(CITIES)
                .flatMap(new Func1<String, Observable<WeatherData>>() {
                    @Override
                    public Observable<WeatherData> call(String s) {
                        return RxJavaApi.getWeatherData(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(/*onNext*/new Action1<WeatherData>() {
                    @Override
                    public void call(WeatherData weatherData) {
                        textView.setText(weatherData.toString());
                        Log.d(LOG_TAG, weatherData.toString());
                    }
                }, /*onError*/new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    /**
     * 将服务接口返回的数据，封装成{@link rx.Observable}
     * 这种写法适用于将旧代码封装
     *
     * @param city
     * @return
     */
    public static Observable<WeatherData> getWeatherData(final String city) {
        return Observable.create(new Observable.OnSubscribe<WeatherData>() {
            @Override
            public void call(Subscriber<? super WeatherData> subscriber) {
                //订阅者回调 onNext 和 onCompleted
                subscriber.onNext(apiManager.getWeather(city, "metric"));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    /**
     * 服务接口
     */
    private interface ApiManagerService {
        @GET("/weather")
        WeatherData getWeather(@Query("q") String place, @Query("units") String units);

        /**
         * retrofit 支持 rxjava 整合
         * 这种方法适用于新接口
         */
        @GET("/weather")
        Observable<WeatherData> getWeatherData(@Query("q") String place, @Query("units") String units);
    }

    public static class WeatherData {
        public Coordinates coord;
        public Local sys;
        public List<Weather> weathers;
        public String base;
        public Main main;
        public Wind wind;
        public Rain rain;
        public Cloud clouds;
        public long id;
        public long dt;
        public String name;
        public int cod;

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }

        public static class Coordinates {
            public double lat;
            public double lon;
        }

        public static class Local {
            public String country;
            public long sunrise;
            public long sunset;
        }

        public static class Weather {
            public int id;
            public String main;
            public String description;
            public String icon;
        }

        public static class Main {
            public double temp;
            public double pressure;
            public double humidity;
            public double temp_min;
            public double temp_max;
            public double sea_level;
            public double grnd_level;
        }

        public static class Wind {
            public double speed;
            public double deg;
        }

        public static class Rain {
            public int threehourforecast;
        }

        public static class Cloud {
            public int all;
        }
    }
}

package com.frodo.android.app.simple;

import java.util.List;

import android.util.Log;
import android.widget.TextView;
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
 * Created by frodo on 2015/8/10.
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
     *
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
            final StringBuilder sb = new StringBuilder("WeatherData{");
            sb.append("coord=").append(coord);
            sb.append(", sys=").append(sys);
            sb.append(", weathers=").append(weathers);
            sb.append(", base='").append(base).append('\'');
            sb.append(", main=").append(main);
            sb.append(", wind=").append(wind);
            sb.append(", rain=").append(rain);
            sb.append(", clouds=").append(clouds);
            sb.append(", id=").append(id);
            sb.append(", dt=").append(dt);
            sb.append(", name='").append(name).append('\'');
            sb.append(", cod=").append(cod);
            sb.append('}');
            return sb.toString();
        }

        public static class Coordinates {
            public double lat;
            public double lon;

            @Override
            public String toString() {
                final StringBuilder sb = new StringBuilder("Coordinates{");
                sb.append("lat=").append(lat);
                sb.append(", lon=").append(lon);
                sb.append('}');
                return sb.toString();
            }
        }

        public static class Local {
            public String country;
            public long sunrise;
            public long sunset;

            @Override
            public String toString() {
                final StringBuilder sb = new StringBuilder("Local{");
                sb.append("country='").append(country).append('\'');
                sb.append(", sunrise=").append(sunrise);
                sb.append(", sunset=").append(sunset);
                sb.append('}');
                return sb.toString();
            }
        }

        public static class Weather {
            public int id;
            public String main;
            public String description;
            public String icon;

            @Override
            public String toString() {
                final StringBuilder sb = new StringBuilder("Weather{");
                sb.append("id=").append(id);
                sb.append(", main='").append(main).append('\'');
                sb.append(", description='").append(description).append('\'');
                sb.append(", icon='").append(icon).append('\'');
                sb.append('}');
                return sb.toString();
            }
        }

        public static class Main {
            public double temp;
            public double pressure;
            public double humidity;
            public double temp_min;
            public double temp_max;
            public double sea_level;
            public double grnd_level;

            @Override
            public String toString() {
                final StringBuilder sb = new StringBuilder("Main{");
                sb.append("temp=").append(temp);
                sb.append(", pressure=").append(pressure);
                sb.append(", humidity=").append(humidity);
                sb.append(", temp_min=").append(temp_min);
                sb.append(", temp_max=").append(temp_max);
                sb.append(", sea_level=").append(sea_level);
                sb.append(", grnd_level=").append(grnd_level);
                sb.append('}');
                return sb.toString();
            }
        }

        public static class Wind {
            public double speed;
            public double deg;

            @Override
            public String toString() {
                final StringBuilder sb = new StringBuilder("Wind{");
                sb.append("speed=").append(speed);
                sb.append(", deg=").append(deg);
                sb.append('}');
                return sb.toString();
            }
        }

        public static class Rain {
            public int threehourforecast;

            @Override
            public String toString() {
                final StringBuilder sb = new StringBuilder("Rain{");
                sb.append("threehourforecast=").append(threehourforecast);
                sb.append('}');
                return sb.toString();
            }
        }

        public static class Cloud {
            public int all;

            @Override
            public String toString() {
                final StringBuilder sb = new StringBuilder("Cloud{");
                sb.append("all=").append(all);
                sb.append('}');
                return sb.toString();
            }
        }
    }

}

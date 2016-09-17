package com.jack.weather.model;

import java.util.List;

/**
 * Created by Jack on 2016/6/26.
 */
public class WeatherInfo {

    /**
     * msg : success
     * result : [{"airCondition":"优","city":"南京","coldIndex":"低发期","date":"2016-06-26","distrct":"南京","dressingIndex":"薄短袖类","exerciseIndex":"不适宜","future":[{"date":"2016-06-26","night":"小雨-中雨","temperature":"21°C","week":"今天","wind":"东南风 3～4级"},{"date":"2016-06-27","dayTime":"雷阵雨","night":"中雨","temperature":"24°C / 22°C","week":"星期一","wind":"东南风 3～4级"},{"date":"2016-06-28","dayTime":"大雨","night":"大雨","temperature":"25°C / 23°C","week":"星期二","wind":"南风 3～4级"},{"date":"2016-06-29","dayTime":"雷阵雨","night":"多云","temperature":"29°C / 24°C","week":"星期三","wind":"西南风 3～4级"},{"date":"2016-06-30","dayTime":"多云","night":"阴","temperature":"32°C / 25°C","week":"星期四","wind":"南风 3～4级"},{"date":"2016-07-01","dayTime":"雷阵雨","night":"雷阵雨","temperature":"31°C / 26°C","week":"星期五","wind":"南风 3～4级"},{"date":"2016-07-02","dayTime":"雷阵雨","night":"雷阵雨","temperature":"31°C / 26°C","week":"星期六","wind":"西南风 3～4级"},{"date":"2016-07-03","dayTime":"雷雨","night":"雷雨","temperature":"28°C / 23°C","week":"星期日","wind":"西南风 2级"},{"date":"2016-07-04","dayTime":"雷雨","night":"雷雨","temperature":"29°C / 24°C","week":"星期一","wind":"西南偏南风 2级"},{"date":"2016-07-05","dayTime":"雷雨","night":"雷雨","temperature":"32°C / 25°C","week":"星期二","wind":"西南风 2级"}],"humidity":"湿度：86%","pollutionIndex":"31","province":"江苏","sunrise":"05:01","sunset":"19:15","temperature":"22℃","time":"22:42","updateTime":"20160626230021","washIndex":"不适宜","weather":"小到中雨","week":"周日","wind":"东南风2级"}]
     * retCode : 200
     */

    private String msg;
    private String retCode;
    /**
     * airCondition : 优
     * city : 南京
     * coldIndex : 低发期
     * date : 2016-06-26
     * distrct : 南京
     * dressingIndex : 薄短袖类
     * exerciseIndex : 不适宜
     * future : [{"date":"2016-06-26","night":"小雨-中雨","temperature":"21°C","week":"今天","wind":"东南风 3～4级"},{"date":"2016-06-27","dayTime":"雷阵雨","night":"中雨","temperature":"24°C / 22°C","week":"星期一","wind":"东南风 3～4级"},{"date":"2016-06-28","dayTime":"大雨","night":"大雨","temperature":"25°C / 23°C","week":"星期二","wind":"南风 3～4级"},{"date":"2016-06-29","dayTime":"雷阵雨","night":"多云","temperature":"29°C / 24°C","week":"星期三","wind":"西南风 3～4级"},{"date":"2016-06-30","dayTime":"多云","night":"阴","temperature":"32°C / 25°C","week":"星期四","wind":"南风 3～4级"},{"date":"2016-07-01","dayTime":"雷阵雨","night":"雷阵雨","temperature":"31°C / 26°C","week":"星期五","wind":"南风 3～4级"},{"date":"2016-07-02","dayTime":"雷阵雨","night":"雷阵雨","temperature":"31°C / 26°C","week":"星期六","wind":"西南风 3～4级"},{"date":"2016-07-03","dayTime":"雷雨","night":"雷雨","temperature":"28°C / 23°C","week":"星期日","wind":"西南风 2级"},{"date":"2016-07-04","dayTime":"雷雨","night":"雷雨","temperature":"29°C / 24°C","week":"星期一","wind":"西南偏南风 2级"},{"date":"2016-07-05","dayTime":"雷雨","night":"雷雨","temperature":"32°C / 25°C","week":"星期二","wind":"西南风 2级"}]
     * humidity : 湿度：86%
     * pollutionIndex : 31
     * province : 江苏
     * sunrise : 05:01
     * sunset : 19:15
     * temperature : 22℃
     * time : 22:42
     * updateTime : 20160626230021
     * washIndex : 不适宜
     * weather : 小到中雨
     * week : 周日
     * wind : 东南风2级
     */

    private List<ResultBean> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String airCondition;
        private String city;
        private String coldIndex;
        private String date;
        private String distrct;
        private String dressingIndex;
        private String exerciseIndex;
        private String humidity;
        private String pollutionIndex;
        private String province;
        private String sunrise;
        private String sunset;
        private String temperature;
        private String time;
        private String updateTime;
        private String washIndex;
        private String weather;
        private String week;
        private String wind;
        /**
         * date : 2016-06-26
         * night : 小雨-中雨
         * temperature : 21°C
         * week : 今天
         * wind : 东南风 3～4级
         */

        private List<FutureBean> future;

        public String getAirCondition() {
            return airCondition;
        }

        public void setAirCondition(String airCondition) {
            this.airCondition = airCondition;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getColdIndex() {
            return coldIndex;
        }

        public void setColdIndex(String coldIndex) {
            this.coldIndex = coldIndex;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDistrct() {
            return distrct;
        }

        public void setDistrct(String distrct) {
            this.distrct = distrct;
        }

        public String getDressingIndex() {
            return dressingIndex;
        }

        public void setDressingIndex(String dressingIndex) {
            this.dressingIndex = dressingIndex;
        }

        public String getExerciseIndex() {
            return exerciseIndex;
        }

        public void setExerciseIndex(String exerciseIndex) {
            this.exerciseIndex = exerciseIndex;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getPollutionIndex() {
            return pollutionIndex;
        }

        public void setPollutionIndex(String pollutionIndex) {
            this.pollutionIndex = pollutionIndex;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getWashIndex() {
            return washIndex;
        }

        public void setWashIndex(String washIndex) {
            this.washIndex = washIndex;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getWind() {
            return wind;
        }

        public void setWind(String wind) {
            this.wind = wind;
        }

        public List<FutureBean> getFuture() {
            return future;
        }

        public void setFuture(List<FutureBean> future) {
            this.future = future;
        }

        public static class FutureBean {
            private String date;
            private String night;
            private String temperature;
            private String week;
            private String wind;
            private String dayTime;

            public String getDayTime() {return dayTime;}

            public void setDayTime(String dayTime) {this.dayTime = dayTime;}

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getNight() {
                return night;
            }

            public void setNight(String night) {
                this.night = night;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }
        }
    }
}

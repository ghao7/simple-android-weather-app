package com.example.guhao.myweather.bean;

import java.util.List;

/**
 * Created by guhao on 6/13/17.
 */

public class ForecastEntity {


    private List<HeWeather5Bean> HeWeather5;

    public List<HeWeather5Bean> getHeWeather5() {
        return HeWeather5;
    }

    public void setHeWeather5(List<HeWeather5Bean> HeWeather5) {
        this.HeWeather5 = HeWeather5;
    }

    public static class HeWeather5Bean {
        /**
         * basic : {"city":"北京","cnty":"中国","id":"CN101010100","lat":"39.90498734","lon":"116.40528870","update":{"loc":"2017-06-23 10:50","utc":"2017-06-23 02:50"}}
         * daily_forecast : [{"astro":{"mr":"04:00","ms":"18:42","sr":"04:48","ss":"19:45"},"cond":{"code_d":"306","code_n":"302","txt_d":"中雨","txt_n":"雷阵雨"},"date":"2017-06-23","hum":"87","pcpn":"4.2","pop":"100","pres":"1003","tmp":{"max":"24","min":"19"},"uv":"6","vis":"15","wind":{"deg":"97","dir":"东风","sc":"微风","spd":"7"}},{"astro":{"mr":"04:56","ms":"19:48","sr":"04:48","ss":"19:46"},"cond":{"code_d":"300","code_n":"104","txt_d":"阵雨","txt_n":"阴"},"date":"2017-06-24","hum":"75","pcpn":"0.1","pop":"23","pres":"1003","tmp":{"max":"27","min":"18"},"uv":"7","vis":"17","wind":{"deg":"109","dir":"北风","sc":"微风","spd":"3"}},{"astro":{"mr":"05:58","ms":"20:47","sr":"04:48","ss":"19:46"},"cond":{"code_d":"104","code_n":"101","txt_d":"阴","txt_n":"多云"},"date":"2017-06-25","hum":"72","pcpn":"0.3","pop":"44","pres":"1004","tmp":{"max":"29","min":"20"},"uv":"8","vis":"19","wind":{"deg":"169","dir":"南风","sc":"微风","spd":"8"}}]
         * status : ok
         */

        private BasicBean basic;
        private String status;
        private List<DailyForecastBean> daily_forecast;

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<DailyForecastBean> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public static class BasicBean {
            /**
             * city : 北京
             * cnty : 中国
             * id : CN101010100
             * lat : 39.90498734
             * lon : 116.40528870
             * update : {"loc":"2017-06-23 10:50","utc":"2017-06-23 02:50"}
             */

            private String city;
            private String cnty;
            private String id;
            private String lat;
            private String lon;
            private UpdateBean update;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public UpdateBean getUpdate() {
                return update;
            }

            public void setUpdate(UpdateBean update) {
                this.update = update;
            }

            public static class UpdateBean {
                /**
                 * loc : 2017-06-23 10:50
                 * utc : 2017-06-23 02:50
                 */

                private String loc;
                private String utc;

                public String getLoc() {
                    return loc;
                }

                public void setLoc(String loc) {
                    this.loc = loc;
                }

                public String getUtc() {
                    return utc;
                }

                public void setUtc(String utc) {
                    this.utc = utc;
                }
            }
        }

        public static class DailyForecastBean {
            /**
             * astro : {"mr":"04:00","ms":"18:42","sr":"04:48","ss":"19:45"}
             * cond : {"code_d":"306","code_n":"302","txt_d":"中雨","txt_n":"雷阵雨"}
             * date : 2017-06-23
             * hum : 87
             * pcpn : 4.2
             * pop : 100
             * pres : 1003
             * tmp : {"max":"24","min":"19"}
             * uv : 6
             * vis : 15
             * wind : {"deg":"97","dir":"东风","sc":"微风","spd":"7"}
             */

            private AstroBean astro;
            private CondBean cond;
            private String date;
            private String hum;
            private String pcpn;
            private String pop;
            private String pres;
            private TmpBean tmp;
            private String uv;
            private String vis;
            private WindBean wind;

            public AstroBean getAstro() {
                return astro;
            }

            public void setAstro(AstroBean astro) {
                this.astro = astro;
            }

            public CondBean getCond() {
                return cond;
            }

            public void setCond(CondBean cond) {
                this.cond = cond;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public TmpBean getTmp() {
                return tmp;
            }

            public void setTmp(TmpBean tmp) {
                this.tmp = tmp;
            }

            public String getUv() {
                return uv;
            }

            public void setUv(String uv) {
                this.uv = uv;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public WindBean getWind() {
                return wind;
            }

            public void setWind(WindBean wind) {
                this.wind = wind;
            }

            public static class AstroBean {
                /**
                 * mr : 04:00
                 * ms : 18:42
                 * sr : 04:48
                 * ss : 19:45
                 */

                private String mr;
                private String ms;
                private String sr;
                private String ss;

                public String getMr() {
                    return mr;
                }

                public void setMr(String mr) {
                    this.mr = mr;
                }

                public String getMs() {
                    return ms;
                }

                public void setMs(String ms) {
                    this.ms = ms;
                }

                public String getSr() {
                    return sr;
                }

                public void setSr(String sr) {
                    this.sr = sr;
                }

                public String getSs() {
                    return ss;
                }

                public void setSs(String ss) {
                    this.ss = ss;
                }
            }

            public static class CondBean {
                /**
                 * code_d : 306
                 * code_n : 302
                 * txt_d : 中雨
                 * txt_n : 雷阵雨
                 */

                private String code_d;
                private String code_n;
                private String txt_d;
                private String txt_n;

                public String getCode_d() {
                    return code_d;
                }

                public void setCode_d(String code_d) {
                    this.code_d = code_d;
                }

                public String getCode_n() {
                    return code_n;
                }

                public void setCode_n(String code_n) {
                    this.code_n = code_n;
                }

                public String getTxt_d() {
                    return txt_d;
                }

                public void setTxt_d(String txt_d) {
                    this.txt_d = txt_d;
                }

                public String getTxt_n() {
                    return txt_n;
                }

                public void setTxt_n(String txt_n) {
                    this.txt_n = txt_n;
                }
            }

            public static class TmpBean {
                /**
                 * max : 24
                 * min : 19
                 */

                private String max;
                private String min;

                public String getMax() {
                    return max;
                }

                public void setMax(String max) {
                    this.max = max;
                }

                public String getMin() {
                    return min;
                }

                public void setMin(String min) {
                    this.min = min;
                }
            }

            public static class WindBean {
                /**
                 * deg : 97
                 * dir : 东风
                 * sc : 微风
                 * spd : 7
                 */

                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }
            }
        }
    }
}

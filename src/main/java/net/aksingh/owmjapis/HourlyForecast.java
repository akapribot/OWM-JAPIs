/*
 * Copyright (c) 2013-2015 Ashutosh Kumar Singh <me@aksingh.net>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.aksingh.owmjapis;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Parses hourly forecast data and provides methods to get/access the same information.
 * This class provides <code>has</code> and <code>get</code> methods to access the information.
 * </p>
 * <p>
 * <code>has</code> methods can be used to check if the data exists, i.e., if the data was available
 * (successfully downloaded) and was parsed correctly.
 * <code>get</code> methods can be used to access the data, if the data exists, otherwise <code>get</code>
 * methods will give value as per following basis:
 * Boolean: <code>false</code>
 * Integral: Minimum value (MIN_VALUE)
 * Floating point: Not a number (NaN)
 * Others: <code>null</code>
 * </p>
 *
 * @author Ashutosh Kumar Singh
 * @version 2014/12/27
 * @see <a href="http://openweathermap.org/forecast">OWM's Weather Forecast API</a>
 * @since 2.5.0.3
 */
public class HourlyForecast extends AbstractForecast {
    /*
    Instance variables
     */
    private final List<Forecast> forecastList;

    /*
    Constructor
     */
    HourlyForecast(JSONObject jsonObj) {
        super(jsonObj);

        JSONArray forecastArr = (jsonObj != null) ? jsonObj.optJSONArray(this.JSON_FORECAST_LIST) : new JSONArray();
        this.forecastList = (forecastArr != null) ? new ArrayList<Forecast>(forecastArr.length()) : Collections.EMPTY_LIST;
        if (forecastArr != null && this.forecastList != Collections.EMPTY_LIST) {
            for (int i = 0; i < forecastArr.length(); i++) {
                JSONObject forecastObj = forecastArr.optJSONObject(i);
                if (forecastObj != null) {
                    this.forecastList.add(new Forecast(forecastObj));
                }
            }
        }
    }

    /**
     * @param index Index of Forecast instance in the list.
     * @return Forecast instance if available, otherwise <code>null</code>.
     */
    public Forecast getForecastInstance(int index) {
        return this.forecastList.get(index);
    }

    /**
     * <p>
     * Parses forecast data (one element in the forecastList) and provides methods to get/access the same information.
     * This class provides <code>has</code> and <code>get</code> methods to access the information.
     * </p>
     * <p>
     * <code>has</code> methods can be used to check if the data exists, i.e., if the data was available
     * (successfully downloaded) and was parsed correctly.
     * <code>get</code> methods can be used to access the data, if the data exists, otherwise <code>get</code>
     * methods will give value as per following basis:
     * Boolean: <code>false</code>
     * Integral: Minimum value (MIN_VALUE)
     * Floating point: Not a number (NaN)
     * Others: <code>null</code>
     * </p>
     */
    public static class Forecast extends AbstractForecast.Forecast {
        /*
        JSON Keys
         */
        private static final String JSON_SYS = "sys";
        private static final String JSON_DT_TEXT = "dt_txt";

        /*
        Instance Variables
         */
        private final String dateTimeText;

        private final Clouds clouds;
        private final Main main;
        private final Sys sys;
        private final Wind wind;

        /*
        Constructor
         */
        Forecast(JSONObject jsonObj) {
            super(jsonObj);

            this.dateTimeText = (jsonObj != null) ? jsonObj.optString(JSON_DT_TEXT, null) : null;

            JSONObject jsonObjClouds = (jsonObj != null) ? jsonObj.optJSONObject(JSON_CLOUDS) : null;
            this.clouds = (jsonObjClouds != null) ? new Clouds(jsonObjClouds) : null;

            JSONObject jsonObjMain = (jsonObj != null) ? jsonObj.optJSONObject(JSON_MAIN) : null;
            this.main = (jsonObjMain != null) ? new Main(jsonObjMain) : null;

            JSONObject jsonObjSys = (jsonObj != null) ? jsonObj.optJSONObject(JSON_SYS) : null;
            this.sys = (jsonObjSys != null) ? new Sys(jsonObjSys) : null;

            JSONObject jsonObjWind = (jsonObj != null) ? jsonObj.optJSONObject(JSON_WIND) : null;
            this.wind = (jsonObjWind != null) ? new Wind(jsonObjWind) : null;
        }

        public boolean hasDateTimeText() {
            return this.dateTimeText != null;
        }

        /**
         * @return <code>true</code> if Clouds instance is available, otherwise <code>false</code>.
         */
        public boolean hasCloudsInstance() {
            return clouds != null;
        }

        /**
         * @return <code>true</code> if Main instance is available, otherwise <code>false</code>.
         */
        public boolean hasMainInstance() {
            return main != null;
        }

        /**
         * @return <code>true</code> if Sys instance is available, otherwise <code>false</code>.
         */
        public boolean hasSysInstance() {
            return sys != null;
        }

        /**
         * @return <code>true</code> if Wind instance is available, otherwise <code>false</code>.
         */
        public boolean hasWindInstance() {
            return wind != null;
        }

        public String getDateTimeText() {
            return this.dateTimeText;
        }

        /**
         * @return Clouds instance if available, otherwise <code>null</code>.
         */
        public Clouds getCloudsInstance() {
            return this.clouds;
        }

        /**
         * @return Main instance if available, otherwise <code>null</code>.
         */
        public Main getMainInstance() {
            return this.main;
        }

        /**
         * @return Sys instance if available, otherwise <code>null</code>.
         */
        public Sys getSysInstance() {
            return this.sys;
        }

        /**
         * @return Wind instance if available, otherwise <code>null</code>.
         */
        public Wind getWindInstance() {
            return this.wind;
        }

        /**
         * <p>
         * Parses clouds data and provides methods to get/access the same information.
         * This class provides <code>has</code> and <code>get</code> methods to access the information.
         * </p>
         * <p>
         * <code>has</code> methods can be used to check if the data exists, i.e., if the data was available
         * (successfully downloaded) and was parsed correctly.
         * <code>get</code> methods can be used to access the data, if the data exists, otherwise <code>get</code>
         * methods will give value as per following basis:
         * Boolean: <code>false</code>
         * Integral: Minimum value (MIN_VALUE)
         * Floating point: Not a number (NaN)
         * Others: <code>null</code>
         * </p>
         *
         * @author Ashutosh Kumar Singh
         * @version 2014/12/26
         * @since 2.5.0.1
         */
        public static class Clouds extends AbstractForecast.Forecast.Clouds {

            Clouds() {
                super();
            }

            Clouds(JSONObject jsonObj) {
                super(jsonObj);
            }
        }

        /**
         * <p>
         * Parses main data and provides methods to get/access the same information.
         * This class provides <code>has</code> and <code>get</code> methods to access the information.
         * </p>
         * <p>
         * <code>has</code> methods can be used to check if the data exists, i.e., if the data was available
         * (successfully downloaded) and was parsed correctly.
         * <code>get</code> methods can be used to access the data, if the data exists, otherwise <code>get</code>
         * methods will give value as per following basis:
         * Boolean: <code>false</code>
         * Integral: Minimum value (MIN_VALUE)
         * Floating point: Not a number (NaN)
         * Others: <code>null</code>
         * </p>
         *
         * @author Ashutosh Kumar Singh
         * @version 2014/12/26
         * @since 2.5.0.1
         */
        public static class Main extends AbstractForecast.Forecast.Main {
            private static final String JSON_MAIN_SEA_LEVEL = "sea_level";
            private static final String JSON_MAIN_GRND_LEVEL = "grnd_level";
            private static final String JSON_MAIN_TMP_KF = "temp_kf";

            private final float seaLevel;
            private final float groundLevel;
            private final float tempKF;

            Main() {
                super();

                this.seaLevel = Float.NaN;
                this.groundLevel = Float.NaN;
                this.tempKF = Float.NaN;
            }

            Main(JSONObject jsonObj) {
                super(jsonObj);

                this.seaLevel = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_MAIN_SEA_LEVEL, Float.NaN) : Float.NaN;
                this.groundLevel = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_MAIN_GRND_LEVEL, Float.NaN) : Float.NaN;
                this.tempKF = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_MAIN_TMP_KF, Float.NaN) : Float.NaN;
            }

            public boolean hasSeaLevel() {
                return !Float.isNaN(this.seaLevel);
            }

            public boolean hasGroundLevel() {
                return !Float.isNaN(this.groundLevel);
            }

            public boolean hasTempKF() {
                return !Float.isNaN(this.tempKF);
            }

            public float getSeaLevel() {
                return this.seaLevel;
            }

            public float getGroundLevel() {
                return this.groundLevel;
            }

            public float getTempKF() {
                return this.tempKF;
            }
        }

        /**
         * <p>
         * Parses sys data and provides methods to get/access the same information.
         * This class provides <code>has</code> and <code>get</code> methods to access the information.
         * </p>
         * <p>
         * <code>has</code> methods can be used to check if the data exists, i.e., if the data was available
         * (successfully downloaded) and was parsed correctly.
         * <code>get</code> methods can be used to access the data, if the data exists, otherwise <code>get</code>
         * methods will give value as per following basis:
         * Boolean: <code>false</code>
         * Integral: Minimum value (MIN_VALUE)
         * Floating point: Not a number (NaN)
         * Others: <code>null</code>
         * </p>
         *
         * @author Ashutosh Kumar Singh
         * @version 2014/12/26
         * @since 2.5.0.1
         */
        public static class Sys implements Serializable {
            private static final String JSON_SYS_POD = "pod";

            private final String pod;

            Sys() {
                this.pod = null;
            }

            Sys(JSONObject jsonObj) {
                this.pod = (jsonObj != null) ? jsonObj.optString(JSON_SYS_POD, null) : null;
            }

            public boolean hasPod() {
                return this.pod != null && (! "".equals(this.pod));
            }

            public String getPod() {
                return this.pod;
            }
        }

        /**
         * <p>
         * Parses wind data and provides methods to get/access the same information.
         * This class provides <code>has</code> and <code>get</code> methods to access the information.
         * </p>
         * <p>
         * <code>has</code> methods can be used to check if the data exists, i.e., if the data was available
         * (successfully downloaded) and was parsed correctly.
         * <code>get</code> methods can be used to access the data, if the data exists, otherwise <code>get</code>
         * methods will give value as per following basis:
         * Boolean: <code>false</code>
         * Integral: Minimum value (MIN_VALUE)
         * Floating point: Not a number (NaN)
         * Others: <code>null</code>
         * </p>
         *
         * @author Ashutosh Kumar Singh
         * @version 2014/12/26
         * @since 2.5.0.1
         */
        public static class Wind extends AbstractWeather.Wind {

            Wind() {
                super();
            }

            Wind(JSONObject jsonObj) {
                super(jsonObj);
            }
        }
    }
}

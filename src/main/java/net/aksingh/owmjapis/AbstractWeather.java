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
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Provides default behaviours and implementations for:
 * 1. {@link net.aksingh.owmjapis.CurrentWeather}
 * It defines common methods like <code>has</code>, <code>get</code> and some others.
 * </p>
 *
 * @author Ashutosh Kumar Singh
 * @version 2014/12/21
 * @since 2.5.0.1
 */
public abstract class AbstractWeather extends AbstractResponse {
    /*
    JSON Keys
     */
    static final String JSON_CLOUDS = "clouds";
    static final String JSON_COORD = "coord";
    static final String JSON_MAIN = "main";
    static final String JSON_WIND = "wind";
    private static final String JSON_WEATHER = "weather";
    private static final String JSON_DATE_TIME = "dt";

    /*
    Instance variables
     */
    private final Date dateTime;

    private final int weatherCount;
    private final List<Weather> weatherList;

    /*
    Constructors
     */
    AbstractWeather() {
        super();

        this.weatherCount = 0;
        this.weatherList = null;
        this.dateTime = null;
    }

    AbstractWeather(JSONObject jsonObj) {
        super(jsonObj);

        long sec = (jsonObj != null) ? jsonObj.optLong(JSON_DATE_TIME, Long.MIN_VALUE) : Long.MIN_VALUE;
        if (sec != Long.MIN_VALUE) { // converting seconds to Date object
            this.dateTime = new Date(sec * 1000);
        } else {
            this.dateTime = null;
        }

        JSONArray weatherArray = (jsonObj != null) ? jsonObj.optJSONArray(JSON_WEATHER) : new JSONArray();
        this.weatherList = (weatherArray != null) ? new ArrayList<Weather>(weatherArray.length()) : Collections.EMPTY_LIST;
        if (weatherArray != null && this.weatherList != Collections.EMPTY_LIST) {
            for (int i = 0; i < weatherArray.length(); i++) {
                JSONObject weatherObj = weatherArray.optJSONObject(i);
                if (weatherObj != null) {
                    this.weatherList.add(new Weather(weatherObj));
                }
            }
        }
        this.weatherCount = this.weatherList.size();
    }

    /**
     * @return <code>true</code> if date/time is available, otherwise <code>false</code>.
     */
    public boolean hasDateTime() {
        return this.dateTime != null;
    }

    /**
     * @return <code>true</code> if Weather instance(s) is available, otherwise <code>false</code>.
     */
    public boolean hasWeatherInstance() {
        return weatherCount != 0;
    }

    /**
     * @return Date and time if available, otherwise <code>null</code>.
     */
    public Date getDateTime() {
        return this.dateTime;
    }

    /**
     * @return Count of Weather instance(s) if available, otherwise 0.
     */
    public int getWeatherCount() {
        return this.weatherCount;
    }

    /**
     * @param index Index of Weather instance in the list.
     * @return Weather instance if available, otherwise <code>null</code>.
     */
    public Weather getWeatherInstance(int index) {
        return this.weatherList.get(index);
    }

    /**
     * <p>
     * Provides default behaviours for Cloud
     * </p>
     *
     * @author Ashutosh Kumar Singh
     * @version 2013/12/23
     * @since 2.5.0.1
     */
    abstract public static class Clouds implements Serializable {
        private static final String JSON_CLOUDS_ALL = "all";

        private final float percentOfClouds;

        Clouds() {
            this.percentOfClouds = Float.NaN;
        }

        Clouds(JSONObject jsonObj) {
            this.percentOfClouds = (float) jsonObj.optDouble(JSON_CLOUDS_ALL, Double.NaN);
        }

        /**
         * Tells if percentage of clouds is available or not.
         *
         * @return <code>true</code> if data available, otherwise <code>false</code>
         */
        public boolean hasPercentageOfClouds() {
            return !Float.isNaN(this.percentOfClouds);
        }

        /**
         * @return Percentage of all clouds if available, otherwise <code>Float.NaN</code>.
         */
        public float getPercentageOfClouds() {
            return this.percentOfClouds;
        }
    }

    /**
     * <p>
     * Provides default behaviours for Coord, i.e., coordinates.
     * </p>
     *
     * @author Ashutosh Kumar Singh
     * @version 2013/12/23
     * @since 2.5.0.1
     */
    abstract public static class Coord implements Serializable {
        private static final String JSON_COORD_LATITUDE = "lat";
        private static final String JSON_COORD_LONGITUDE = "lon";

        private final float lat;
        private final float lon;

        Coord() {
            this.lat = Float.NaN;
            this.lon = Float.NaN;
        }

        Coord(JSONObject jsonObj) {
            this.lat = (float) jsonObj.optDouble(JSON_COORD_LATITUDE, Double.NaN);
            this.lon = (float) jsonObj.optDouble(JSON_COORD_LONGITUDE, Double.NaN);
        }

        /**
         * Tells if the latitude of the city is available or not.
         *
         * @return <code>true</code> if data available, otherwise <code>false</code>
         */
        public boolean hasLatitude() {
            return !Float.isNaN(this.lat);
        }

        /**
         * Tells if the longitude of the city is available or not.
         *
         * @return <code>true</code> if data available, otherwise <code>false</code>
         */
        public boolean hasLongitude() {
            return !Float.isNaN(this.lon);
        }

        /**
         * @return Latitude of the city if available, otherwise <code>Float.NaN</code>.
         */
        public float getLatitude() {
            return this.lat;
        }

        /**
         * @return Longitude of the city if available, otherwise <code>Float.NaN</code>.
         */
        public float getLongitude() {
            return this.lon;
        }
    }

    /**
     * <p>
     * Provides default behaviours for Main, i.e., main weather elements like temperature, humidity, etc.
     * </p>
     *
     * @author Ashutosh Kumar Singh
     * @version 2013/12/23
     * @since 2.5.0.1
     */
    abstract public static class Main implements Serializable {

        private static final String JSON_MAIN_TEMP = "temp";
        private static final String JSON_MAIN_TEMP_MIN = "temp_min";
        private static final String JSON_MAIN_TEMP_MAX = "temp_max";
        private static final String JSON_MAIN_PRESSURE = "pressure";
        private static final String JSON_MAIN_HUMIDITY = "humidity";

        private final float temp;
        private final float minTemp;
        private final float maxTemp;
        private final float pressure;
        private final float humidity;

        Main() {
            this.temp = Float.NaN;
            this.minTemp = Float.NaN;
            this.maxTemp = Float.NaN;
            this.pressure = Float.NaN;
            this.humidity = Float.NaN;
        }

        Main(JSONObject jsonObj) {
            this.temp = (float) jsonObj.optDouble(JSON_MAIN_TEMP, Double.NaN);
            this.minTemp = (float) jsonObj.optDouble(JSON_MAIN_TEMP_MIN, Double.NaN);
            this.maxTemp = (float) jsonObj.optDouble(JSON_MAIN_TEMP_MAX, Double.NaN);
            this.pressure = (float) jsonObj.optDouble(JSON_MAIN_PRESSURE, Double.NaN);
            this.humidity = (float) jsonObj.optDouble(JSON_MAIN_HUMIDITY, Double.NaN);
        }

        /**
         * Tells if the temperature of the city is available or not.
         *
         * @return <code>true</code> if data available, otherwise <code>false</code>
         */
        public boolean hasTemperature() {
            return !Float.isNaN(this.temp);
        }

        /**
         * Tells if the minimum temperature of the city is available or not.
         *
         * @return <code>true</code> if data available, otherwise <code>false</code>
         */
        public boolean hasMinTemperature() {
            return !Float.isNaN(this.minTemp);
        }

        /**
         * Tells if the maximum temperature of the city is available or not.
         *
         * @return <code>true</code> if data available, otherwise <code>false</code>
         */
        public boolean hasMaxTemperature() {
            return !Float.isNaN(this.maxTemp);
        }

        /**
         * Tells if pressure of the city is available or not.
         *
         * @return <code>true</code> if data available, otherwise <code>false</code>
         */
        public boolean hasPressure() {
            return !Float.isNaN(this.pressure);
        }

        /**
         * Tells if humidity of the city is available or not.
         *
         * @return <code>true</code> if data available, otherwise <code>false</code>
         */
        public boolean hasHumidity() {
            return !Float.isNaN(this.humidity);
        }

        /**
         * @return Temperature of the city if available, otherwise <code>Float.NaN</code>.
         */
        public float getTemperature() {
            return this.temp;
        }

        /**
         * @return Minimum temperature of the city if available, otherwise <code>Float.NaN</code>.
         */
        public float getMinTemperature() {
            return this.minTemp;
        }

        /**
         * @return Maximum temperature of the city if available, otherwise <code>Float.NaN</code>.
         */
        public float getMaxTemperature() {
            return this.maxTemp;
        }

        /**
         * @return Pressure of the city if available, otherwise <code>Float.NaN</code>.
         */
        public float getPressure() {
            return this.pressure;
        }

        /**
         * @return Humidity of the city if available, otherwise <code>Float.NaN</code>.
         */
        public float getHumidity() {
            return this.humidity;
        }
    }

    /**
     * <p>
     * Parses weather data and provides methods to get/access the same information.
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
     * @since 2.5.0.3
     */
    public static class Weather implements Serializable {
        private static final String JSON_WEATHER_ID = "id";
        private static final String JSON_WEATHER_MAIN = "main";
        private static final String JSON_WEATHER_DESCRIPTION = "description";
        private static final String JSON_WEATHER_ICON = "icon";

        private final int id;
        private final String name;
        private final String description;
        private final String icon;

        Weather() {
            this.id = Integer.MIN_VALUE;
            this.name = null;
            this.description = null;
            this.icon = null;
        }

        Weather(JSONObject jsonObj) {
            this.id = jsonObj.optInt(JSON_WEATHER_ID, Integer.MIN_VALUE);
            this.name = jsonObj.optString(JSON_WEATHER_MAIN, null);
            this.description = jsonObj.optString(JSON_WEATHER_DESCRIPTION, null);
            this.icon = jsonObj.optString(JSON_WEATHER_ICON, null);
        }

        /**
         * Tells if weather's code is available or not.
         *
         * @return <code>true</code> if data available, otherwise <code>false</code>.
         */
        public boolean hasWeatherCode() {
            return this.id != Integer.MIN_VALUE;
        }

        /**
         * Tells if weather's name is available or not.
         *
         * @return <code>true</code> if data available, otherwise <code>false</code>.
         */
        public boolean hasWeatherName() {
            return this.name != null && (! "".equals(this.name));
        }

        /**
         * Tells if weather's description is available or not.
         *
         * @return <code>true</code> if data available, otherwise <code>false</code>.
         */
        public boolean hasWeatherDescription() {
            return this.description != null && (! "".equals(this.description));
        }

        /**
         * Tells if name of weather's icon is available or not.
         *
         * @return <code>true</code> if data available, otherwise <code>false</code>.
         */
        public boolean hasWeatherIconName() {
            return this.icon != null && (! "".equals(this.icon));
        }

        /**
         * @return Code for weather of the city if available, otherwise <code>Integer.MIN_VALUE</code>.
         */
        public int getWeatherCode() {
            return this.id;
        }

        /**
         * @return Name for weather of the city if available, otherwise <code>null</code>.
         */
        public String getWeatherName() {
            return this.name;
        }

        /**
         * @return Description for weather of the city if available, otherwise <code>null</code>.
         */
        public String getWeatherDescription() {
            return this.description;
        }

        /**
         * @return Name of icon for weather of the city if available, otherwise <code>null</code>.
         */
        public String getWeatherIconName() {
            return this.icon;
        }
    }

    /**
     * <p>
     * Provides default behaviours for Wind.
     * </p>
     *
     * @author Ashutosh Kumar Singh
     * @version 2013/12/23
     * @since 2.5.0.1
     */
    abstract public static class Wind implements Serializable {
        private static final String JSON_WIND_SPEED = "speed";
        private static final String JSON_WIND_DEGREE = "deg";

        private final float speed;
        private final float degree;

        Wind() {
            this.speed = Float.NaN;
            this.degree = Float.NaN;
        }

        Wind(JSONObject jsonObj) {
            this.speed = (float) jsonObj.optDouble(JSON_WIND_SPEED, Double.NaN);
            this.degree = (float) jsonObj.optDouble(JSON_WIND_DEGREE, Double.NaN);
        }

        /**
         * Tells if speed of wind in the city is available or not.
         *
         * @return <code>true</code> if data available, otherwise <code>false</code>.
         */
        public boolean hasWindSpeed() {
            return !Float.isNaN(this.speed);
        }

        /**
         * Tells if degree (degree gives direction) of wind in the city is available or not.
         *
         * @return <code>true</code> if data available, otherwise <code>false</code>.
         */
        public boolean hasWindDegree() {
            return this.hasWindSpeed() && (! Float.isNaN(this.degree));
        }

        /**
         * @return Speed of wind in the city if available, otherwise <code>Float.NaN</code>.
         */
        public float getWindSpeed() {
            return this.speed;
        }

        /**
         * @return Degree of wind in the city if available, otherwise <code>Float.NaN</code>.
         */
        public float getWindDegree() {
            return this.degree;
        }
    }
}

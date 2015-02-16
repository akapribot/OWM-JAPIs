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

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Parses current weather data and provides methods to get/access the same information.
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
 * @see <a href="http://openweathermap.org/current">OWM's Current Weather API</a>
 * @since 2.5.0.1
 */
public class CurrentWeather extends AbstractWeather {
    /*
    JSON Keys
     */
    private static final String JSON_RAIN = "rain";
    private static final String JSON_SNOW = "snow";
    private static final String JSON_SYS = "sys";
    private static final String JSON_BASE = "base";
    private static final String JSON_CITY_ID = "id";
    private static final String JSON_CITY_NAME = "name";

    /*
    Instance variables
     */
    private final String base;
    private final long cityId;
    private final String cityName;

    private final Clouds clouds;
    private final Coord coord;
    private final Main main;
    private final Rain rain;
    private final Snow snow;
    private final Sys sys;
    private final Wind wind;

    /*
    Constructor
     */
    CurrentWeather(JSONObject jsonObj) {
        super(jsonObj);

        this.base = (jsonObj != null) ? jsonObj.optString(JSON_BASE, null) : null;
        this.cityId = (jsonObj != null) ? jsonObj.optLong(JSON_CITY_ID, Long.MIN_VALUE) : Long.MIN_VALUE;
        this.cityName = (jsonObj != null) ? jsonObj.optString(JSON_CITY_NAME, null) : null;

        JSONObject cloudsObj = (jsonObj != null) ? jsonObj.optJSONObject(JSON_CLOUDS) : null;
        this.clouds = (cloudsObj != null) ? new Clouds(cloudsObj) : null;

        JSONObject coordObj = (jsonObj != null) ? jsonObj.optJSONObject(JSON_COORD) : null;
        this.coord = (coordObj != null) ? new Coord(coordObj) : null;

        JSONObject mainObj = (jsonObj != null) ? jsonObj.optJSONObject(JSON_MAIN) : null;
        this.main = (mainObj != null) ? new Main(mainObj) : null;

        JSONObject rainObj = (jsonObj != null) ? jsonObj.optJSONObject(JSON_RAIN) : null;
        this.rain = (rainObj != null) ? new Rain(rainObj) : null;

        JSONObject snowObj = (jsonObj != null) ? jsonObj.optJSONObject(JSON_SNOW) : null;
        this.snow = (snowObj != null) ? new Snow(snowObj) : null;

        JSONObject sysObj = (jsonObj != null) ? jsonObj.optJSONObject(JSON_SYS) : null;
        this.sys = (sysObj != null) ? new Sys(sysObj) : null;

        JSONObject windObj = (jsonObj != null) ? jsonObj.optJSONObject(JSON_WIND) : null;
        this.wind = (windObj != null) ? new Wind(windObj) : null;
    }

    /**
     * @return <code>true</code> if base station is available, otherwise <code>false</code>.
     */
    public boolean hasBaseStation() {
        return this.base != null && (! "".equals(this.base));
    }

    /**
     * @return <code>true</code> if city code is available, otherwise <code>false</code>.
     */
    public boolean hasCityCode() {
        return this.cityId != Long.MIN_VALUE;
    }

    /**
     * @return <code>true</code> if city name is available, otherwise <code>false</code>.
     */
    public boolean hasCityName() {
        return this.cityName != null && (! "".equals(this.cityName));
    }

    /**
     * @return <code>true</code> if Clouds instance is available, otherwise <code>false</code>.
     */
    public boolean hasCloudsInstance() {
        return clouds != null;
    }

    /**
     * @return <code>true</code> if Coord instance is available, otherwise <code>false</code>.
     */
    public boolean hasCoordInstance() {
        return coord != null;
    }

    /**
     * @return <code>true</code> if Main instance is available, otherwise <code>false</code>.
     */
    public boolean hasMainInstance() {
        return main != null;
    }

    /**
     * @return <code>true</code> if Rain instance is available, otherwise <code>false</code>.
     */
    public boolean hasRainInstance() {
        return rain != null;
    }

    /**
     * @return <code>true</code> if Snow instance is available, otherwise <code>false</code>.
     */
    public boolean hasSnowInstance() {
        return snow != null;
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

    /**
     * @return Base station if available, otherwise <code>null</code>.
     */
    public String getBaseStation() {
        return this.base;
    }

    /**
     * @return City code if available, otherwise <code>Long.MIN_VALUE</code>.
     */
    public long getCityCode() {
        return this.cityId;
    }

    /**
     * @return City name if available, otherwise <code>null</code>.
     */
    public String getCityName() {
        return this.cityName;
    }

    /**
     * @return Clouds instance if available, otherwise <code>null</code>.
     */
    public Clouds getCloudsInstance() {
        return this.clouds;
    }

    /**
     * @return Coord instance if available, otherwise <code>null</code>.
     */
    public Coord getCoordInstance() {
        return this.coord;
    }

    /**
     * @return Main instance if available, otherwise <code>null</code>.
     */
    public Main getMainInstance() {
        return this.main;
    }

    /**
     * @return Rain instance if available, otherwise <code>null</code>.
     */
    public Rain getRainInstance() {
        return this.rain;
    }

    /**
     * @return Snow instance if available, otherwise <code>null</code>.
     */
    public Snow getSnowInstance() {
        return this.snow;
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
    public static class Clouds extends AbstractWeather.Clouds {

        Clouds() {
            super();
        }

        Clouds(JSONObject jsonObj) {
            super(jsonObj);
        }
    }

    /**
     * <p>
     * Parses coordination data and provides methods to get/access the same information.
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
    public static class Coord extends AbstractWeather.Coord {

        Coord() {
            super();
        }

        Coord(JSONObject jsonObj) {
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
    public static class Main extends AbstractWeather.Main {

        Main() {
            super();
        }

        Main(JSONObject jsonObj) {
            super(jsonObj);
        }
    }

    /**
     * <p>
     * Parses rain data and provides methods to get/access the same information.
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
    public static class Rain implements Serializable {

        private static final String JSON_RAIN_1HOUR = "1h";
        private static final String JSON_RAIN_3HOUR = "3h";

        private final float rain1h;
        private final float rain3h;

        Rain() {
            this.rain1h = Float.NaN;
            this.rain3h = Float.NaN;
        }

        Rain(JSONObject jsonObj) {
            this.rain1h = (float) jsonObj.optDouble(JSON_RAIN_1HOUR, Double.NaN);
            this.rain3h = (float) jsonObj.optDouble(JSON_RAIN_3HOUR, Double.NaN);
        }

        public boolean hasRain1h() {
            return !Float.isNaN(this.rain1h);
        }

        public boolean hasRain3h() {
            return !Float.isNaN(this.rain3h);
        }

        public float getRain1h() {
            return this.rain1h;
        }

        public float getRain3h() {
            return this.rain3h;
        }
    }

    /**
     * <p>
     * Parses snow data and provides methods to get/access the same information.
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
     * @version 2015/01/28
     * @since 2.5.0.4
     */
    public static class Snow implements Serializable {

        private static final String JSON_SNOW_1HOUR = "1h";
        private static final String JSON_SNOW_3HOUR = "3h";

        private final float snow1h;
        private final float snow3h;

        Snow() {
            this.snow1h = Float.NaN;
            this.snow3h = Float.NaN;
        }

        Snow(JSONObject jsonObj) {
            this.snow1h = (float) jsonObj.optDouble(JSON_SNOW_1HOUR, Double.NaN);
            this.snow3h = (float) jsonObj.optDouble(JSON_SNOW_3HOUR, Double.NaN);
        }

        public boolean hasSnow1h() {
            return !Float.isNaN(this.snow1h);
        }

        public boolean hasSnow3h() {
            return !Float.isNaN(this.snow3h);
        }

        public float getSnow1h() {
            return this.snow1h;
        }

        public float getSnow3h() {
            return this.snow3h;
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

        private static final String JSON_SYS_TYPE = "type";
        private static final String JSON_SYS_ID = "id";
        private static final String JSON_SYS_MESSAGE = "message";
        private static final String JSON_SYS_COUNTRY_CODE = "country";
        private static final String JSON_SYS_SUNRISE = "sunrise";
        private static final String JSON_SYS_SUNSET = "sunset";

        private final int type;
        private final int id;
        private final double message;
        private final String countryCode;
        private final Date sunrise;
        private final Date sunset;

        Sys() {
            this.type = Integer.MIN_VALUE;
            this.id = Integer.MIN_VALUE;
            this.message = Double.NaN;
            this.countryCode = null;
            this.sunrise = null;
            this.sunset = null;
        }

        Sys(JSONObject jsonObj) {
            this.type = jsonObj.optInt(JSON_SYS_TYPE, Integer.MIN_VALUE);
            this.id = jsonObj.optInt(JSON_SYS_ID, Integer.MIN_VALUE);
            this.message = jsonObj.optDouble(JSON_SYS_MESSAGE, Double.NaN);
            this.countryCode = jsonObj.optString(JSON_SYS_COUNTRY_CODE, null);

            long sr_secs = jsonObj.optLong(JSON_SYS_SUNRISE, Long.MIN_VALUE);
            if (sr_secs != Long.MIN_VALUE) {
                this.sunrise = new Date(sr_secs * 1000);
            } else {
                this.sunrise = null;
            }

            long ss_secs = jsonObj.optLong(JSON_SYS_SUNSET, Long.MIN_VALUE);
            if (ss_secs != Long.MIN_VALUE) {
                this.sunset = new Date(ss_secs * 1000);
            } else {
                this.sunset = null;
            }
        }

        public boolean hasType() {
            return this.type != Integer.MIN_VALUE;
        }

        public boolean hasId() {
            return this.id != Integer.MIN_VALUE;
        }

        public boolean hasMessage() {
            return !Double.isNaN(this.message);
        }

        public boolean hasCountryCode() {
            return this.countryCode != null && (! "".equals(this.countryCode));
        }

        public boolean hasSunriseTime() {
            return this.sunrise != null;
        }

        public boolean hasSunsetTime() {
            return this.sunset != null;
        }

        public int getType() {
            return this.type;
        }

        public int getId() {
            return this.id;
        }

        public double getMessage() {
            return this.message;
        }

        public String getCountryCode() {
            return this.countryCode;
        }

        public Date getSunriseTime() {
            return this.sunrise;
        }

        public Date getSunsetTime() {
            return this.sunset;
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

        private static final String JSON_WIND_GUST = "gust";

        private final float gust;

        Wind() {
            super();

            this.gust = Float.NaN;
        }

        Wind(JSONObject jsonObj) {
            super(jsonObj);

            this.gust = (float) jsonObj.optDouble(JSON_WIND_GUST, Double.NaN);
        }

        public boolean hasWindGust() {
            return !Float.isNaN(this.gust);
        }

        public float getWindGust() {
            return this.gust;
        }
    }
}

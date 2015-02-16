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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * <p>
 *     <b>The starting point for all API operations.</b>
 *     If you're new to this API, read the docs for this class first.
 * </p>
 * <p>
 * Lets you access data from OpenWeatherMap.org using its Weather APIs.
 * Henceforth, it's shortened as OWM.org to ease commenting.
 * </p>
 * <p>
 * <b>Sample code:</b><br>
 * <code>OpenWeatherMap.org owm = new OpenWeatherMap("your-api-key");</code><br>
 * <code>OpenWeatherMap.org owm = new OpenWeatherMap(your-units, "your-api-key");</code><br>
 * <code>OpenWeatherMap.org owm = new OpenWeatherMap(your-units, your-language, "your-api-key");</code>
 * </p>
 *
 * @author Ashutosh Kumar Singh <me@aksingh.net>
 * @version 2015-01-17
 * @see <a href="http://openweathermap.org/">OpenWeatherMap.org</a>
 * @see <a href="http://openweathermap.org/api">OpenWeatherMap.org API</a>
 * @since 2.5.0.1
 */
public class OpenWeatherMap {
    /*
    URLs and parameters for OWM.org
     */
    private static final String URL_API = "http://api.openweathermap.org/data/2.5/";
    private static final String URL_CURRENT = "weather?";
    private static final String URL_HOURLY_FORECAST = "forecast?";
    private static final String URL_DAILY_FORECAST = "forecast/daily?";

    private static final String PARAM_COUNT = "cnt=";
    private static final String PARAM_CITY_NAME = "q=";
    private static final String PARAM_CITY_ID = "id=";
    private static final String PARAM_LATITUDE = "lat=";
    private static final String PARAM_LONGITUDE = "lon=";
    private static final String PARAM_MODE = "mode=";
    private static final String PARAM_UNITS = "units=";
    private static final String PARAM_APPID = "appId=";
    private static final String PARAM_LANG = "lang=";

    /*
    Instance Variables
     */
    private final OWMAddress owmAddress;
    private final OWMResponse owmResponse;
    private final OWMProxy owmProxy;

    /**
     * Constructor
     *
     * @param apiKey API key from OWM.org
     * @see <a href="http://openweathermap.org/appid">OWM.org API Key</a>
     */
    public OpenWeatherMap(String apiKey) {
        this(Units.IMPERIAL, Language.ENGLISH, apiKey);
    }

    /**
     * Constructor
     *
     * @param units  Any constant from Units
     * @param apiKey API key from OWM.org
     * @see net.aksingh.owmjapis.OpenWeatherMap.Units
     * @see <a href="http://openweathermap.org/appid">OWM.org API Key</a>
     */
    public OpenWeatherMap(Units units, String apiKey) {
        this(units, Language.ENGLISH, apiKey);
    }

    /**
     * Constructor
     *
     * @param units  Any constant from Units
     * @param lang   Any constant from Language
     * @param apiKey API key from OWM.org
     * @see net.aksingh.owmjapis.OpenWeatherMap.Units
     * @see net.aksingh.owmjapis.OpenWeatherMap.Language
     * @see <a href="http://openweathermap.org/current#multi">OWM.org's Multilingual support</a>
     * @see <a href="http://openweathermap.org/appid">OWM.org's API Key</a>
     */
    public OpenWeatherMap(Units units, Language lang, String apiKey) {
        this.owmAddress = new OWMAddress(units, lang, apiKey);
        this.owmProxy = new OWMProxy(null, Integer.MIN_VALUE, null, null);
        this.owmResponse = new OWMResponse(owmAddress, owmProxy);
    }

    /*
    Getters
     */
    public OWMAddress getOwmAddressInstance() {
        return owmAddress;
    }

    public String getApiKey() {
        return owmAddress.getAppId();
    }

    public Units getUnits() {
        return owmAddress.getUnits();
    }

    public String getMode() {
        return owmAddress.getMode();
    }

    public Language getLang() {
        return owmAddress.getLang();
    }

    /*
    Setters
     */

    /**
     * Set units for getting data from OWM.org
     *
     * @param units Any constant from Units
     * @see net.aksingh.owmjapis.OpenWeatherMap.Units
     */
    public void setUnits(Units units) {
        owmAddress.setUnits(units);
    }

    /**
     * Set API key for getting data from OWM.org
     *
     * @param appId API key from OWM.org
     * @see <a href="http://openweathermap.org/appid">OWM.org's API Key</a>
     */
    public void setApiKey(String appId) {
        owmAddress.setAppId(appId);
    }

    /**
     * Set language for getting data from OWM.org
     *
     * @param lang Any constant from Language
     * @see net.aksingh.owmjapis.OpenWeatherMap.Language
     * @see <a href="http://openweathermap.org/current#multi">OWM.org's Multilingual support</a>
     */
    public void setLang(Language lang) {
        owmAddress.setLang(lang);
    }

    /**
     * Set proxy for getting data from OWM.org
     *
     * @param ip IP address of the proxy
     * @param port Port address of the proxy
     */
    public void setProxy(String ip, int port) {
        owmProxy.setIp(ip);
        owmProxy.setPort(port);
        owmProxy.setUser(null);
        owmProxy.setPass(null);
    }

    /**
     * Set proxy and authentication details for getting data from OWM.org
     *
     * @param ip IP address of the proxy
     * @param port Port address of the proxy
     * @param user User name for the proxy if required
     * @param pass Password for the proxy if required
     */
    public void setProxy(String ip, int port, String user, String pass) {
        owmProxy.setIp(ip);
        owmProxy.setPort(port);
        owmProxy.setUser(user);
        owmProxy.setPass(pass);
    }

    public CurrentWeather currentWeatherByCityName(String cityName)
            throws IOException, JSONException {
        String response = owmResponse.currentWeatherByCityName(cityName);
        return this.currentWeatherFromRawResponse(response);
    }

    public CurrentWeather currentWeatherByCityName(String cityName, String countryCode)
            throws IOException, JSONException {
        String response = owmResponse.currentWeatherByCityName(cityName, countryCode);
        return this.currentWeatherFromRawResponse(response);
    }

    public CurrentWeather currentWeatherByCityCode(long cityCode)
            throws JSONException {
        String response = owmResponse.currentWeatherByCityCode(cityCode);
        return this.currentWeatherFromRawResponse(response);
    }

    public CurrentWeather currentWeatherByCoordinates(float latitude, float longitude)
            throws JSONException {
        String response = owmResponse.currentWeatherByCoordinates(latitude, longitude);
        return this.currentWeatherFromRawResponse(response);
    }

    public CurrentWeather currentWeatherFromRawResponse(String response)
            throws JSONException {
        JSONObject jsonObj = (response != null) ? new JSONObject(response) : null;
        return new CurrentWeather(jsonObj);
    }

    public HourlyForecast hourlyForecastByCityName(String cityName)
            throws IOException, JSONException {
        String response = owmResponse.hourlyForecastByCityName(cityName);
        return this.hourlyForecastFromRawResponse(response);
    }

    public HourlyForecast hourlyForecastByCityName(String cityName, String countryCode)
            throws IOException, JSONException {
        String response = owmResponse.hourlyForecastByCityName(cityName, countryCode);
        return this.hourlyForecastFromRawResponse(response);
    }

    public HourlyForecast hourlyForecastByCityCode(long cityCode)
            throws JSONException {
        String response = owmResponse.hourlyForecastByCityCode(cityCode);
        return this.hourlyForecastFromRawResponse(response);
    }

    public HourlyForecast hourlyForecastByCoordinates(float latitude, float longitude)
            throws JSONException {
        String response = owmResponse.hourlyForecastByCoordinates(latitude, longitude);
        return this.hourlyForecastFromRawResponse(response);
    }

    public HourlyForecast hourlyForecastFromRawResponse(String response)
            throws JSONException {
        JSONObject jsonObj = (response != null) ? new JSONObject(response) : null;
        return new HourlyForecast(jsonObj);
    }

    public DailyForecast dailyForecastByCityName(String cityName, byte count)
            throws IOException, JSONException {
        String response = owmResponse.dailyForecastByCityName(cityName, count);
        return this.dailyForecastFromRawResponse(response);
    }

    public DailyForecast dailyForecastByCityName(String cityName, String countryCode, byte count)
            throws IOException, JSONException {
        String response = owmResponse.dailyForecastByCityName(cityName, countryCode, count);
        return this.dailyForecastFromRawResponse(response);
    }

    public DailyForecast dailyForecastByCityCode(long cityCode, byte count)
            throws JSONException {
        String response = owmResponse.dailyForecastByCityCode(cityCode, count);
        return this.dailyForecastFromRawResponse(response);
    }

    public DailyForecast dailyForecastByCoordinates(float latitude, float longitude, byte count)
            throws JSONException {
        String response = owmResponse.dailyForecastByCoordinates(latitude, longitude, count);
        return this.dailyForecastFromRawResponse(response);
    }

    public DailyForecast dailyForecastFromRawResponse(String response)
            throws JSONException {
        JSONObject jsonObj = (response != null) ? new JSONObject(response) : null;
        return new DailyForecast(jsonObj);
    }

    /**
     * Units that can be set for getting data from OWM.org
     *
     * @since 2.5.0.3
     */
    public static enum Units {
        METRIC("metric"),
        IMPERIAL("imperial");

        private final String unit;

        Units(String unit) {
            this.unit = unit;
        }
    }

    /**
     * Languages that can be set for getting data from OWM.org
     *
     * @since 2.5.0.3
     */
    public static enum Language {
        ENGLISH("en"),
        RUSSIAN("ru"),
        ITALIAN("it"),
        SPANISH("es"),
        UKRAINIAN("uk"),
        GERMAN("de"),
        PORTUGUESE("pt"),
        ROMANIAN("ro"),
        POLISH("pl"),
        FINNISH("fi"),
        DUTCH("nl"),
        FRENCH("FR"),
        BULGARIAN("bg"),
        SWEDISH("sv"),
        CHINESE_TRADITIONAL("zh_tw"),
        CHINESE_SIMPLIFIED("zh"),
        TURKISH("tr"),
        CROATIAN("hr"),
        CATALAN("ca");

        private final String lang;

        Language(String lang) {
            this.lang = lang;
        }
    }

    /**
     * Proxifies the default HTTP requests
     *
     * @since 2.5.0.5
     */
    private static class OWMProxy {
        private String ip;
        private int port;
        private String user;
        private String pass;

        private OWMProxy(String ip, int port, String user, String pass) {
            this.ip = ip;
            this.port = port;
            this.user = user;
            this.pass = pass;
        }

        public Proxy getProxy() {
            Proxy proxy = null;

            if (ip != null && (! "".equals(ip)) && port != Integer.MIN_VALUE) {
                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            }

            if (user != null && (! "".equals(user)) && pass != null && (! "".equals(pass))) {
                Authenticator.setDefault(getAuthenticatorInstance(user, pass));
            }

            return proxy;
        }

        private Authenticator getAuthenticatorInstance(final String user, final String pass) {
            Authenticator authenticator = new Authenticator() {

                public PasswordAuthentication getPasswordAuthentication() {
                    return (new PasswordAuthentication(user, pass.toCharArray()));
                }
            };

            return authenticator;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }
    }

    /**
     * Generates addresses for accessing the information from OWM.org
     *
     * @since 2.5.0.3
     */
    public static class OWMAddress {
        private static final String MODE = "json";
        private static final String ENCODING = "UTF-8";

        private String mode;
        private Units units;
        private String appId;
        private Language lang;

        /*
        Constructors
         */
        private OWMAddress(String appId) {
            this(Units.IMPERIAL, Language.ENGLISH, appId);
        }

        private OWMAddress(Units units, String appId) {
            this(units, Language.ENGLISH, appId);
        }

        private OWMAddress(Units units, Language lang, String appId) {
            this.mode = MODE;
            this.units = units;
            this.lang = lang;
            this.appId = appId;
        }

        /*
        Getters
         */
        private String getAppId() {
            return this.appId;
        }

        private Units getUnits() {
            return this.units;
        }

        private String getMode() {
            return this.mode;
        }

        private Language getLang() {
            return this.lang;
        }

        /*
        Setters
         */
        private void setUnits(Units units) {
            this.units = units;
        }

        private void setAppId(String appId) {
            this.appId = appId;
        }

        private void setLang(Language lang) {
            this.lang = lang;
        }

        /*
        Addresses for current weather
         */
        public String currentWeatherByCityName(String cityName) throws UnsupportedEncodingException {
            return new StringBuilder()
                    .append(URL_API).append(URL_CURRENT)
                    .append(PARAM_CITY_NAME).append(URLEncoder.encode(cityName, ENCODING)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_LANG).append(this.lang).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }

        public String currentWeatherByCityName(String cityName, String countryCode) throws UnsupportedEncodingException {
            return currentWeatherByCityName(new StringBuilder()
                    .append(cityName).append(",").append(countryCode)
                    .toString());
        }

        public String currentWeatherByCityCode(long cityCode) {
            return new StringBuilder()
                    .append(URL_API).append(URL_CURRENT)
                    .append(PARAM_CITY_ID).append(Long.toString(cityCode)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_LANG).append(this.lang).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }

        public String currentWeatherByCoordinates(float latitude, float longitude) {
            return new StringBuilder()
                    .append(URL_API).append(URL_CURRENT)
                    .append(PARAM_LATITUDE).append(Float.toString(latitude)).append("&")
                    .append(PARAM_LONGITUDE).append(Float.toString(longitude)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }

        /*
        Addresses for hourly forecasts
         */
        public String hourlyForecastByCityName(String cityName) throws UnsupportedEncodingException {
            return new StringBuilder()
                    .append(URL_API).append(URL_HOURLY_FORECAST)
                    .append(PARAM_CITY_NAME).append(URLEncoder.encode(cityName, ENCODING)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_LANG).append(this.lang).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }

        public String hourlyForecastByCityName(String cityName, String countryCode) throws UnsupportedEncodingException {
            return hourlyForecastByCityName(new StringBuilder()
                    .append(cityName).append(",").append(countryCode)
                    .toString());
        }

        public String hourlyForecastByCityCode(long cityCode) {
            return new StringBuilder()
                    .append(URL_API).append(URL_HOURLY_FORECAST)
                    .append(PARAM_CITY_ID).append(Long.toString(cityCode)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_LANG).append(this.lang).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }

        public String hourlyForecastByCoordinates(float latitude, float longitude) {
            return new StringBuilder()
                    .append(URL_API).append(URL_HOURLY_FORECAST)
                    .append(PARAM_LATITUDE).append(Float.toString(latitude)).append("&")
                    .append(PARAM_LONGITUDE).append(Float.toString(longitude)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_LANG).append(this.lang).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }

        /*
        Addresses for daily forecasts
         */
        public String dailyForecastByCityName(String cityName, byte count) throws UnsupportedEncodingException {
            return new StringBuilder()
                    .append(URL_API).append(URL_DAILY_FORECAST)
                    .append(PARAM_CITY_NAME).append(URLEncoder.encode(cityName, ENCODING)).append("&")
                    .append(PARAM_COUNT).append(Byte.toString(count)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_LANG).append(this.lang).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }

        public String dailyForecastByCityName(String cityName, String countryCode, byte count) throws UnsupportedEncodingException {
            return dailyForecastByCityName(new StringBuilder()
                    .append(cityName).append(",").append(countryCode)
                    .toString(), count);
        }

        public String dailyForecastByCityCode(long cityCode, byte count) {
            return new StringBuilder()
                    .append(URL_API).append(URL_DAILY_FORECAST)
                    .append(PARAM_CITY_ID).append(Long.toString(cityCode)).append("&")
                    .append(PARAM_COUNT).append(Byte.toString(count)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_LANG).append(this.lang).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }

        public String dailyForecastByCoordinates(float latitude, float longitude, byte count) {
            return new StringBuilder()
                    .append(URL_API).append(URL_DAILY_FORECAST)
                    .append(PARAM_LATITUDE).append(Float.toString(latitude)).append("&")
                    .append(PARAM_LONGITUDE).append(Float.toString(longitude)).append("&")
                    .append(PARAM_COUNT).append(Byte.toString(count)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_LANG).append(this.lang).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }
    }

    /**
     * Requests OWM.org for data and provides back the incoming response.
     *
     * @since 2.5.0.3
     */
    private static class OWMResponse {
        private final OWMAddress owmAddress;
        private final OWMProxy owmProxy;

        public OWMResponse(OWMAddress owmAddress, OWMProxy owmProxy) {
            this.owmAddress = owmAddress;
            this.owmProxy = owmProxy;
        }

        /*
        Responses for current weather
         */
        public String currentWeatherByCityName(String cityName) throws UnsupportedEncodingException {
            String address = owmAddress.currentWeatherByCityName(cityName);
            return httpGET(address);
        }

        public String currentWeatherByCityName(String cityName, String countryCode) throws UnsupportedEncodingException {
            String address = owmAddress.currentWeatherByCityName(cityName, countryCode);
            return httpGET(address);
        }

        public String currentWeatherByCityCode(long cityCode) {
            String address = owmAddress.currentWeatherByCityCode(cityCode);
            return httpGET(address);
        }

        public String currentWeatherByCoordinates(float latitude, float longitude) {
            String address = owmAddress.currentWeatherByCoordinates(latitude, longitude);
            return httpGET(address);
        }

        /*
        Responses for hourly forecasts
         */
        public String hourlyForecastByCityName(String cityName) throws UnsupportedEncodingException {
            String address = owmAddress.hourlyForecastByCityName(cityName);
            return httpGET(address);
        }

        public String hourlyForecastByCityName(String cityName, String countryCode) throws UnsupportedEncodingException {
            String address = owmAddress.hourlyForecastByCityName(cityName, countryCode);
            return httpGET(address);
        }

        public String hourlyForecastByCityCode(long cityCode) {
            String address = owmAddress.hourlyForecastByCityCode(cityCode);
            return httpGET(address);
        }

        public String hourlyForecastByCoordinates(float latitude, float longitude) {
            String address = owmAddress.hourlyForecastByCoordinates(latitude, longitude);
            return httpGET(address);
        }

        /*
        Responses for daily forecasts
         */
        public String dailyForecastByCityName(String cityName, byte count) throws UnsupportedEncodingException {
            String address = owmAddress.dailyForecastByCityName(cityName, count);
            return httpGET(address);
        }

        public String dailyForecastByCityName(String cityName, String countryCode, byte count) throws UnsupportedEncodingException {
            String address = owmAddress.dailyForecastByCityName(cityName, countryCode, count);
            return httpGET(address);
        }

        public String dailyForecastByCityCode(long cityCode, byte count) {
            String address = owmAddress.dailyForecastByCityCode(cityCode, count);
            return httpGET(address);
        }

        public String dailyForecastByCoordinates(float latitude, float longitude, byte count) {
            String address = owmAddress.dailyForecastByCoordinates(latitude, longitude, count);
            return httpGET(address);
        }

        /**
         * Implements HTTP's GET method
         *
         * @param requestAddress Address to be loaded
         * @return Response if successful, else <code>null</code>
         * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html">HTTP - (9.3) GET</a>
         */
        private String httpGET(String requestAddress) {
            URL request;
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            String tmpStr;
            String response = null;

            try {
                request = new URL(requestAddress);

                if (owmProxy.getProxy() != null) {
                    connection = (HttpURLConnection) request.openConnection(owmProxy.getProxy());
                } else {
                    connection = (HttpURLConnection) request.openConnection();
                }

                connection.setRequestMethod("GET");
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(false);
                connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String encoding = connection.getContentEncoding();

                    try {
                        if (encoding != null && "gzip".equalsIgnoreCase(encoding)) {
                            reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
                        } else if (encoding != null && "deflate".equalsIgnoreCase(encoding)) {
                            reader = new BufferedReader(new InputStreamReader(new InflaterInputStream(connection.getInputStream(), new Inflater(true))));
                        } else {
                            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        }

                        while ((tmpStr = reader.readLine()) != null) {
                            response = tmpStr;
                        }
                    } catch (IOException e) {
                        System.err.println("Error: " + e.getMessage());
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                System.err.println("Error: " + e.getMessage());
                            }
                        }
                    }
                } else { // if HttpURLConnection is not okay
                    try {
                        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        while ((tmpStr = reader.readLine()) != null) {
                            response = tmpStr;
                        }
                    } catch (IOException e) {
                        System.err.println("Error: " + e.getMessage());
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                System.err.println("Error: " + e.getMessage());
                            }
                        }
                    }

                    // if response is bad
                    System.err.println("Bad Response: " + response + "\n");
                    return null;
                }
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                response = null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return response;
        }
    }
}

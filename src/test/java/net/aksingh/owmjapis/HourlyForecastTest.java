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

import java.io.IOException;

/**
 * <p>
 * Tests the HourlyForecast's functionality.
 * </p>
 *
 * @author Ashutosh Kumar Singh
 * @version 2015/01/22
 * @since 2.5.0.3
 */
public class HourlyForecastTest {

    public static void main(String[] args) throws IOException {
        OpenWeatherMap owm = new OpenWeatherMap("");
        HourlyForecast hf = owm.hourlyForecastByCityName("London, UK");

        if (!hf.isValid()) {
            System.out.println("Reponse is inValid!");
        } else {
            System.out.println("Reponse is Valid!");
            System.out.println();

            if (hf.hasCityInstance()) {
                HourlyForecast.City city = hf.getCityInstance();
                if (city.hasCityName()) {
                    if (city.hasCityCode()) {
                        System.out.println("City code: " + city.getCityCode());
                    }
                    if (city.hasCityName()) {
                        System.out.println("City name: " + city.getCityName());
                    }
                    System.out.println();
                }
            }

            System.out.println("Total forecast instances: " + hf.getForecastCount());
            System.out.println();

            for (int i = 0; i < hf.getForecastCount(); i++) {
                HourlyForecast.Forecast forecast = hf.getForecastInstance(i);

                System.out.println("*** Forecast instance number " + (i+1) + " ***");

                if (forecast.hasDateTime()) {
                    System.out.println(forecast.getDateTime());
                }

                System.out.println();
            }
        }
    }
}

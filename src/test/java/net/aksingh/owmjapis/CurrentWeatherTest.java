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
 * Tests the CurrentWeather's functionality.
 * </p>
 *
 * @author Ashutosh Kumar Singh
 * @version 2014/12/26
 * @since 2.5.0.3
 */
public class CurrentWeatherTest {

    public static void main(String[] args) throws IOException {
        OpenWeatherMap owm = new OpenWeatherMap("");
        CurrentWeather cw = owm.currentWeatherByCityName("London, UK");

        if (!cw.isValid()) {
            System.out.println("Reponse is inValid!");
        } else {
            System.out.println("Reponse is Valid!");
            System.out.println();

            if (cw.hasBaseStation()) {
                System.out.println("Base station: " + cw.getBaseStation());
            }
            if (cw.hasDateTime()) {
                System.out.println("Date time: " + cw.getDateTime());
            }
            System.out.println();


            if (cw.hasCityCode()) {
                System.out.println("City code: " + cw.getCityCode());
            }
            if (cw.hasCityName()) {
                System.out.println("City name: " + cw.getCityName());
            }
            System.out.println();
        }
    }
}

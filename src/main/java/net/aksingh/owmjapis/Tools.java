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

/**
 * <p>
 * Provides methods for conversions, etc.
 * </p>
 *
 * @author Ashutosh Kumar Singh
 * @version 2014-12-27
 * @since 2.5.0.1
 */
public class Tools {
    /**
     * <p>
     * Converts degree to direction.
     * </p>
     *
     * @param degree Degree of wind as received from OWM.org
     * @return Direction
     * @throws IllegalArgumentException Degree should be between 0 and 360.
     */
    public String convertDegree2Direction(float degree)
            throws IllegalArgumentException {
        String direction;

        // degree should be between 0 and 360
        if ((degree < 0.0f) || (degree > 360.0f)) {
            throw new IllegalArgumentException("Degree cannot be less than 0 or more than 360.");
        }

        if (degree <= 11.25f) {
            direction = "N";
        } else if (degree <= 33.75f) {
            direction = "NNE";
        } else if (degree <= 56.25f) {
            direction = "NE";
        } else if (degree <= 78.75f) {
            direction = "ENE";
        } else if (degree <= 101.25f) {
            direction = "E";
        } else if (degree <= 123.75f) {
            direction = "ESE";
        } else if (degree <= 146.25f) {
            direction = "SE";
        } else if (degree <= 168.75f) {
            direction = "SSE";
        } else if (degree <= 191.25f) {
            direction = "S";
        } else if (degree <= 213.75f) {
            direction = "SSW";
        } else if (degree <= 236.25f) {
            direction = "SW";
        } else if (degree <= 258.75f) {
            direction = "WSW";
        } else if (degree <= 281.25f) {
            direction = "W";
        } else if (degree <= 303.75f) {
            direction = "WNW";
        } else if (degree <= 326.25f) {
            direction = "NW";
        } else if (degree <= 348.75f) {
            direction = "NNW";
        } else {
            direction = "N";
        }

        return direction;
    }
}

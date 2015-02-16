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

/**
 * <p>
 * Provides default behaviours and implementations for the response from OWM.org
 * </p>
 *
 * @author Ashutosh Kumar Singh
 * @version 2014/12/28
 * @since 2.5.0.3
 */
abstract class AbstractResponse implements Serializable {
    /*
    JSON Keys
     */
    private static final String JSON_RESPONSE_CODE = "cod";

    /*
    Instance variables
     */
    private final int responseCode;
    private final String rawResponse;

    /*
    Constructors
     */
    AbstractResponse() {
        this.rawResponse = null;
        this.responseCode = Integer.MIN_VALUE;
    }

    AbstractResponse(JSONObject jsonObj) {
        this.rawResponse = (jsonObj != null) ? jsonObj.toString() : null;
        this.responseCode = (jsonObj != null) ? jsonObj.optInt(JSON_RESPONSE_CODE, Integer.MIN_VALUE) : Integer.MIN_VALUE;
    }

    /**
     * @return <code>true</code> if response is valid (downloaded and parsed correctly), otherwise <code>false</code>.
     */
    public boolean isValid() {
        return this.responseCode == 200;
    }

    /**
     * @return <code>true</code> if response code is available, otherwise <code>false</code>.
     */
    public boolean hasResponseCode() {
        return this.responseCode != Integer.MIN_VALUE;
    }

    /**
     * @return <code>true</code> if raw response is available, otherwise <code>false</code>.
     */
    public boolean hasRawResponse() {
        return this.rawResponse != null;
    }

    /**
     * @return Response code if available, otherwise <code>Integer.MIN_VALUE</code>.
     */
    public int getResponseCode() {
        return this.responseCode;
    }

    /**
     * @return Raw response if available, otherwise <code>null</code>.
     */
    public String getRawResponse() {
        return this.rawResponse;
    }
}

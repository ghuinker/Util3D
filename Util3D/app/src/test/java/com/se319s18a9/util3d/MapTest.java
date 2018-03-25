package com.se319s18a9.util3d;

import com.se319s18a9.util3d.backend.Map;

import org.json.*;
import org.junit.*;

import dalvik.annotation.TestTarget;

import static junit.framework.Assert.*;

/**
 * Created by malco on 3/22/2018.
 */

public class MapTest {
    Map testMap;

    @Before
    public void setup() {
        testMap = new Map();
        testMap.addNewLine("electric");
    }

    @Test
    public void addLineTest() {
        assertEquals("electric", testMap.getLines().get(0).getType());
    }

    @Test
    public void setPointTest() {
        testMap.setSavedPoint(testMap.getLines().get(0).getNullHeadPoint());
        assertEquals(testMap.getLines().get(0).getNullHeadPoint(), testMap.getSavedPoint());
    }

    @Test
    public void mapJSONTest() {
        String temp = null;
        try {
            temp = testMap.writeToJSON();
        } catch(JSONException e) {
            assertTrue(false);
        }

        Map clone = new Map();
        try {
            clone.readFromJSON(temp);
        } catch(JSONException e) {
            assertTrue(false);
        }

        assertEquals(testMap, clone);
    }

}

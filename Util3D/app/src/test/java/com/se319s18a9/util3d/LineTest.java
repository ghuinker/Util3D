package com.se319s18a9.util3d;

import com.se319s18a9.util3d.backend.ConnectedPoint;
import com.se319s18a9.util3d.backend.Line;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;

import static junit.framework.Assert.*;

/**
 * Created by malco on 3/22/2018.
 */

public class LineTest {
    Line testLine;
    @Before
    public void setup() {
        testLine = new Line("dummy");
    }

    @Test
    public void createTest() {
        assertEquals("dummy", testLine.getType());
    }

    @Test
    public void lineJSONTest() {
        JSONObject temp = null;
        try {
            temp = testLine.writeToJSON();
        } catch(JSONException e) {
            assertTrue(false);
        }

        Line clone = new Line(null);
        try {
            clone.readFromJSON(temp);
        } catch(JSONException e) {
            assertTrue(false);
        }

        assertEquals(testLine, clone);
    }
}

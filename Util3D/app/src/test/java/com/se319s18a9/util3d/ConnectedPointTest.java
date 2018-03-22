package com.se319s18a9.util3d;

import com.google.android.gms.maps.model.LatLng;
import com.se319s18a9.util3d.backend.ConnectedPoint;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;

import static junit.framework.Assert.*;

/**
 * Created by malco on 3/22/2018.
 */

public class ConnectedPointTest {

    ConnectedPoint testPoint;
    ConnectedPoint testPointJr;
    ConnectedPoint testPointJrTwo;
    ConnectedPoint testPointJrTheSecond;

    @Before
    public void setup(){
        testPoint = new ConnectedPoint(null, 25, 25);
        testPointJr = new ConnectedPoint(testPoint);
        testPointJrTwo = new ConnectedPoint(testPoint);
        testPointJrTheSecond = new ConnectedPoint(testPointJr);
    }

    @Test
    public void createPointTest() {
        assertEquals(25.0, testPoint.getLatitude());
        assertEquals(25.0, testPoint.getLongitude());
        LatLng temp = testPoint.getLatLng();
        assertEquals(25.0, temp.latitude);
        assertEquals(25.0, temp.longitude);
    }

    @Test
    public void changeLatLongTest() {
        testPoint.move(30, 30);
        assertEquals(30.0, testPoint.getLatitude());
        assertEquals(30.0, testPoint.getLongitude());
        LatLng temp = testPoint.getLatLng();
        assertEquals(30.0, temp.latitude);
        assertEquals(30.0, temp.longitude);
        testPointJr.move(40, 40);
        assertEquals(40.0, testPointJr.getLatitude());
        assertEquals(40.0, testPointJr.getLongitude());
        temp = testPointJr.getLatLng();
        assertEquals(40.0, temp.latitude);
        assertEquals(40.0, temp.longitude);
    }

    @Test
    public void isRootTest() {
        assertEquals(true, testPoint.isRoot());
        assertEquals(false, testPointJr.isRoot());
    }

    @Test
    public void isBranchPointTest() {
        assertEquals(true, testPoint.isBranchPoint());
        assertEquals(false, testPointJrTwo.isBranchPoint());
        assertEquals(false, testPointJr.isBranchPoint());
    }

    @Test
    public void insertPointTest() {
        testPointJr.insertPointBetweenThisPointAndParent(45, 45);
        ConnectedPoint temp = testPointJr.getParentPoint();
        assertTrue(new LatLng(45,45).equals(temp.getLatLng()));
    }

    @Test
    public void addChildTest() {
        testPoint.addAChild(50, 50);
        LatLng test = new LatLng(50, 50);
        assertTrue(test.equals(testPoint.getChildren().get(0).getLatLng()));
    }

    @Test
    public void pointJSONTest() {
        JSONObject temp = null;
        try {
            temp = testPoint.writeToJSON();
        } catch(JSONException e) {
            assertTrue(false);
        }

        ConnectedPoint clone = new ConnectedPoint(null);
        try {
            clone.readFromJSON(temp);
        } catch(JSONException e) {
            assertTrue(false);
        }

        assertEquals(testPoint, clone);
    }
}

/*
 * Copyright (C) 2013-2018 The enviroCar project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.envirocar.server.rest.encoding.csv;

import com.vividsolutions.jts.geom.Coordinate;
import org.envirocar.server.core.entities.*;
import org.envirocar.server.core.exception.TrackNotFoundException;
import org.envirocar.server.core.exception.ValidationException;
import org.envirocar.server.mongo.entity.MongoSensor;
import org.envirocar.server.rest.MediaTypes;
import org.envirocar.server.rest.encoding.AbstractEncodingTest;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * TODO JavaDoc
 *
 * @author Benjamin Pross
 */
public class CSVEncodingTest extends AbstractEncodingTest {

    private String dateTime = "2014-05-20T08:42:06Z";

    String trackObjectId1 = "537a0d68b7c39b56ef230942";
    String trackObjectId2 = "537b0ef874c965606f093b0d";
    String trackObjectId3 = "537b0ef874c965606f093b0e";

    private String measurementObjectId1 = "537b0ef874c965606f093b0f";
    private String measurementObjectId2 = "537b0ef874c965606f093b10";
    private String measurementObjectId3 = "537b0ef874c965606f093b11";
    private String measurementObjectId4 = "537b0ef874c965606f093b12";
    private String measurementObjectId5 = "537b0ef874c965606f093b13";
    private String measurementObjectId6 = "537b0ef874c965606f093b14";

    private Track testTrack1;
    private Track testTrack2;
    private Track testTrack3;

    private Sensor sensor;
    private List<Phenomenon> phenomenons;

    String testUserName = "TestUser";

    @Before
    public void setup() {

        try {

            testTrack1 = getTestTrack(trackObjectId1);
            testTrack2 = getTestTrack(trackObjectId2);
            testTrack3 = getTestTrack(trackObjectId3);

            if (testTrack1 == null) {

                testTrack1 = createTrack(trackObjectId1, getSensor());

                createTrackWithMeasurements_AllMeasurementsHaveAllPhenomenons(
                        testTrack1, getPhenomenons(), getSensor());

            }
            if (testTrack2 == null) {

                testTrack2 = createTrack(trackObjectId2, getSensor());

                createTrackWithMeasurements_FirstMeasurementHasLessPhenomenons(
                        testTrack2, getPhenomenons(), getSensor());
            }
            if (testTrack3 == null) {

                testTrack3 = createTrack(trackObjectId3, getSensor());

                createTrackWithMeasurements_FirstMeasurementHasMorePhenomenons(
                        testTrack3, getPhenomenons(), getSensor());
            }

        } catch (ValidationException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCSVEncodingAllMeasurementsHaveAllPhenomenons()
            throws IOException {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(trackCSVEncoder.encodeCSV(testTrack1,
                        MediaTypes.TEXT_CSV_TYPE)));

        String line = "";

        String[] propertyNames = new String[0];
        String[] propertyValues1 = new String[0];
        String[] propertyValues2 = new String[0];

        for (int i = 0; i < 3; i++) {
            line = bufferedReader.readLine();
            if (line != null) {
                switch (i) {
                    case 0:
                        propertyNames = line.split(";");
                        break;
                    case 1:
                        propertyValues1 = line.split(";");
                        break;
                    case 2:
                        propertyValues2 = line.split(";");
                        break;

                    default:
                        break;
                }
            }

        }

        Map<String, String> expectedValues = new HashMap<String, String>();

        expectedValues.put("id", "537b0ef874c965606f093b0f");
        expectedValues.put("RPM(u/min)", "1");
        expectedValues.put("Speed(km/h)", "3");
        expectedValues.put("Intake Temperature(C)", "2");
        expectedValues.put("MAF(l/s)", "4");
        expectedValues.put("longitude", "1.1");
        expectedValues.put("latitude", "1.2");

        checkMeasurementValues(propertyNames, propertyValues1, expectedValues);

        expectedValues = new HashMap<String, String>();

        expectedValues.put("id", "537b0ef874c965606f093b10");
        expectedValues.put("RPM(u/min)", "2");
        expectedValues.put("Speed(km/h)", "4");
        expectedValues.put("Intake Temperature(C)", "3");
        expectedValues.put("MAF(l/s)", "5");
        expectedValues.put("longitude", "2.1");
        expectedValues.put("latitude", "2.2");

        checkMeasurementValues(propertyNames, propertyValues2, expectedValues);

    }

    @Test
    public void testCSVEncodingFirstMeasurementHasLessPhenomenons()
            throws IOException {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(trackCSVEncoder.encodeCSV(testTrack2,
                        MediaTypes.TEXT_CSV_TYPE)));

        String line = "";

        String[] propertyNames = new String[0];
        String[] propertyValues1 = new String[0];
        String[] propertyValues2 = new String[0];

        for (int i = 0; i < 3; i++) {
            line = bufferedReader.readLine();
            if (line != null) {
                switch (i) {
                    case 0:
                        propertyNames = line.split(";");
                        break;
                    case 1:
                        propertyValues1 = line.split(";");
                        break;
                    case 2:
                        propertyValues2 = line.split(";");
                        break;

                    default:
                        break;
                }
            }

        }

        Map<String, String> expectedValues = new HashMap<String, String>();

        expectedValues.put("id", "537b0ef874c965606f093b11");
        expectedValues.put("RPM(u/min)", "1");
        expectedValues.put("Speed(km/h)", "3");
        expectedValues.put("Intake Temperature(C)", "2");
        expectedValues.put("MAF(l/s)", "");
        expectedValues.put("longitude", "1.1");
        expectedValues.put("latitude", "1.2");

        checkMeasurementValues(propertyNames, propertyValues1, expectedValues);

        expectedValues = new HashMap<String, String>();

        expectedValues.put("id", "537b0ef874c965606f093b12");
        expectedValues.put("RPM(u/min)", "2");
        expectedValues.put("Speed(km/h)", "4");
        expectedValues.put("Intake Temperature(C)", "3");
        expectedValues.put("MAF(l/s)", "5");
        expectedValues.put("longitude", "2.1");
        expectedValues.put("latitude", "2.2");

        checkMeasurementValues(propertyNames, propertyValues2, expectedValues);

    }

    @Test
    public void testCSVEncodingFirstMeasurementHasMorePhenomenons()
            throws IOException {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(trackCSVEncoder.encodeCSV(testTrack3,
                        MediaTypes.TEXT_CSV_TYPE)));

        String line = "";

        String[] propertyNames = new String[0];
        String[] propertyValues1 = new String[0];
        String[] propertyValues2 = new String[0];

        for (int i = 0; i < 3; i++) {
            line = bufferedReader.readLine();
            if (line != null) {
                switch (i) {
                    case 0:
                        propertyNames = line.split(";");
                        break;
                    case 1:
                        propertyValues1 = line.split(";");
                        break;
                    case 2:
                        propertyValues2 = line.split(";");
                        break;

                    default:
                        break;
                }
            }

        }

        Map<String, String> expectedValues = new HashMap<String, String>();

        expectedValues.put("id", "537b0ef874c965606f093b13");
        expectedValues.put("RPM(u/min)", "1");
        expectedValues.put("Speed(km/h)", "3");
        expectedValues.put("Intake Temperature(C)", "2");
        expectedValues.put("longitude", "1.1");
        expectedValues.put("latitude", "1.2");

        checkMeasurementValues(propertyNames, propertyValues1, expectedValues);

        expectedValues = new HashMap<String, String>();

        expectedValues.put("id", "537b0ef874c965606f093b14");
        expectedValues.put("RPM(u/min)", "2");
        expectedValues.put("Speed(km/h)", "");
        expectedValues.put("Intake Temperature(C)", "3");
        expectedValues.put("longitude", "2.1");
        expectedValues.put("latitude", "2.2");

        checkMeasurementValues(propertyNames, propertyValues2, expectedValues);

    }

    private Sensor getSensor() {

        if (sensor == null) {
            sensor = createSensor();
        }
        return sensor;
    }

    private List<Phenomenon> getPhenomenons() {

        if (phenomenons == null) {
            phenomenons = createPhenomenoms();
        }
        return phenomenons;
    }

    private Track getTestTrack(String trackId) {
        Track testTrack = null;
        try {
            testTrack = dataService.getTrack(trackId);
        } catch (TrackNotFoundException e) {
            /*
             * ignore
             */
        }
        return testTrack;
    }

    private void createTrackWithMeasurements_AllMeasurementsHaveAllPhenomenons(
            Track track, List<Phenomenon> phenomenons, Sensor sensor) {

        DateTime now = DateTime.now();
        createMeasurement(track, measurementObjectId1, phenomenons, sensor, 1, now.minusSeconds(5));
        createMeasurement(track, measurementObjectId2, phenomenons, sensor, 2, now);

        dataService.createTrack(track);

    }

    private void createTrackWithMeasurements_FirstMeasurementHasLessPhenomenons(
            Track track, List<Phenomenon> phenomenons, Sensor sensor) {

        List<Phenomenon> originalPhenomenons = new ArrayList<Phenomenon>(phenomenons.size());

        for (Phenomenon phenomenon : phenomenons) {
            originalPhenomenons.add(phenomenon);
        }

        phenomenons.remove(phenomenons.size() - 1);

        DateTime now = DateTime.now();
        createMeasurement(track, measurementObjectId3, phenomenons, sensor, 1, now.minusSeconds(5));

        createMeasurement(track, measurementObjectId4, originalPhenomenons, sensor, 2, now);

        dataService.createTrack(track);
    }

    private void createTrackWithMeasurements_FirstMeasurementHasMorePhenomenons(
            Track track, List<Phenomenon> phenomenons, Sensor sensor) {

        DateTime now = DateTime.now();
        createMeasurement(track, measurementObjectId5, phenomenons, sensor, 1, now.minusSeconds(5));

        phenomenons.remove(phenomenons.size() - 1);

        createMeasurement(track, measurementObjectId6, phenomenons, sensor, 2, now);

        dataService.createTrack(track);
    }

    private Measurement createMeasurement(Track testTrack, String objectId,
                                          List<Phenomenon> phenomenons, Sensor sensor,
                                          int basenumber, DateTime time) {

        Measurement measurement = entityFactory.createMeasurement();

        measurement.setGeometry(geometryFactory.createPoint(new Coordinate(
                basenumber + 0.1, basenumber + 0.2)));

        measurement.setSensor(sensor);

        measurement.setIdentifier(objectId);

        int value = basenumber;

        for (Phenomenon phenomenon : phenomenons) {

            MeasurementValue measurementValue = entityFactory
                    .createMeasurementValue();

            measurementValue.setPhenomenon(phenomenon);

            measurementValue.setValue(value);

            measurement.addValue(measurementValue);

            value++;
        }

        measurement.setTime(time);

        measurement.setTrack(testTrack);

        dataService.createMeasurement(measurement);

        return measurement;

    }

    private Track createTrack(String objectId, Sensor sensor) {

        Track result = entityFactory.createTrack();

        result.setIdentifier(objectId);

        result.setSensor(sensor);

        return result;
    }

    private Sensor createSensor() {
        Sensor s = entityFactory.createSensor();

        s.setIdentifier("51bc53ab5064ba7f336ef920");

        s.setType("Car");

        MongoSensor ms = (MongoSensor) s;

        ms.setCreationTime(DateTime.parse(dateTime));
        ms.setModificationTime(DateTime.parse(dateTime));

        dataService.createSensor(ms);
        return s;
    }

    private List<Phenomenon> createPhenomenoms() {

        List<Phenomenon> result = new ArrayList<Phenomenon>();

        result.add(createPhenomenom("RPM", "u/min"));
        result.add(createPhenomenom("Intake Temperature", "C"));
        result.add(createPhenomenom("Speed", "km/h"));
        result.add(createPhenomenom("MAF", "l/s"));

        return result;
    }

    private Phenomenon createPhenomenom(String name, String unit) {

        Phenomenon f = entityFactory.createPhenomenon();

        f.setName(name);
        f.setUnit(unit);

        dataService.createPhenomenon(f);

        return f;
    }

    private void checkMeasurementValues(String[] propertyNames, String[] propertyValues,
                                        Map<String, String> expectedValues) {

        for (int i = 0; i < propertyNames.length; i++) {

            String propertyName = propertyNames[i].trim();

            if (!propertyName.equals("time")) {

                String expectedMeasurementValue = expectedValues
                        .get(propertyName);

                String propertyValue = propertyValues[i].trim();

                assertTrue("Value for " + propertyName + " does not match expected. Got " + propertyValue + " expected " +
                        expectedMeasurementValue + ".", expectedMeasurementValue
                        .equals(propertyValue));
            }

        }
    }

}

package com.codechallenge.itineraryservice.util;

import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Just a class for calculating time intervals
 */
public class TimeUtils {


    public static int calculateTime(String startTime, String endTime) {

        LocalTime l1 = LocalTime.parse(startTime.concat(":00"));
        LocalTime l2 = LocalTime.parse(endTime.concat(":00"));

        //We're calculating in a short range, but do not do this in production.
        return (int) MINUTES.between(l1, l2);
    }
}

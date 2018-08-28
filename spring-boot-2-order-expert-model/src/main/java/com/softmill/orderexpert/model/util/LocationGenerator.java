package com.softmill.orderexpert.model.util;

import java.util.Random;

import com.softmill.orderexpert.model.dto.request.LocationDTO;

public class LocationGenerator {

	public static LocationDTO getLocation(double x0, double y0, int radiusInMeters) {
		Random random = new Random();

		// Convert radius from meters to degrees
		double radiusInDegrees = radiusInMeters / 111320f;

		// Get a random distance and a random angle.
		double u = random.nextDouble();
		double v = random.nextDouble();
		double w = radiusInDegrees * Math.sqrt(u);
		double t = 2 * Math.PI * v;
		double x = w * Math.cos(t);
		double y = w * Math.sin(t);

		// Compensate the x value
		double new_x = x / Math.cos(Math.toRadians(y0));

		double foundLongitude = new_x + x0;
		double foundLatitude = y + y0;

		return new LocationDTO(foundLatitude, foundLongitude, null);
	}

}

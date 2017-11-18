package com.bugzzilla.models;

import com.drew.lang.GeoLocation;
import com.drew.metadata.exif.GpsDirectory;

import java.io.File;
import java.util.Collection;
import java.util.Date;

public class GpsData {

    private File file;
    private GeoLocation location;
    private Date date;

    public GpsData(Collection<GpsDirectory> gpsDirectories, File file) {
        this.file = file;
        for (GpsDirectory gpsDirectory : gpsDirectories) {
            GeoLocation geoLocation = gpsDirectory.getGeoLocation();
            if (geoLocation != null && !geoLocation.isZero()) {
                this.location = geoLocation;
            }
            Date geoData = gpsDirectory.getGpsDate();
            if (geoData != null) {
                this.date = geoData;
            }
        }
    }

    public File getFile() {
        return file;
    }

    public GeoLocation getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "GpsData{" +
                "file=" + file +
                ", location=" + location +
                ", date=" + date +
                '}';
    }
}

package com.bugzzilla.tools;

import com.bugzzilla.models.GpsData;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.exif.GpsDirectory;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MetaDataExtractor {

    private List<File> files;

    public MetaDataExtractor(List<File> files) {
        this.files = files;
    }

    private static GpsData readGpsData(File file) {
        Collection<GpsDirectory> gpsDirectories = null;
        try {
            gpsDirectories = ImageMetadataReader.readMetadata(file).getDirectoriesOfType(GpsDirectory.class);
        } catch (IOException | ImageProcessingException ignored) {
        }
        return new GpsData(gpsDirectories, file);
    }

    public List<GpsData> getGeoData() {
        return files.parallelStream().map(MetaDataExtractor::readGpsData).collect(Collectors.toList());
    }

    /*
    public List<GpsData> getGeoData() {

        List<GpsData> list = new ArrayList<GpsData>();
        for (File file: files) {
            try {
                Metadata metadata = ImageMetadataReader.readMetadata(file);
                list.add(new GpsData(metadata.getDirectoriesOfType(GpsDirectory.class), file));
            } catch (Exception e) {
                list.add(new GpsData(null, file));
            }
        }
        return list;
    }
    */
}

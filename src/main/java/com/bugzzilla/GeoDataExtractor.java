package com.bugzzilla;

import com.bugzzilla.models.GpsData;
import com.bugzzilla.tools.DummyMap;
import com.bugzzilla.tools.FileFinder;
import com.bugzzilla.tools.MetaDataExtractor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GeoDataExtractor {

    public static void main(String[] args) throws Exception{

        List<GpsData> gpsData;

        if (args.length < 2) usage();

        try {
            FileFinder fileFinder = new FileFinder();
            MetaDataExtractor metadataExtractor = new MetaDataExtractor(fileFinder.getFiles(args[0], args[1]));
            gpsData = metadataExtractor.getGeoData();
            gpsData.forEach(System.out::println);

            if (args.length == 3 && args[2].toUpperCase().equals("-MAKEDUMMYMAP"))
                try (PrintWriter out = new PrintWriter(new FileWriter(args[0] + File.separator + "index.html"), true)) {
                    out.print(DummyMap.getMap(gpsData));
                } catch (Exception ignored) {
                }

        }  catch (IOException e) {
            System.err.println("FIO Error, " + e.getMessage());
            System.exit(-1);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid pattern, " + e.getMessage());
            System.err.println(patternSample());
            System.exit(-1);
        }
    }

    private static void usage() {
        System.err.println("java GeoDataExtractor <path> <glob_pattern> [-MakeDummyMap]");
        System.err.println(patternSample());
        System.exit(-1);
    }

    private static String patternSample() {
        return "Glob_pattern sample: *.jpg or *.{jpg,jpeg,png}";
    }
}

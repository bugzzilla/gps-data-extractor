package com.bugzzilla.tools;

import com.bugzzilla.models.GpsData;

import java.util.List;

public class DummyMap {

    public static String getMap(List<GpsData> gpsData) {

        String fullPath;
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<!DOCTYPE html>\n<html>\n<head>\n");
        stringBuilder.append("<<meta name=\\\"viewport\\\" content=\\\"initial-scale=1.0, user-scalable=no\\\" />\n");
        stringBuilder.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n");
        stringBuilder.append("<style>html,body{height:100%;margin:0;padding:0;}#map_canvas{height:100%;}</style>\n");
        stringBuilder.append("<script type=\"text/javascript\" src=\"https://maps.googleapis.com/maps/api/js?sensor=false\"></script>\n");
        stringBuilder.append("<script type=\"text/javascript\">\n");
        stringBuilder.append("function initialise() {\n");
        stringBuilder.append("\tvar options = { zoom:2, mapTypeId:google.maps.MapTypeId.ROADMAP, center:new google.maps.LatLng(0.0, 0.0)};\n");
        stringBuilder.append("\tvar map = new google.maps.Map(document.getElementById('map_canvas'), options);\n");
        stringBuilder.append("\tvar marker;\n");
        stringBuilder.append("\tvar markers = [];\n");
        stringBuilder.append("\tvar bounds = new google.maps.LatLngBounds();\n");
        for (GpsData g: gpsData)
            if (g.getLocation() != null) {
                fullPath = g.getFile().getAbsolutePath().trim().replace("\\", "\\\\");
                stringBuilder.append("\tmarker = new google.maps.Marker({position:new google.maps.LatLng(").append(g.getLocation()).append("), map:map, title:\"").append(fullPath).append("\"});\n");
                stringBuilder.append("\tgoogle.maps.event.addListener(marker, 'click', function() { window.open(\"").append(fullPath).append("\")});\n");
                stringBuilder.append("\tmarkers.push(marker)\n");
            }
        stringBuilder.append("\tfor (var i = 0; i < markers.length; i++) {\n");
        stringBuilder.append("\t\tbounds.extend(markers[i].getPosition());\n");
        stringBuilder.append("\t}\n\tmap.fitBounds(bounds);\n");
        stringBuilder.append("}\n</script>\n</head>\n");
        stringBuilder.append("<body onload=\"initialise()\">\n");
        stringBuilder.append("<div id=\"map_canvas\"></div>\n");
        stringBuilder.append("</body>\n</html>\n");
        return stringBuilder.toString();
    }
}

/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *   (C) 2016, Open Source Geospatial Foundation (OSGeo). 
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.gml3.iso.simple;

import org.geotools.gml2.iso.simple.GMLWriter;
import org.geotools.gml2.iso.simple.GeometryEncoder;
import org.geotools.gml3.iso.GML;
import org.geotools.gml3.iso.simple.CurveEncoder;
import org.geotools.gml3.iso.simple.LineStringEncoder;
import org.geotools.gml3.iso.simple.LinearRingEncoder;
import org.geotools.gml3.iso.simple.MultiLineStringEncoder;
import org.geotools.gml3.iso.simple.MultiPointEncoder;
import org.geotools.gml3.iso.simple.MultiPolygonEncoder;
import org.geotools.gml3.iso.simple.PointEncoder;
import org.geotools.gml3.iso.simple.PolygonEncoder;
import org.geotools.xml.Encoder;
import org.opengis.geometry.Geometry;
import org.opengis.geometry.complex.CompositeSurface;
import org.opengis.geometry.primitive.Curve;
import org.opengis.geometry.primitive.Point;
import org.opengis.geometry.primitive.Ring;
import org.opengis.geometry.primitive.Solid;
import org.opengis.geometry.primitive.Surface;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Helper class that encodes the geometries within GeometryCollection
 * 
 * @author 
 */
public class GenericGeometryEncoder extends GeometryEncoder<Geometry> {

    Encoder encoder;
    String gmlPrefix;
    String gmlUri;

    public GenericGeometryEncoder(Encoder encoder) {
        this(encoder, "gml", GML.NAMESPACE);
    }

    /**
     *
     * @param encoder
     * @param gmlPrefix
     */
    public GenericGeometryEncoder(Encoder encoder, String gmlPrefix, String gmlUri) {
        super(encoder);
        this.encoder = encoder;
        this.gmlPrefix = gmlPrefix;
        this.gmlUri = gmlUri;
    }

    @Override
    public void encode(Geometry geometry, AttributesImpl atts, GMLWriter handler) throws Exception {
        
        if (geometry instanceof Curve) {
            LineStringEncoder lineString = new LineStringEncoder(encoder,
                LineStringEncoder.LINE_STRING);
            lineString.encode((Curve) geometry, atts, handler);
        } else if (geometry instanceof Point) {
            PointEncoder pt = new PointEncoder(encoder, gmlPrefix, gmlUri);
            pt.encode((Point) geometry, atts, handler);
        } else if (geometry instanceof Surface) {
            PolygonEncoder polygon = new PolygonEncoder(encoder, gmlPrefix, gmlUri);
            polygon.encode((Surface) geometry, atts, handler);
        } else if (geometry instanceof CompositeSurface) {
        	CompositeSurfaceEncoder compositeSurface = new CompositeSurfaceEncoder(encoder, gmlPrefix, gmlUri);
        	compositeSurface.encode((CompositeSurface) geometry, atts, handler);
        } else if (geometry instanceof Solid) {
        	SolidEncoder solid = new SolidEncoder(encoder, gmlPrefix, gmlUri);
        	solid.encode((Solid) geometry, atts, handler);
        } /*else if (geometry instanceof MultiLineString) {
            MultiLineStringEncoder multiLineString = new MultiLineStringEncoder(encoder, gmlPrefix, gmlUri, true);
            multiLineString.encode((MultiLineString) geometry, atts, handler);
        } else if (geometry instanceof MultiPoint) {
            MultiPointEncoder multiPoint = new MultiPointEncoder(encoder, gmlPrefix, gmlUri);
            multiPoint.encode((MultiPoint) geometry, atts, handler);
        } else if (geometry instanceof MultiPolygon) {
            MultiPolygonEncoder multiPolygon = new MultiPolygonEncoder(encoder, gmlPrefix, gmlUri);
            multiPolygon.encode((MultiPolygon) geometry, atts, handler);
        }*/ else if (geometry instanceof Ring) {
            LinearRingEncoder linearRing = new LinearRingEncoder(encoder, gmlPrefix, gmlUri);
            linearRing.encode((Curve) ((Ring) geometry).getElements().iterator().next(), atts, handler);
        } /*else if (geometry instanceof CircularString || geometry instanceof CompoundCurve
                        || geometry instanceof CircularRing || geometry instanceof CompoundRing) {
            CurveEncoder curve = new CurveEncoder(encoder, gmlPrefix, gmlUri);
            curve.encode((LineString) geometry, atts, handler);
        }*/ else {
            throw new Exception("Unsupported geometry " + geometry.toString());
        }
    }
}

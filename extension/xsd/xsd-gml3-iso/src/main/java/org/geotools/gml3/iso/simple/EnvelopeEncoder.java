/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2015 - 2016, Open Source Geospatial Foundation (OSGeo)
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
import org.geotools.gml2.iso.simple.ObjectEncoder;
import org.geotools.gml2.iso.simple.QualifiedName;
import org.geotools.gml3.iso.GML;
import org.geotools.xml.Encoder;
import org.opengis.geometry.Envelope;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Encodes a GML3 envelope
 * 
 * @author Justin Deoliveira, OpenGeo
 * @author Andrea Aime - GeoSolutions
 */
class EnvelopeEncoder extends ObjectEncoder<Envelope> {

    static final QualifiedName ENVELOPE = new QualifiedName(GML.NAMESPACE, "Envelope", "gml");

    static final QualifiedName LOWER_CORNER = new QualifiedName(GML.NAMESPACE, "lowerCorner", "gml");

    static final QualifiedName UPPER_CORNER = new QualifiedName(GML.NAMESPACE, "upperCorner", "gml");

    QualifiedName box;

    QualifiedName envelope;

    QualifiedName lowerCorner;

    QualifiedName upperCorner;

    protected EnvelopeEncoder(Encoder e, String gmlPrefix, String gmlNamespace) {
        super(e);
        this.envelope = ENVELOPE.derive(gmlPrefix, gmlNamespace);
        this.lowerCorner = LOWER_CORNER.derive(gmlPrefix, gmlNamespace);
        this.upperCorner = UPPER_CORNER.derive(gmlPrefix, gmlNamespace);
    }

    @Override
    public void encode(Envelope e, AttributesImpl atts, GMLWriter handler) throws Exception {
        handler.startElement(envelope, atts);
        handler.startElement(lowerCorner, null);
        handler.position(e.getLowerCorner().getCoordinate());
        handler.endElement(lowerCorner);

        handler.startElement(upperCorner, null);
        handler.position(e.getUpperCorner().getCoordinate());
        handler.endElement(upperCorner);
        handler.endElement(envelope);
    }
}
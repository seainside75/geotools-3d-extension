/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2008, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.gml2.iso.bindings;

import org.geotools.gml2.iso.GML;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.xml.ElementInstance;
import org.geotools.xml.Node;
import org.opengis.geometry.Geometry;
import org.opengis.geometry.ISOGeometryBuilder;
/**
 * 
 *
 * @source $URL$
 */
public class GMLGeometryPropertyTypeBindingTest extends AbstractGMLBindingTest {
    ElementInstance association;
    ElementInstance geometry;

    ISOGeometryBuilder builder = new ISOGeometryBuilder(DefaultGeographicCRS.WGS84_3D);
    protected void setUp() throws Exception {
        super.setUp();

        association = createElement(GML.NAMESPACE, "myGeometryProperty", GML.GEOMETRYPROPERTYTYPE,
                null);
        geometry = createElement(GML.NAMESPACE, "myPoint", GML.POINTTYPE, null);
    }

    public void testWithGeometry() throws Exception {
        Node node = createNode(association, new ElementInstance[] { geometry },
                new Object[] { builder.createPoint(new double[] {0, 0, 0}) }, null, null);
        GMLGeometryAssociationTypeBinding s = (GMLGeometryAssociationTypeBinding) getBinding(GML.GEOMETRYASSOCIATIONTYPE);
        GMLGeometryPropertyTypeBinding s1 = (GMLGeometryPropertyTypeBinding) getBinding(GML.GEOMETRYPROPERTYTYPE);
        Geometry p = (Geometry) s1.parse(association, node, s.parse(association, node, null));
        assertNotNull(p);
    }
}

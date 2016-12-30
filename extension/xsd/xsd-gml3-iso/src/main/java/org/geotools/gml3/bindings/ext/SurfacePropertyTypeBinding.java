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
package org.geotools.gml3.bindings.ext;

import javax.xml.namespace.QName;

import org.geotools.gml3.GML;
import org.geotools.gml3.XSDIdRegistry;
import org.geotools.gml3.bindings.GML3EncodingUtils;
import org.geotools.gml3.bindings.SurfaceTypeBinding;
import org.geotools.xml.ElementInstance;
import org.geotools.xml.Node;
import org.opengis.geometry.Geometry;
import org.opengis.geometry.ISOGeometryBuilder;
import org.opengis.geometry.aggregate.MultiSurface;
import org.opengis.geometry.primitive.Surface;

import com.google.common.collect.Sets;

/**
 * 
 *
 * @source $URL$
 */
public class SurfacePropertyTypeBinding extends org.geotools.gml3.bindings.SurfacePropertyTypeBinding 
    implements Comparable {

    ISOGeometryBuilder gb;
    
    public SurfacePropertyTypeBinding(GML3EncodingUtils encodingUtils, XSDIdRegistry idRegistry, ISOGeometryBuilder gb) {
        super(encodingUtils, idRegistry);
        this.gb = gb;
    }

    public Class<? extends Geometry> getGeometryType() {
        return MultiSurface.class;
    }

    @Override
    public Object parse(ElementInstance instance, Node node, Object value) throws Exception {
        Surface polygon = (Surface)node.getChildValue(Surface.class);
        MultiSurface surface = (MultiSurface)node.getChildValue(MultiSurface.class);

        if (polygon != null) {
            return gb.createMultiSurface(Sets.newHashSet(polygon));
        } else {
            return surface;
        }
    }

    @Override
    public Object getProperty(Object object, QName name)
        throws Exception {
        if ("_Surface".equals(name.getLocalPart()) || "AbstractSurface".equals(name.getLocalPart())) {
            MultiSurface multiPolygon = (MultiSurface) object;
            // this MultiPolygon consists of a single Polygon wrapped in a MultiPolygon:
            return multiPolygon.getElements().iterator().next();
        }

        return super.getProperty(object, name);
    }

    public int compareTo(Object o) {
        if (o instanceof SurfaceTypeBinding) {
            return 1;
        } else {
            return 0;
        }
    }
}

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
package org.geotools.filter.iso.v1_0;

import javax.xml.namespace.QName;

import org.geotools.xml.AbstractComplexBinding;
import org.geotools.xml.ElementInstance;
import org.geotools.xml.Node;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.expression.Expression;
import org.opengis.filter.spatial.Contains;
import org.opengis.geometry.ISOGeometryBuilder;


/**
 * Binding object for the element http://www.opengis.net/ogc:Contains.
 *
 * <p>
 *        <pre>
 *         <code>
 *  &lt;xsd:element name="Contains" substitutionGroup="ogc:spatialOps" type="ogc:BinarySpatialOpType"/&gt;
 *
 *          </code>
 *         </pre>
 * </p>
 *
 * @generated
 *
 *
 *
 * @source $URL$
 */
public class OGCContainsBinding extends AbstractComplexBinding {
    FilterFactory2 filterFactory;
    ISOGeometryBuilder gBuilder;

    public OGCContainsBinding(FilterFactory2 filterFactory, ISOGeometryBuilder gBuilder) {
        this.filterFactory = filterFactory;
        this.gBuilder = gBuilder;
    }

    /**
     * @generated
     */
    public QName getTarget() {
        return OGC.Contains;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Class getType() {
        return Contains.class;
    }

    public int getExecutionMode() {
        return AFTER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Object parse(ElementInstance instance, Node node, Object value)
        throws Exception {
        Expression[] operands = OGCUtils.spatial(node, filterFactory, gBuilder);

        return filterFactory.contains(operands[0], operands[1]);
    }
}

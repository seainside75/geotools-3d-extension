/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2015, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.gml3.iso.bindings;

import java.net.URI;
import java.util.logging.Logger;

import javax.xml.namespace.QName;

import org.geotools.gml2.SrsSyntax;
import org.geotools.gml2.iso.bindings.GML2EncodingUtils;
import org.geotools.gml3.iso.GML;
import org.geotools.gml3.iso.bindings.GML3EncodingUtils;
import org.geotools.referencing.CRS;
import org.geotools.xml.AbstractComplexBinding;
import org.geotools.xml.Configuration;
import org.geotools.xml.ElementInstance;
import org.geotools.xml.Node;
import org.opengis.geometry.Geometry;
import org.opengis.geometry.ISOGeometryBuilder;
import org.opengis.referencing.crs.CoordinateReferenceSystem;


/**
 * Binding object for the type http://www.opengis.net/gml:AbstractGeometryType.
 *
 * <p>
 *        <pre>
 *         <code>
 *  &lt;complexType abstract="true" name="AbstractGeometryType"&gt;
 *      &lt;annotation&gt;
 *          &lt;documentation&gt;All geometry elements are derived directly or indirectly from this abstract supertype. A geometry element may
 *                          have an identifying attribute ("gml:id"), a name (attribute "name") and a description (attribute "description"). It may be associated
 *                          with a spatial reference system (attribute "srsName"). The following rules shall be adhered: - Every geometry type shall derive
 *                          from this abstract type. - Every geometry element (i.e. an element of a geometry type) shall be directly or indirectly in the
 *                          substitution group of _Geometry.&lt;/documentation&gt;
 *      &lt;/annotation&gt;
 *      &lt;complexContent&gt;
 *          &lt;extension base="gml:AbstractGMLType"&gt;
 *              &lt;attribute name="gid" type="string" use="optional"&gt;
 *                  &lt;annotation&gt;
 *                      &lt;documentation&gt;This attribute is included for backward compatibility with GML 2 and is deprecated with GML 3.
 *                                                  This identifer is superceded by "gml:id" inherited from AbstractGMLType. The attribute "gid" should not be used
 *                                                  anymore and may be deleted in future versions of GML without further notice.&lt;/documentation&gt;
 *                  &lt;/annotation&gt;
 *              &lt;/attribute&gt;
 *              &lt;attributeGroup ref="gml:SRSReferenceGroup"/&gt;
 *          &lt;/extension&gt;
 *      &lt;/complexContent&gt;
 *  &lt;/complexType&gt;
 *
 *          </code>
 *         </pre>
 * </p>
 *
 * @generated
 * @author Hyung-Gyu Ryoo, Pusan National University
 *
 *
 * @source $URL$
 */
public class AbstractGeometryTypeBinding extends AbstractComplexBinding {
    ISOGeometryBuilder builder;
	Configuration config;
    SrsSyntax srsSyntax;

    public AbstractGeometryTypeBinding(ISOGeometryBuilder builder, Configuration config, SrsSyntax srsSyntax) {
    	this.builder = builder;
        this.config = config;
        this.srsSyntax = srsSyntax;
    }

    public void setConfiguration(Configuration config) {
        this.config = config;
    }

    public void setSrsSyntax(SrsSyntax srsSyntax) {
        this.srsSyntax = srsSyntax;
    }

    /**
     * @generated
     */
    public QName getTarget() {
        return GML.AbstractGeometryType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public int getExecutionMode() {
        return OVERRIDE;
    }
    
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Class getType() {
        return Geometry.class;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Object parse(ElementInstance instance, Node node, Object value)
        throws Exception {
        //set the crs
        /*if (value instanceof Geometry) {
            CoordinateReferenceSystem crs = GML3ParsingUtils.crs(node);

            if (crs != null) {
                Geometry geometry = (Geometry) value;
                geometry.setUserData(crs);
            }
        }*/

    	/*if (node.hasAttribute("srsName")) {
            URI srs = (URI) node.getAttributeValue("srsName");
            CoordinateReferenceSystem crs = CRS.decode(srs.toString());
            
            if (crs != null) {
                if(!builder.getCoordinateReferenceSystem().equals(crs)) {
                	builder.setCoordinateReferenceSystem(crs);
                }
            } else {
                //logger.warning("Could not create Coordinate Reference System for " + srs);
            }
            return crs;
        }*/
    	
        return value;
    }
    
    public Object getProperty(Object object, QName name)
        throws Exception {
        Geometry geometry = (Geometry) object;
        
        if ("srsName".equals(name.getLocalPart())) {
            CoordinateReferenceSystem crs = GML3EncodingUtils.getCRS(geometry);
            if (crs != null) {
                return GML3EncodingUtils.toURI(crs, srsSyntax);
            }
        }

        if ("srsDimension".equals(name.getLocalPart())) {
            return GML2EncodingUtils.getGeometryDimension(geometry, config);
        }

        // FIXME: should be gml:id, but which GML?
        // Refactor bindings or introduce a new one for GML 3.2
        if ("id".equals(name.getLocalPart())) {
            return GML3EncodingUtils.getID(geometry);
        }

        // FIXME: should be gml:name, but which GML?
        // Refactor bindings or introduce a new one for GML 3.2
        if ("name".equals(name.getLocalPart())) {
            return GML3EncodingUtils.getName(geometry);
        }
        
        // FIXME: should be gml:description, but which GML?
        // Refactor bindings or introduce a new one for GML 3.2
        if ("description".equals(name.getLocalPart())) {
            return GML3EncodingUtils.getDescription(geometry);
        }   
        /*if ("uomLabels".equals(name.getLocalPart())) {
            return GML3EncodingUtils.getUomLabels(geometry);
        }

        if ("axisLabels".equals(name.getLocalPart())) {
            return GML3EncodingUtils.getAxisLabels(geometry);
        }*/

        return null;
    }
}

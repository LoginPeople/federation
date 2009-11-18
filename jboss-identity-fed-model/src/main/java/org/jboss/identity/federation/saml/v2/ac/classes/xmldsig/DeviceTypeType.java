//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.08 at 05:45:20 PM CST 
//


package org.jboss.identity.federation.saml.v2.ac.classes.xmldsig;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeviceTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DeviceTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="hardware"/>
 *     &lt;enumeration value="software"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DeviceTypeType")
@XmlEnum
public enum DeviceTypeType {

    @XmlEnumValue("hardware")
    HARDWARE("hardware"),
    @XmlEnumValue("software")
    SOFTWARE("software");
    private final String value;

    DeviceTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DeviceTypeType fromValue(String v) {
        for (DeviceTypeType c: DeviceTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

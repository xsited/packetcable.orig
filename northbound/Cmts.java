/*
{
        "name": "cmts 1",
        "description": "A cmts name one.",
        "macAddress": "00:14:bf:28:81:AA",
        "uptime": "13528508325234",
        "enabled": true,
        "protocols_supported": "pcmm",
        "config_pcmm": {
           "ip": "192.168.1.2",
           "port": "3390",
           "version": "5"
        },
        "config_snmp": {
           "ip": "192.168.1.2",
           "port": "161",
           "version": "2c",
           "config_v1v2c": {
               "community_ro": "public",
               "community_rw": "private"
            },
           "config_v3": {
               "user":"you",
               "passphrase":"test",
               "auth": true,
               "privacy":"des",
               "access":"readwrite"
            }
    }
}
*/
 
----------------------org.opendaylight.pcmm.northbound.nodes.Cmts.java--------------------------

package org.opendaylight.pcmm.northbound.nodes;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
"name",
"description",
"macAddress",
"uptime",
"enabled",
"protocols_supported",
"config_pcmm",
"config_snmp"
})
public class Cmts {

@JsonProperty("name")
private String name;
@JsonProperty("description")
private String description;
@JsonProperty("macAddress")
private String macAddress;
@JsonProperty("uptime")
private String uptime;
@JsonProperty("enabled")
private boolean enabled;
@JsonProperty("protocols_supported")
private String protocols_supported;
@JsonProperty("config_pcmm")
private Config_pcmm config_pcmm;
@JsonProperty("config_snmp")
private Config_snmp config_snmp;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("name")
public String getName() {
return name;
}

@JsonProperty("name")
public void setName(String name) {
this.name = name;
}

public Cmts withName(String name) {
this.name = name;
return this;
}

@JsonProperty("description")
public String getDescription() {
return description;
}

@JsonProperty("description")
public void setDescription(String description) {
this.description = description;
}

public Cmts withDescription(String description) {
this.description = description;
return this;
}

@JsonProperty("macAddress")
public String getMacAddress() {
return macAddress;
}

@JsonProperty("macAddress")
public void setMacAddress(String macAddress) {
this.macAddress = macAddress;
}

public Cmts withMacAddress(String macAddress) {
this.macAddress = macAddress;
return this;
}

@JsonProperty("uptime")
public String getUptime() {
return uptime;
}

@JsonProperty("uptime")
public void setUptime(String uptime) {
this.uptime = uptime;
}

public Cmts withUptime(String uptime) {
this.uptime = uptime;
return this;
}

@JsonProperty("enabled")
public boolean isEnabled() {
return enabled;
}

@JsonProperty("enabled")
public void setEnabled(boolean enabled) {
this.enabled = enabled;
}

public Cmts withEnabled(boolean enabled) {
this.enabled = enabled;
return this;
}

@JsonProperty("protocols_supported")
public String getProtocols_supported() {
return protocols_supported;
}

@JsonProperty("protocols_supported")
public void setProtocols_supported(String protocols_supported) {
this.protocols_supported = protocols_supported;
}

public Cmts withProtocols_supported(String protocols_supported) {
this.protocols_supported = protocols_supported;
return this;
}

@JsonProperty("config_pcmm")
public Config_pcmm getConfig_pcmm() {
return config_pcmm;
}

@JsonProperty("config_pcmm")
public void setConfig_pcmm(Config_pcmm config_pcmm) {
this.config_pcmm = config_pcmm;
}

public Cmts withConfig_pcmm(Config_pcmm config_pcmm) {
this.config_pcmm = config_pcmm;
return this;
}

@JsonProperty("config_snmp")
public Config_snmp getConfig_snmp() {
return config_snmp;
}

@JsonProperty("config_snmp")
public void setConfig_snmp(Config_snmp config_snmp) {
this.config_snmp = config_snmp;
}

public Cmts withConfig_snmp(Config_snmp config_snmp) {
this.config_snmp = config_snmp;
return this;
}

@Override
public String toString() {
return ToStringBuilder.reflectionToString(this);
}

@Override
public int hashCode() {
return HashCodeBuilder.reflectionHashCode(this);
}

@Override
public boolean equals(Object other) {
return EqualsBuilder.reflectionEquals(this, other);
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
-----------------------------------org.opendaylight.pcmm.northbound.nodes.Config_pcmm.java-----------------------------------

package org.opendaylight.pcmm.northbound.nodes;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
"ip",
"port",
"version"
})
public class Config_pcmm {

@JsonProperty("ip")
private String ip;
@JsonProperty("port")
private String port;
@JsonProperty("version")
private String version;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("ip")
public String getIp() {
return ip;
}

@JsonProperty("ip")
public void setIp(String ip) {
this.ip = ip;
}

public Config_pcmm withIp(String ip) {
this.ip = ip;
return this;
}

@JsonProperty("port")
public String getPort() {
return port;
}

@JsonProperty("port")
public void setPort(String port) {
this.port = port;
}

public Config_pcmm withPort(String port) {
this.port = port;
return this;
}

@JsonProperty("version")
public String getVersion() {
return version;
}

@JsonProperty("version")
public void setVersion(String version) {
this.version = version;
}

public Config_pcmm withVersion(String version) {
this.version = version;
return this;
}

@Override
public String toString() {
return ToStringBuilder.reflectionToString(this);
}

@Override
public int hashCode() {
return HashCodeBuilder.reflectionHashCode(this);
}

@Override
public boolean equals(Object other) {
return EqualsBuilder.reflectionEquals(this, other);
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
-----------------------------------org.opendaylight.pcmm.northbound.nodes.Config_snmp.java-----------------------------------

package org.opendaylight.pcmm.northbound.nodes;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
"ip",
"port",
"version",
"config_v1v2c",
"config_v3"
})
public class Config_snmp {

@JsonProperty("ip")
private String ip;
@JsonProperty("port")
private String port;
@JsonProperty("version")
private String version;
@JsonProperty("config_v1v2c")
private Config_v1v2c config_v1v2c;
@JsonProperty("config_v3")
private Config_v3 config_v3;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("ip")
public String getIp() {
return ip;
}

@JsonProperty("ip")
public void setIp(String ip) {
this.ip = ip;
}

public Config_snmp withIp(String ip) {
this.ip = ip;
return this;
}

@JsonProperty("port")
public String getPort() {
return port;
}

@JsonProperty("port")
public void setPort(String port) {
this.port = port;
}

public Config_snmp withPort(String port) {
this.port = port;
return this;
}

@JsonProperty("version")
public String getVersion() {
return version;
}

@JsonProperty("version")
public void setVersion(String version) {
this.version = version;
}

public Config_snmp withVersion(String version) {
this.version = version;
return this;
}

@JsonProperty("config_v1v2c")
public Config_v1v2c getConfig_v1v2c() {
return config_v1v2c;
}

@JsonProperty("config_v1v2c")
public void setConfig_v1v2c(Config_v1v2c config_v1v2c) {
this.config_v1v2c = config_v1v2c;
}

public Config_snmp withConfig_v1v2c(Config_v1v2c config_v1v2c) {
this.config_v1v2c = config_v1v2c;
return this;
}

@JsonProperty("config_v3")
public Config_v3 getConfig_v3() {
return config_v3;
}

@JsonProperty("config_v3")
public void setConfig_v3(Config_v3 config_v3) {
this.config_v3 = config_v3;
}

public Config_snmp withConfig_v3(Config_v3 config_v3) {
this.config_v3 = config_v3;
return this;
}

@Override
public String toString() {
return ToStringBuilder.reflectionToString(this);
}

@Override
public int hashCode() {
return HashCodeBuilder.reflectionHashCode(this);
}

@Override
public boolean equals(Object other) {
return EqualsBuilder.reflectionEquals(this, other);
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
--------------------org.opendaylight.pcmm.northbound.nodes.Config_snmp.Config_v1v2c.java--------------------

package org.opendaylight.pcmm.northbound.nodes;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
"community_ro",
"community_rw"
})
public class Config_v1v2c {

@JsonProperty("community_ro")
private String community_ro;
@JsonProperty("community_rw")
private String community_rw;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("community_ro")
public String getCommunity_ro() {
return community_ro;
}

@JsonProperty("community_ro")
public void setCommunity_ro(String community_ro) {
this.community_ro = community_ro;
}

public Config_v1v2c withCommunity_ro(String community_ro) {
this.community_ro = community_ro;
return this;
}

@JsonProperty("community_rw")
public String getCommunity_rw() {
return community_rw;
}

@JsonProperty("community_rw")
public void setCommunity_rw(String community_rw) {
this.community_rw = community_rw;
}

public Config_v1v2c withCommunity_rw(String community_rw) {
this.community_rw = community_rw;
return this;
}

@Override
public String toString() {
return ToStringBuilder.reflectionToString(this);
}

@Override
public int hashCode() {
return HashCodeBuilder.reflectionHashCode(this);
}

@Override
public boolean equals(Object other) {
return EqualsBuilder.reflectionEquals(this, other);
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
----------------org.opendaylight.pcmm.northbound.nodes.Config_snmp.Config_v3.java-----------------------------

package org.opendaylight.pcmm.northbound.nodes;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
"user",
"passphrase",
"auth",
"privacy",
"access"
})
public class Config_v3 {

@JsonProperty("user")
private String user;
@JsonProperty("passphrase")
private String passphrase;
@JsonProperty("auth")
private boolean auth;
@JsonProperty("privacy")
private String privacy;
@JsonProperty("access")
private String access;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("user")
public String getUser() {
return user;
}

@JsonProperty("user")
public void setUser(String user) {
this.user = user;
}

public Config_v3 withUser(String user) {
this.user = user;
return this;
}

@JsonProperty("passphrase")
public String getPassphrase() {
return passphrase;
}

@JsonProperty("passphrase")
public void setPassphrase(String passphrase) {
this.passphrase = passphrase;
}

public Config_v3 withPassphrase(String passphrase) {
this.passphrase = passphrase;
return this;
}

@JsonProperty("auth")
public boolean isAuth() {
return auth;
}

@JsonProperty("auth")
public void setAuth(boolean auth) {
this.auth = auth;
}

public Config_v3 withAuth(boolean auth) {
this.auth = auth;
return this;
}

@JsonProperty("privacy")
public String getPrivacy() {
return privacy;
}

@JsonProperty("privacy")
public void setPrivacy(String privacy) {
this.privacy = privacy;
}

public Config_v3 withPrivacy(String privacy) {
this.privacy = privacy;
return this;
}

@JsonProperty("access")
public String getAccess() {
return access;
}

@JsonProperty("access")
public void setAccess(String access) {
this.access = access;
}

public Config_v3 withAccess(String access) {
this.access = access;
return this;
}

@Override
public String toString() {
return ToStringBuilder.reflectionToString(this);
}

@Override
public int hashCode() {
return HashCodeBuilder.reflectionHashCode(this);
}

@Override
public boolean equals(Object other) {
return EqualsBuilder.reflectionEquals(this, other);
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

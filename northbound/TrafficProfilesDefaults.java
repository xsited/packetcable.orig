
/*
{

"flowspec" : {
		"token-bucket-rate":0,
		"token-bucket-size":2,
		"peak-data-rate":0,
		"minimum-policed-unit":1,
		"maximum-packet-size":500,
		"rate":0,
		"slack-term":0
 },

"ugs" : {
                "request-transmission-policy":0,
                "unsolicited-grant-size":0,
                "grants-interval":0,
                "reserved":0,
                "nominal-grant-interval":0,
                "tolerated-grant-jitter":800,
                "required-attribute-mask":0,
                "forbidden-attribute-mask":0,
                "attribute-aggregation-rule-mask":0
 },

"us" : {
                 "traffic-priority":0,
                 "downstream-resequencing":1,
                 "reserved0":0,
                 "maximum-sustained-traffic-rate":0,
                 "maximum-traffic-burst":3044,
                 "minimum-reserved-traffic-rate":0,
                 "assumed-minimum-reserved-traffic-rate-packet-size":0,
                 "reserved1":0,
                 "maximum-downstream-latency":0,
                 "downstream-peak-traffic-rate":0,
                 "required-attribute-mask":0,
                 "forbidden-attribute-mask":0,
                 "attribute-aggregation-rule-mask":0
 },

"default":{
                 "traffic-priority":0,
                 "reserved0":0,
                 "reserved1":0,
                 "request-transmission-policy":0,
                 "maximum-sustained-traffic-rate":0,
                 "maximum-traffic-burst":3044,
                 "maximum-reserved-traffic-rate":0,
                 "traffic-rate-packet-size-maximum-concatenated-burst":0,
                 "assumed-minimum-reserved":1522,
                 "required-attribute-mask":0,
                 "forbidden-attribute-mask":0,
                 "attribute-aggregation-rule-mask":0

 }
}
*/
----------------------org.opendaylight.pcmm.northbound.trafficprofiles.defaults.java-----------------------

package org.opendaylight.pcmm.northbound.trafficprofiles.defaults;

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
"flowspec",
"ugs",
"us",
"default"
})
public class TrafficProfilesDefaults {

@JsonProperty("flowspec")
private Flowspec flowspec;
@JsonProperty("ugs")
private Ugs ugs;
@JsonProperty("us")
private Us us;
@JsonProperty("default")
private Default _default;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("flowspec")
public Flowspec getFlowspec() {
return flowspec;
}

@JsonProperty("flowspec")
public void setFlowspec(Flowspec flowspec) {
this.flowspec = flowspec;
}

public Besteffort withFlowspec(Flowspec flowspec) {
this.flowspec = flowspec;
return this;
}

@JsonProperty("ugs")
public Ugs getUgs() {
return ugs;
}

@JsonProperty("ugs")
public void setUgs(Ugs ugs) {
this.ugs = ugs;
}

public Besteffort withUgs(Ugs ugs) {
this.ugs = ugs;
return this;
}

@JsonProperty("us")
public Us getUs() {
return us;
}

@JsonProperty("us")
public void setUs(Us us) {
this.us = us;
}

public Besteffort withUs(Us us) {
this.us = us;
return this;
}

@JsonProperty("default")
public Default getDefault() {
return _default;
}

@JsonProperty("default")
public void setDefault(Default _default) {
this._default = _default;
}

public Besteffort with_default(Default _default) {
this._default = _default;
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
-----------------------------------org.opendaylight.cmts.Default.java-----------------------------------

package org.opendaylight.cmts;

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
"traffic-priority",
"reserved0",
"reserved1",
"request-transmission-policy",
"maximum-sustained-traffic-rate",
"maximum-traffic-burst",
"maximum-reserved-traffic-rate",
"traffic-rate-packet-size-maximum-concatenated-burst",
"assumed-minimum-reserved",
"required-attribute-mask",
"forbidden-attribute-mask",
"attribute-aggregation-rule-mask"
})
public class Default {

@JsonProperty("traffic-priority")
private int traffic_priority;
@JsonProperty("reserved0")
private int reserved0;
@JsonProperty("reserved1")
private int reserved1;
@JsonProperty("request-transmission-policy")
private int request_transmission_policy;
@JsonProperty("maximum-sustained-traffic-rate")
private int maximum_sustained_traffic_rate;
@JsonProperty("maximum-traffic-burst")
private int maximum_traffic_burst;
@JsonProperty("maximum-reserved-traffic-rate")
private int maximum_reserved_traffic_rate;
@JsonProperty("traffic-rate-packet-size-maximum-concatenated-burst")
private int traffic_rate_packet_size_maximum_concatenated_burst;
@JsonProperty("assumed-minimum-reserved")
private int assumed_minimum_reserved;
@JsonProperty("required-attribute-mask")
private int required_attribute_mask;
@JsonProperty("forbidden-attribute-mask")
private int forbidden_attribute_mask;
@JsonProperty("attribute-aggregation-rule-mask")
private int attribute_aggregation_rule_mask;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("traffic-priority")
public int getTraffic_priority() {
return traffic_priority;
}

@JsonProperty("traffic-priority")
public void setTraffic_priority(int traffic_priority) {
this.traffic_priority = traffic_priority;
}

public Default withTraffic_priority(int traffic_priority) {
this.traffic_priority = traffic_priority;
return this;
}

@JsonProperty("reserved0")
public int getReserved0() {
return reserved0;
}

@JsonProperty("reserved0")
public void setReserved0(int reserved0) {
this.reserved0 = reserved0;
}

public Default withReserved0(int reserved0) {
this.reserved0 = reserved0;
return this;
}

@JsonProperty("reserved1")
public int getReserved1() {
return reserved1;
}

@JsonProperty("reserved1")
public void setReserved1(int reserved1) {
this.reserved1 = reserved1;
}

public Default withReserved1(int reserved1) {
this.reserved1 = reserved1;
return this;
}

@JsonProperty("request-transmission-policy")
public int getRequest_transmission_policy() {
return request_transmission_policy;
}

@JsonProperty("request-transmission-policy")
public void setRequest_transmission_policy(int request_transmission_policy) {
this.request_transmission_policy = request_transmission_policy;
}

public Default withRequest_transmission_policy(int request_transmission_policy) {
this.request_transmission_policy = request_transmission_policy;
return this;
}

@JsonProperty("maximum-sustained-traffic-rate")
public int getMaximum_sustained_traffic_rate() {
return maximum_sustained_traffic_rate;
}

@JsonProperty("maximum-sustained-traffic-rate")
public void setMaximum_sustained_traffic_rate(int maximum_sustained_traffic_rate) {
this.maximum_sustained_traffic_rate = maximum_sustained_traffic_rate;
}

public Default withMaximum_sustained_traffic_rate(int maximum_sustained_traffic_rate) {
this.maximum_sustained_traffic_rate = maximum_sustained_traffic_rate;
return this;
}

@JsonProperty("maximum-traffic-burst")
public int getMaximum_traffic_burst() {
return maximum_traffic_burst;
}

@JsonProperty("maximum-traffic-burst")
public void setMaximum_traffic_burst(int maximum_traffic_burst) {
this.maximum_traffic_burst = maximum_traffic_burst;
}

public Default withMaximum_traffic_burst(int maximum_traffic_burst) {
this.maximum_traffic_burst = maximum_traffic_burst;
return this;
}

@JsonProperty("maximum-reserved-traffic-rate")
public int getMaximum_reserved_traffic_rate() {
return maximum_reserved_traffic_rate;
}

@JsonProperty("maximum-reserved-traffic-rate")
public void setMaximum_reserved_traffic_rate(int maximum_reserved_traffic_rate) {
this.maximum_reserved_traffic_rate = maximum_reserved_traffic_rate;
}

public Default withMaximum_reserved_traffic_rate(int maximum_reserved_traffic_rate) {
this.maximum_reserved_traffic_rate = maximum_reserved_traffic_rate;
return this;
}

@JsonProperty("traffic-rate-packet-size-maximum-concatenated-burst")
public int getTraffic_rate_packet_size_maximum_concatenated_burst() {
return traffic_rate_packet_size_maximum_concatenated_burst;
}

@JsonProperty("traffic-rate-packet-size-maximum-concatenated-burst")
public void setTraffic_rate_packet_size_maximum_concatenated_burst(int traffic_rate_packet_size_maximum_concatenated_burst) {
this.traffic_rate_packet_size_maximum_concatenated_burst = traffic_rate_packet_size_maximum_concatenated_burst;
}

public Default withTraffic_rate_packet_size_maximum_concatenated_burst(int traffic_rate_packet_size_maximum_concatenated_burst) {
this.traffic_rate_packet_size_maximum_concatenated_burst = traffic_rate_packet_size_maximum_concatenated_burst;
return this;
}

@JsonProperty("assumed-minimum-reserved")
public int getAssumed_minimum_reserved() {
return assumed_minimum_reserved;
}

@JsonProperty("assumed-minimum-reserved")
public void setAssumed_minimum_reserved(int assumed_minimum_reserved) {
this.assumed_minimum_reserved = assumed_minimum_reserved;
}

public Default withAssumed_minimum_reserved(int assumed_minimum_reserved) {
this.assumed_minimum_reserved = assumed_minimum_reserved;
return this;
}

@JsonProperty("required-attribute-mask")
public int getRequired_attribute_mask() {
return required_attribute_mask;
}

@JsonProperty("required-attribute-mask")
public void setRequired_attribute_mask(int required_attribute_mask) {
this.required_attribute_mask = required_attribute_mask;
}

public Default withRequired_attribute_mask(int required_attribute_mask) {
this.required_attribute_mask = required_attribute_mask;
return this;
}

@JsonProperty("forbidden-attribute-mask")
public int getForbidden_attribute_mask() {
return forbidden_attribute_mask;
}

@JsonProperty("forbidden-attribute-mask")
public void setForbidden_attribute_mask(int forbidden_attribute_mask) {
this.forbidden_attribute_mask = forbidden_attribute_mask;
}

public Default withForbidden_attribute_mask(int forbidden_attribute_mask) {
this.forbidden_attribute_mask = forbidden_attribute_mask;
return this;
}

@JsonProperty("attribute-aggregation-rule-mask")
public int getAttribute_aggregation_rule_mask() {
return attribute_aggregation_rule_mask;
}

@JsonProperty("attribute-aggregation-rule-mask")
public void setAttribute_aggregation_rule_mask(int attribute_aggregation_rule_mask) {
this.attribute_aggregation_rule_mask = attribute_aggregation_rule_mask;
}

public Default withAttribute_aggregation_rule_mask(int attribute_aggregation_rule_mask) {
this.attribute_aggregation_rule_mask = attribute_aggregation_rule_mask;
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
-----------------------------------org.opendaylight.cmts.Flowspec.java-----------------------------------

package org.opendaylight.cmts;

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
"token-bucket-rate",
"token-bucket-size",
"peak-data-rate",
"minimum-policed-unit",
"maximum-packet-size",
"rate",
"slack-term"
})
public class Flowspec {

@JsonProperty("token-bucket-rate")
private int token_bucket_rate;
@JsonProperty("token-bucket-size")
private int token_bucket_size;
@JsonProperty("peak-data-rate")
private int peak_data_rate;
@JsonProperty("minimum-policed-unit")
private int minimum_policed_unit;
@JsonProperty("maximum-packet-size")
private int maximum_packet_size;
@JsonProperty("rate")
private int rate;
@JsonProperty("slack-term")
private int slack_term;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("token-bucket-rate")
public int getToken_bucket_rate() {
return token_bucket_rate;
}

@JsonProperty("token-bucket-rate")
public void setToken_bucket_rate(int token_bucket_rate) {
this.token_bucket_rate = token_bucket_rate;
}

public Flowspec withToken_bucket_rate(int token_bucket_rate) {
this.token_bucket_rate = token_bucket_rate;
return this;
}

@JsonProperty("token-bucket-size")
public int getToken_bucket_size() {
return token_bucket_size;
}

@JsonProperty("token-bucket-size")
public void setToken_bucket_size(int token_bucket_size) {
this.token_bucket_size = token_bucket_size;
}

public Flowspec withToken_bucket_size(int token_bucket_size) {
this.token_bucket_size = token_bucket_size;
return this;
}

@JsonProperty("peak-data-rate")
public int getPeak_data_rate() {
return peak_data_rate;
}

@JsonProperty("peak-data-rate")
public void setPeak_data_rate(int peak_data_rate) {
this.peak_data_rate = peak_data_rate;
}

public Flowspec withPeak_data_rate(int peak_data_rate) {
this.peak_data_rate = peak_data_rate;
return this;
}

@JsonProperty("minimum-policed-unit")
public int getMinimum_policed_unit() {
return minimum_policed_unit;
}

@JsonProperty("minimum-policed-unit")
public void setMinimum_policed_unit(int minimum_policed_unit) {
this.minimum_policed_unit = minimum_policed_unit;
}

public Flowspec withMinimum_policed_unit(int minimum_policed_unit) {
this.minimum_policed_unit = minimum_policed_unit;
return this;
}

@JsonProperty("maximum-packet-size")
public int getMaximum_packet_size() {
return maximum_packet_size;
}

@JsonProperty("maximum-packet-size")
public void setMaximum_packet_size(int maximum_packet_size) {
this.maximum_packet_size = maximum_packet_size;
}

public Flowspec withMaximum_packet_size(int maximum_packet_size) {
this.maximum_packet_size = maximum_packet_size;
return this;
}

@JsonProperty("rate")
public int getRate() {
return rate;
}

@JsonProperty("rate")
public void setRate(int rate) {
this.rate = rate;
}

public Flowspec withRate(int rate) {
this.rate = rate;
return this;
}

@JsonProperty("slack-term")
public int getSlack_term() {
return slack_term;
}

@JsonProperty("slack-term")
public void setSlack_term(int slack_term) {
this.slack_term = slack_term;
}

public Flowspec withSlack_term(int slack_term) {
this.slack_term = slack_term;
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
-----------------------------------org.opendaylight.cmts.Ugs.java-----------------------------------

package org.opendaylight.cmts;

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
"request-transmission-policy",
"unsolicited-grant-size",
"grants-interval",
"reserved",
"nominal-grant-interval",
"tolerated-grant-jitter",
"required-attribute-mask",
"forbidden-attribute-mask",
"attribute-aggregation-rule-mask"
})
public class Ugs {

@JsonProperty("request-transmission-policy")
private int request_transmission_policy;
@JsonProperty("unsolicited-grant-size")
private int unsolicited_grant_size;
@JsonProperty("grants-interval")
private int grants_interval;
@JsonProperty("reserved")
private int reserved;
@JsonProperty("nominal-grant-interval")
private int nominal_grant_interval;
@JsonProperty("tolerated-grant-jitter")
private int tolerated_grant_jitter;
@JsonProperty("required-attribute-mask")
private int required_attribute_mask;
@JsonProperty("forbidden-attribute-mask")
private int forbidden_attribute_mask;
@JsonProperty("attribute-aggregation-rule-mask")
private int attribute_aggregation_rule_mask;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("request-transmission-policy")
public int getRequest_transmission_policy() {
return request_transmission_policy;
}

@JsonProperty("request-transmission-policy")
public void setRequest_transmission_policy(int request_transmission_policy) {
this.request_transmission_policy = request_transmission_policy;
}

public Ugs withRequest_transmission_policy(int request_transmission_policy) {
this.request_transmission_policy = request_transmission_policy;
return this;
}

@JsonProperty("unsolicited-grant-size")
public int getUnsolicited_grant_size() {
return unsolicited_grant_size;
}

@JsonProperty("unsolicited-grant-size")
public void setUnsolicited_grant_size(int unsolicited_grant_size) {
this.unsolicited_grant_size = unsolicited_grant_size;
}

public Ugs withUnsolicited_grant_size(int unsolicited_grant_size) {
this.unsolicited_grant_size = unsolicited_grant_size;
return this;
}

@JsonProperty("grants-interval")
public int getGrants_interval() {
return grants_interval;
}

@JsonProperty("grants-interval")
public void setGrants_interval(int grants_interval) {
this.grants_interval = grants_interval;
}

public Ugs withGrants_interval(int grants_interval) {
this.grants_interval = grants_interval;
return this;
}

@JsonProperty("reserved")
public int getReserved() {
return reserved;
}

@JsonProperty("reserved")
public void setReserved(int reserved) {
this.reserved = reserved;
}

public Ugs withReserved(int reserved) {
this.reserved = reserved;
return this;
}

@JsonProperty("nominal-grant-interval")
public int getNominal_grant_interval() {
return nominal_grant_interval;
}

@JsonProperty("nominal-grant-interval")
public void setNominal_grant_interval(int nominal_grant_interval) {
this.nominal_grant_interval = nominal_grant_interval;
}

public Ugs withNominal_grant_interval(int nominal_grant_interval) {
this.nominal_grant_interval = nominal_grant_interval;
return this;
}

@JsonProperty("tolerated-grant-jitter")
public int getTolerated_grant_jitter() {
return tolerated_grant_jitter;
}

@JsonProperty("tolerated-grant-jitter")
public void setTolerated_grant_jitter(int tolerated_grant_jitter) {
this.tolerated_grant_jitter = tolerated_grant_jitter;
}

public Ugs withTolerated_grant_jitter(int tolerated_grant_jitter) {
this.tolerated_grant_jitter = tolerated_grant_jitter;
return this;
}

@JsonProperty("required-attribute-mask")
public int getRequired_attribute_mask() {
return required_attribute_mask;
}

@JsonProperty("required-attribute-mask")
public void setRequired_attribute_mask(int required_attribute_mask) {
this.required_attribute_mask = required_attribute_mask;
}

public Ugs withRequired_attribute_mask(int required_attribute_mask) {
this.required_attribute_mask = required_attribute_mask;
return this;
}

@JsonProperty("forbidden-attribute-mask")
public int getForbidden_attribute_mask() {
return forbidden_attribute_mask;
}

@JsonProperty("forbidden-attribute-mask")
public void setForbidden_attribute_mask(int forbidden_attribute_mask) {
this.forbidden_attribute_mask = forbidden_attribute_mask;
}

public Ugs withForbidden_attribute_mask(int forbidden_attribute_mask) {
this.forbidden_attribute_mask = forbidden_attribute_mask;
return this;
}

@JsonProperty("attribute-aggregation-rule-mask")
public int getAttribute_aggregation_rule_mask() {
return attribute_aggregation_rule_mask;
}

@JsonProperty("attribute-aggregation-rule-mask")
public void setAttribute_aggregation_rule_mask(int attribute_aggregation_rule_mask) {
this.attribute_aggregation_rule_mask = attribute_aggregation_rule_mask;
}

public Ugs withAttribute_aggregation_rule_mask(int attribute_aggregation_rule_mask) {
this.attribute_aggregation_rule_mask = attribute_aggregation_rule_mask;
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
-----------------------------------org.opendaylight.cmts.Us.java-----------------------------------

package org.opendaylight.cmts;

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
"traffic-priority",
"downstream-resequencing",
"reserved0",
"maximum-sustained-traffic-rate",
"maximum-traffic-burst",
"minimum-reserved-traffic-rate",
"assumed-minimum-reserved-traffic-rate-packet-size",
"reserved1",
"maximum-downstream-latency",
"downstream-peak-traffic-rate",
"required-attribute-mask",
"forbidden-attribute-mask",
"attribute-aggregation-rule-mask"
})
public class Us {

@JsonProperty("traffic-priority")
private int traffic_priority;
@JsonProperty("downstream-resequencing")
private int downstream_resequencing;
@JsonProperty("reserved0")
private int reserved0;
@JsonProperty("maximum-sustained-traffic-rate")
private int maximum_sustained_traffic_rate;
@JsonProperty("maximum-traffic-burst")
private int maximum_traffic_burst;
@JsonProperty("minimum-reserved-traffic-rate")
private int minimum_reserved_traffic_rate;
@JsonProperty("assumed-minimum-reserved-traffic-rate-packet-size")
private int assumed_minimum_reserved_traffic_rate_packet_size;
@JsonProperty("reserved1")
private int reserved1;
@JsonProperty("maximum-downstream-latency")
private int maximum_downstream_latency;
@JsonProperty("downstream-peak-traffic-rate")
private int downstream_peak_traffic_rate;
@JsonProperty("required-attribute-mask")
private int required_attribute_mask;
@JsonProperty("forbidden-attribute-mask")
private int forbidden_attribute_mask;
@JsonProperty("attribute-aggregation-rule-mask")
private int attribute_aggregation_rule_mask;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("traffic-priority")
public int getTraffic_priority() {
return traffic_priority;
}

@JsonProperty("traffic-priority")
public void setTraffic_priority(int traffic_priority) {
this.traffic_priority = traffic_priority;
}

public Us withTraffic_priority(int traffic_priority) {
this.traffic_priority = traffic_priority;
return this;
}

@JsonProperty("downstream-resequencing")
public int getDownstream_resequencing() {
return downstream_resequencing;
}

@JsonProperty("downstream-resequencing")
public void setDownstream_resequencing(int downstream_resequencing) {
this.downstream_resequencing = downstream_resequencing;
}

public Us withDownstream_resequencing(int downstream_resequencing) {
this.downstream_resequencing = downstream_resequencing;
return this;
}

@JsonProperty("reserved0")
public int getReserved0() {
return reserved0;
}

@JsonProperty("reserved0")
public void setReserved0(int reserved0) {
this.reserved0 = reserved0;
}

public Us withReserved0(int reserved0) {
this.reserved0 = reserved0;
return this;
}

@JsonProperty("maximum-sustained-traffic-rate")
public int getMaximum_sustained_traffic_rate() {
return maximum_sustained_traffic_rate;
}

@JsonProperty("maximum-sustained-traffic-rate")
public void setMaximum_sustained_traffic_rate(int maximum_sustained_traffic_rate) {
this.maximum_sustained_traffic_rate = maximum_sustained_traffic_rate;
}

public Us withMaximum_sustained_traffic_rate(int maximum_sustained_traffic_rate) {
this.maximum_sustained_traffic_rate = maximum_sustained_traffic_rate;
return this;
}

@JsonProperty("maximum-traffic-burst")
public int getMaximum_traffic_burst() {
return maximum_traffic_burst;
}

@JsonProperty("maximum-traffic-burst")
public void setMaximum_traffic_burst(int maximum_traffic_burst) {
this.maximum_traffic_burst = maximum_traffic_burst;
}

public Us withMaximum_traffic_burst(int maximum_traffic_burst) {
this.maximum_traffic_burst = maximum_traffic_burst;
return this;
}

@JsonProperty("minimum-reserved-traffic-rate")
public int getMinimum_reserved_traffic_rate() {
return minimum_reserved_traffic_rate;
}

@JsonProperty("minimum-reserved-traffic-rate")
public void setMinimum_reserved_traffic_rate(int minimum_reserved_traffic_rate) {
this.minimum_reserved_traffic_rate = minimum_reserved_traffic_rate;
}

public Us withMinimum_reserved_traffic_rate(int minimum_reserved_traffic_rate) {
this.minimum_reserved_traffic_rate = minimum_reserved_traffic_rate;
return this;
}

@JsonProperty("assumed-minimum-reserved-traffic-rate-packet-size")
public int getAssumed_minimum_reserved_traffic_rate_packet_size() {
return assumed_minimum_reserved_traffic_rate_packet_size;
}

@JsonProperty("assumed-minimum-reserved-traffic-rate-packet-size")
public void setAssumed_minimum_reserved_traffic_rate_packet_size(int assumed_minimum_reserved_traffic_rate_packet_size) {
this.assumed_minimum_reserved_traffic_rate_packet_size = assumed_minimum_reserved_traffic_rate_packet_size;
}

public Us withAssumed_minimum_reserved_traffic_rate_packet_size(int assumed_minimum_reserved_traffic_rate_packet_size) {
this.assumed_minimum_reserved_traffic_rate_packet_size = assumed_minimum_reserved_traffic_rate_packet_size;
return this;
}

@JsonProperty("reserved1")
public int getReserved1() {
return reserved1;
}

@JsonProperty("reserved1")
public void setReserved1(int reserved1) {
this.reserved1 = reserved1;
}

public Us withReserved1(int reserved1) {
this.reserved1 = reserved1;
return this;
}

@JsonProperty("maximum-downstream-latency")
public int getMaximum_downstream_latency() {
return maximum_downstream_latency;
}

@JsonProperty("maximum-downstream-latency")
public void setMaximum_downstream_latency(int maximum_downstream_latency) {
this.maximum_downstream_latency = maximum_downstream_latency;
}

public Us withMaximum_downstream_latency(int maximum_downstream_latency) {
this.maximum_downstream_latency = maximum_downstream_latency;
return this;
}

@JsonProperty("downstream-peak-traffic-rate")
public int getDownstream_peak_traffic_rate() {
return downstream_peak_traffic_rate;
}

@JsonProperty("downstream-peak-traffic-rate")
public void setDownstream_peak_traffic_rate(int downstream_peak_traffic_rate) {
this.downstream_peak_traffic_rate = downstream_peak_traffic_rate;
}

public Us withDownstream_peak_traffic_rate(int downstream_peak_traffic_rate) {
this.downstream_peak_traffic_rate = downstream_peak_traffic_rate;
return this;
}

@JsonProperty("required-attribute-mask")
public int getRequired_attribute_mask() {
return required_attribute_mask;
}

@JsonProperty("required-attribute-mask")
public void setRequired_attribute_mask(int required_attribute_mask) {
this.required_attribute_mask = required_attribute_mask;
}

public Us withRequired_attribute_mask(int required_attribute_mask) {
this.required_attribute_mask = required_attribute_mask;
return this;
}

@JsonProperty("forbidden-attribute-mask")
public int getForbidden_attribute_mask() {
return forbidden_attribute_mask;
}

@JsonProperty("forbidden-attribute-mask")
public void setForbidden_attribute_mask(int forbidden_attribute_mask) {
this.forbidden_attribute_mask = forbidden_attribute_mask;
}

public Us withForbidden_attribute_mask(int forbidden_attribute_mask) {
this.forbidden_attribute_mask = forbidden_attribute_mask;
return this;
}

@JsonProperty("attribute-aggregation-rule-mask")
public int getAttribute_aggregation_rule_mask() {
return attribute_aggregation_rule_mask;
}

@JsonProperty("attribute-aggregation-rule-mask")
public void setAttribute_aggregation_rule_mask(int attribute_aggregation_rule_mask) {
this.attribute_aggregation_rule_mask = attribute_aggregation_rule_mask;
}

public Us withAttribute_aggregation_rule_mask(int attribute_aggregation_rule_mask) {
this.attribute_aggregation_rule_mask = attribute_aggregation_rule_mask;
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


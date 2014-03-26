/*
 * @header@
 */

package org.opendaylight.controller.dal.northbound;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.opendaylight.controller.dal.nodes.cmts;

@XmlRootElement (name = "list")
@XmlAccessorType(XmlAccessType.NONE)

public class CMTSConfigs {
        @XmlElement
        List<CmtsConfig> cmtsConfig;
        //To satisfy JAXB
        private CMTSConfigs() {

        }

        public CMTSFlowConfigs(List<CmtsConfig> cmtsConfig) {
                this.cmtsConfig = cmtsConfig;
        }

        public List<CmtsConfig> getCmtsConfig() {
                return cmtsConfig;
        }

        public void setCmtsConfig(List<CmtsConfig> cmtsConfig) {
                this.cmtsConfig = cmtsConfig;
        }
}

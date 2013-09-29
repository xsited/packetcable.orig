/**
 * 
 */
package org.pcmm.gates;

import org.pcmm.base.IPCMMBaseObject;

/**
 * @author rhadjamor@gmail.com
 * 
 */
public interface IGateID extends IPCMMBaseObject {
	static final short LENGTH = 8;
	static final short SNUM = 4;
	static final short STYPE = 1;

	void setGateID(int gateID);

	int getGateID();
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package copsObjs;

import java.nio.ByteBuffer;

/**
 *
 * @author pedro
 */
public class CopsPEPIDObj extends CopsObj {

    private String PEPString;
    public CopsPEPIDObj(String Pep) {
        super((byte)11,(byte)1);
        this.PEPString=Pep.concat("\0");			//  PERGUNTAR AO DIOGO PORQUÊ É QUE SE TEM DE COLOCAR ISTO!
    }

    @Override
    public byte[] Packet(){
        ByteBuffer PacoteTemp = ByteBuffer.allocate(super.Packet().length+this.PEPString.getBytes().length);
		super.setLength((short) (super.Packet().length+this.PEPString.getBytes().length));
		PacoteTemp.put(super.Packet());
        PacoteTemp.put(this.PEPString.getBytes());
        return PacoteTemp.array();
    }


}

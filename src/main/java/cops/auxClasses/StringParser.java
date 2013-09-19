/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auxClasses;

/**
 *
 * @author pedro
 */
public class StringParser {

    private String policerReq = "";
    private String id = "";
    private String inDev = "";
    private String outDev = "";
    private String src = "";
    private String dst = "";
    private String prot = "";
    private String src_port = "";
    private String dst_port = "";
    private String pepString = "";

    public StringParser(String policerReq) {
        this.policerReq = policerReq;
    }

    public void parser() {
        String limiter = "[ ]+";
        String limiterId = "id=";
        String limiterIn = "indev=";
        String limiterOut = "outdev=";
        String limiterSrc = "src=";
        String limiterDst = "dst=";
        String limiterProt = "prot=";
        String limiterSrcP = "src_port=";
        String limiterDstP = "dst_port=";
        String[] tokens = this.policerReq.split(limiter);
        String[] idPar = tokens[0].split(limiterId);
        String[] inDevPar = tokens[1].split(limiterIn);
        String[] outDevPar = tokens[2].split(limiterOut);
        String[] srcPar = tokens[3].split(limiterSrc);
        String[] dstPar = tokens[4].split(limiterDst);
        String[] protPar = tokens[5].split(limiterProt);
        String[] srcPPar = tokens[6].split(limiterSrcP);
        String[] dstPPar = tokens[7].split(limiterDstP);
        tokens = this.policerReq.split("id=[0-9]+ ");
        this.pepString = tokens[1];
        this.id = idPar[1];
        this.inDev = inDevPar[1];
        this.outDev = outDevPar[1];
        this.src = srcPar[1];
        this.dst = dstPar[1];
        this.prot = protPar[1];
        this.src_port = srcPPar[1];
        this.dst_port = dstPPar[1];
    }

    public String getDst() {
        return dst;
    }

    public String getDst_port() {
        return dst_port;
    }

    public String getId() {
        return id;
    }

    public String getInDev() {
        return inDev;
    }

    public String getOutDev() {
        return outDev;
    }

    public String getPepString() {
        return pepString;
    }

    public String getPolicerReq() {
        return policerReq;
    }

    public String getProt() {
        return prot;
    }

    public String getSrc() {
        return src;
    }

    public String getSrc_port() {
        return src_port;
    }

    

}

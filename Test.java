
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

// jcops
import org.umu.cops.common.*;
import org.umu.cops.stack.*;
import org.umu.cops.prpdp.COPSPdpException;

//pcmm
import org.pcmm.PCMMDef;
import org.pcmm.PCMMPdpAgent;
import org.pcmm.PCMMPdpDataProcess;
import org.pcmm.rcd.IPCMMPolicyServer;
import org.pcmm.rcd.impl.PCMMPolicyServer;


import org.pcmm.PCMMGlobalConfig;
import org.pcmm.gates.IAMID;
import org.pcmm.gates.IClassifier;
import org.pcmm.gates.IGateSpec;
import org.pcmm.gates.IGateSpec.DSCPTOS;
import org.pcmm.gates.IGateSpec.Direction;
import org.pcmm.gates.IPCMMGate;
import org.pcmm.gates.ISubscriberID;
import org.pcmm.gates.ITrafficProfile;
import org.pcmm.gates.ITransactionID;
import org.pcmm.gates.impl.AMID;
import org.pcmm.gates.impl.Classifier;
import org.pcmm.gates.impl.DOCSISServiceClassNameTrafficProfile;
import org.pcmm.gates.impl.GateSpec;
import org.pcmm.gates.impl.PCMMGateReq;
import org.pcmm.gates.impl.SubscriberID;
import org.pcmm.gates.impl.TransactionID;
import org.pcmm.messages.IMessage;
import org.pcmm.messages.impl.MessageFactory;
import org.pcmm.objects.MMVersionInfo;
import org.pcmm.state.IState;
import org.pcmm.PCMMPdpMsgSender;
import org.pcmm.PCMMPdpAgent;




public class Test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        byte[] data = "Hello World".getBytes();

//        short transactionID = 300;
//        Socket socket_id = new Socket();
        System.out.println("Test - starting Client");
        PCMMPdpDataProcess process;
        PCMMPdpAgent lpdp;
        PCMMPdpAgent rpdp;
        PCMMPdpAgent pcmm_pdp;

        IPCMMPolicyServer ps = new PCMMPolicyServer();
        IPCMMPolicyServer lps = new PCMMPolicyServer();

        process = new PCMMPdpDataProcess();
        lpdp = new PCMMPdpAgent(PCMMDef.C_PCMM, process) ;
        rpdp = new PCMMPdpAgent(PCMMDef.C_PCMM, process) ;
        pcmm_pdp = new PCMMPdpAgent(PCMMDef.C_PCMM, process) ;

        /*
         *

        COPSHeader hdr = new COPSHeader (COPSHeader.COPS_OP_DEC, PCMMDef.C_PCMM);
        // Client Handle with the same clientHandle as the request

        COPSHandle handle = new COPSHandle();
        COPSDecisionMsg decisionMsg = new COPSDecisionMsg();

        IPCMMGate gate = new PCMMGateReq();
        ITransactionID trID = new TransactionID();
        IAMID amid = new AMID();
        ISubscriberID subscriberID = new SubscriberID();
        IGateSpec gateSpec = new GateSpec();
        IClassifier classifier = new Classifier();

        ITrafficProfile trafficProfile = new DOCSISServiceClassNameTrafficProfile();
        // new pcmm specific clientsi
        COPSClientSI clientSD = new COPSClientSI(COPSObjHeader.COPS_DEC, (byte)4);
         */

        boolean quit = false;
        int menuItem;
        do {
            // print menu
            System.out.println("MENU         ");
            System.out.println("=============");
            System.out.println("1. CMTS+requestCMTSConnection+gateSet");
            //System.out.println("1. Add Flow 1");
            // System.out.println("2. Add Flow 2");
            System.out.println("2. CMTS+PCMMPdpAgent.connect+PCMMPdpMsgSender Add Flow");
            System.out.println("3. Toggle Flow");
            System.out.println("4. Remove Flow 1");
            System.out.println("5. Remove Flow 2");
            System.out.println("6. Localhost Open");
            System.out.println("7. localhost+requestCMTSConnection+gateSet");
            System.out.println("8. Remote Open");
            System.out.println("9. localhost+PCMMPdpAgent.connect+PCMMMsgPdpSender Add Flow");
            System.out.println("0. Quit");
            // handle user commands
            System.out.print("Enter Choice: ");
            menuItem = in.nextInt();
            switch (menuItem) {
            case 1:
                System.out.println("Open and Add Flow 1");
                ps.requestCMTSConnection(PCMMGlobalConfig.DefaultCMTS);
                ps.gateSet();
                break;
            case 2:
                System.out.println("Open and Add Flow 2");
                try  {
                    pcmm_pdp.connect( PCMMGlobalConfig.DefaultCMTS, 3918 );
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
		PCMMPdpMsgSender pcmm_sender =
			 new PCMMPdpMsgSender (PCMMDef.C_PCMM, pcmm_pdp.getClientHandle(), pcmm_pdp.getSocket());
                try {
		    //pcmm_sender.sendGateSet();
		    pcmm_sender.sendGateSetBestEffortWithExtendedClassifier();
                } catch (COPSPdpException e) {
                    System.out.println("Failed to sendGateSet, reason: " + e.getMessage());
                }
		break;
	    case 10:
                break;
            case 3:
                System.out.println("Toggle Flow");
                break;
            case 4:
                System.out.println("Remove Flow 1");
                break;
            case 5:
                System.out.println("Remove Flow 2");
                break;
            case 6:
                System.out.println("Localhost Open");
                try  {
                    lpdp.connect( "localhost", 3918 );
                } catch (Exception e) {

                    System.out.println(e.getMessage());
                }
                break;
            case 7:
                System.out.println("localhost+requestCMTSConnection+gateSet");
                // ps.requestCMTSConnection(PCMMGlobalConfig.DefaultCMTS);
                // socket_id = ps.requestCMTSConnection("10.32.4.232");
                lps.requestCMTSConnection("127.0.0.1");
                lps.gateSet();
                break;
            case 8:
                System.out.println("Remote Open connect");
                try  {
                    pcmm_pdp.connect( PCMMGlobalConfig.DefaultCMTS, 3918 );
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 9:
                System.out.println("Localhost Open connect");
                try  {
                    lpdp.connect( "localhost", 3918 );
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
		PCMMPdpMsgSender pcmm_sender2 =
			 new PCMMPdpMsgSender ( PCMMDef.C_PCMM, lpdp.getClientHandle(), lpdp.getSocket());
                try {
		    pcmm_sender2.sendGateSet();
                } catch (COPSPdpException e) {
                    System.out.println("Failed to sendGateSet, reason: " + e.getMessage());
                }
                break;
            case 0:
                quit = true;
/*
        	if (lpdp.isConnected()) lpdp.disconnect();
        	if (lpdp.isConnected()) rpdp.disconnect();
        	if (lpdp.isConnected()) pcmm_pdp.disconnect();
*/
                break;
            default:
                System.out.println("Invalid choice.");
            }
        } while (!quit);
    }
}




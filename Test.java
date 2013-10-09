
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;

// jcops
import org.umu.cops.common.*;
import org.umu.cops.stack.*;
import org.pcmm.PCMMDef;
import org.pcmm.PCMMPdpAgent;
import org.pcmm.PCMMPdpDataProcess;
import org.pcmm.rcd.IPCMMPolicyServer;
import org.pcmm.rcd.impl.PCMMPolicyServer;
import org.umu.cops.prpdp.COPSPdpException;


import java.util.Scanner;
//pcmm
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
/*
                try  {
                    pcmm_pdp.connect( PCMMGlobalConfig.DefaultCMTS, 3918);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
*/

                /* Example of an UNSOLICITED decision
                 * <Gate Control Command> =
                 *     <COPS Common Header> <Client Handle> <Context>
                 *     <Decision Flags> <ClientSI Data>
                 * <ClientSI Data> = <Gate-Set> | <Gate-Info> | <Gate-Delete> |
                 *                   <PDP-Config> | <Synch-Request> | <Msg-Receipt>
                         * <Gate-Set> = <Decision Header> <TransactionID> <AMID> <SubscriberID>
                 *              [<GateID>] <GateSpec> <Traffic Profile> <classifier>
                 * 		[<classifier...>] [<Event Generation Info>]
                     *              [<Volume-Based Usage Limit>] [<Time-Based Usage Limit>]
                 *	        [<Opaque Data>] [<UserID>]
                // Common Header with the same ClientType as the request
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




                handle.setId(new COPSData("0"));; // getClientHandle().getId());




                // set transaction ID to gate set
                trID.setGateCommandType(ITransactionID.GateSet);
                // transactionID = (short) (transactionID == 0 ? (short) (Math.random() * hashCode()) : transactionID);
                trID.setTransactionIdentifier(transactionID);


                amid.setApplicationType((short) 0);
                amid.setApplicationMgrTag((short) 0);
                gateSpec.setDirection(Direction.UPSTREAM);
                gateSpec.setDSCP_TOSOverwrite(DSCPTOS.OVERRIDE);
                gateSpec.setTimerT1( PCMMGlobalConfig.GateT1 );
                gateSpec.setTimerT2( PCMMGlobalConfig.GateT2 );
                gateSpec.setTimerT3( PCMMGlobalConfig.GateT3 );
                gateSpec.setTimerT4( PCMMGlobalConfig.GateT4 );

                trafficProfile.setEnvelop((byte) 0x0);
                ((DOCSISServiceClassNameTrafficProfile) trafficProfile)
                .setServiceClassName("S_up");

                classifier.setProtocol((short)6);
                try {
                    InetAddress subIP = InetAddress.getByName("10.32.104.2");
                    InetAddress srcIP = InetAddress.getByName("10.32.0.208");
                    InetAddress dstIP = InetAddress.getByName("10.32.154.2");
                    subscriberID.setSourceIPAddress(subIP);
                    classifier.setSourceIPAddress(srcIP);
                    classifier.setDestinationIPAddress(dstIP);
                } catch (UnknownHostException unae) {
                    System.out.println("Error getByName"+unae.getMessage());
                }
                classifier.setSourcePort((short) 0);
                classifier.setDestinationPort((short) 8081);

                gate.setTransactionID(trID);
                gate.setAMID(amid);
                gate.setSubscriberID(subscriberID);
                gate.setGateSpec(gateSpec);
                gate.setTrafficProfile(trafficProfile);
                gate.setClassifier(classifier);
                data = gate.getData();

                // new pcmm specific clientsi
                clientSD.setData(new COPSData(data, 0, data.length));

                try {
                    decisionMsg.add(hdr);
                    decisionMsg.add(handle);
                    // Decisions (no flags supplied)
                    //  <Context>
                    COPSContext cntxt = new COPSContext(COPSContext.CONFIG, (short) 0);
                    COPSDecision install = new COPSDecision();
                    install.setCmdCode(COPSDecision.DEC_INSTALL);
                    install.setFlags(COPSDecision.F_REQERROR);
                    decisionMsg.addDecision(install, cntxt);
                    decisionMsg.add(clientSD);		// setting up the gate
                    try {
                        decisionMsg.dump(System.out);
                    } catch (IOException unae) {
                        System.out.println("Error dumping "+unae.getMessage());
                    }

                } catch (COPSException e) {
                    System.out.println("Error making Msg"+e.getMessage());
                }

                //** Send the decision
                //**
                try {
                    decisionMsg.writeData(pcmm_pdp.getSocket());
                    //decisionMsg.writeData(socket_id);
                } catch (IOException e) {
                    System.out.println("Failed to send the decision, reason: " + e.getMessage());
                }

                 */
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
                break;
            default:
                System.out.println("Invalid choice.");
            }
        } while (!quit);
    }
}




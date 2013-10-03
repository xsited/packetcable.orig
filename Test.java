
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.Socket;
import java.util.Scanner;

// jcops
import org.umu.cops.common.*;
import org.umu.cops.stack.*;
import org.pcmm.PCMMDef;
import org.pcmm.PCMMPdpAgent;
import org.pcmm.PCMMPdpDataProcess;
import org.pcmm.rcd.IPCMMPolicyServer;
import org.pcmm.rcd.impl.PCMMPolicyServer;
import org.umu.cops.prpdp.COPSPdpException;




public class Test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        byte[] data = "Hello World".getBytes();

	Socket socket_id = new Socket();
        System.out.println("Test - starting Client");
        PCMMPdpDataProcess process;
        PCMMPdpAgent lpdp;
        PCMMPdpAgent rpdp;

        IPCMMPolicyServer ps = new PCMMPolicyServer();

        process = new PCMMPdpDataProcess();
        lpdp = new PCMMPdpAgent(PCMMDef.C_PCMM, process) ;
        rpdp = new PCMMPdpAgent(PCMMDef.C_PCMM, process) ;

        boolean quit = false;
        int menuItem;
        do {
        // print menu
        System.out.println("MENU         ");
        System.out.println("=============");
        System.out.println("1. Add Flow 1");
        System.out.println("2. Add Flow 2");
        System.out.println("3. Toggle Flow");
        System.out.println("4. Remove Flow 1");
        System.out.println("5. Remove Flow 2");
        System.out.println("6. Localhost Open");
        System.out.println("7. requestCMTSConnection");
        System.out.println("8. Remote Open");
        System.out.println("9. Close All");
        System.out.println("0. Quit");
        // handle user commands
            System.out.print("Enter Choice: ");
            menuItem = in.nextInt();
            switch (menuItem) {
            case 1:
                System.out.println("Add Flow 1");
                ps.gateSet();
                break;
            case 2:
                System.out.println("Add Flow 2");
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
		 */
		// Common Header with the same ClientType as the request
		COPSHeader hdr = new COPSHeader (COPSHeader.COPS_OP_DEC, PCMMDef.C_PCMM);

		// Client Handle with the same clientHandle as the request
		COPSHandle handle = new COPSHandle();
		handle.setId(new COPSData("0"));; // getClientHandle().getId());




		COPSDecisionMsg decisionMsg = new COPSDecisionMsg();
		// new pcmm specific clientsi 
		COPSClientSI clientSD = new COPSClientSI(COPSObjHeader.COPS_DEC, (byte)4);
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
                        try {
			decisionMsg.dump(System.out);
                        } catch (IOException unae) {
                        }

		}
		catch (COPSException e) {
		System.out.println("Error making Msg"+e.getMessage());
		}

		//** Send the decision
		//**
		try
		{
			decisionMsg.writeData(socket_id);
		}
		catch (IOException e)
		{
		System.out.println("Failed to send the decision, reason: " + e.getMessage());
		}

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
                    rpdp.connect( "localhost", 3918 );
                } catch (Exception e) {

                    System.out.println(e.getMessage());
                }
                break;
            case 7:
                System.out.println("PCMMPolicyServer.requestCMTSConnection");
		socket_id = ps.requestCMTSConnection("10.32.4.3");
		//ps.requestCMTSConnection("127.0.0.1");


                break;		
            case 8:
                System.out.println("Remote Open");
                try  {
                    lpdp.connect( "10.32.4.3", 3918 );
                } catch (Exception e) {

                    System.out.println(e.getMessage());
                }
                break;
            case 9:
                System.out.println("Close All");
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




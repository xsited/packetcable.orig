
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// jcops
import org.umu.cops.common.*;
import org.umu.cops.stack.*;
import org.pcmm.PCMMDef;
import org.pcmm.PCMMPdpAgent;
import org.pcmm.PCMMPdpDataProcess;
import org.pcmm.rcd.IPCMMPolicyServer;
import org.pcmm.rcd.impl.PCMMPolicyServer;



import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        byte[] data = "Hello World".getBytes();

        System.out.println("Test - starting Client");
        PCMMPdpDataProcess process;
        PCMMPdpAgent lpdp;
        PCMMPdpAgent rpdp;

        process = new PCMMPdpDataProcess();
        lpdp = new PCMMPdpAgent(PCMMDef.C_PCMM, process) ;
        rpdp = new PCMMPdpAgent(PCMMDef.C_PCMM, process) ;
        // print menu
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
        boolean quit = false;
        int menuItem;
        do {
            System.out.print("Enter Choice: ");
            menuItem = in.nextInt();
            switch (menuItem) {
            case 1:
                System.out.println("Add Flow 1");
                break;
            case 2:
                System.out.println("Add Flow 2");
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
		IPCMMPolicyServer ps = new PCMMPolicyServer();
		ps.requestCMTSConnection("10.32.4.3");


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




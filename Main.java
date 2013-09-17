import java.io.IOException;
import net.protocol.common.util.*;
import net.protocol.cops.*;
import org.program.connection.*;
 
 
class Main {
    public static void main(String[] args){
        String nodeId = new String("00:02:fc:84:08:1a");
        byte[] sourceMac = new byte[6];
        System.out.printf("0x%02X%02X%02X%02X%02X%02X\n",
                                                sourceMac[0],
                                                sourceMac[1],
                                                sourceMac[2],
                                                sourceMac[3],
                                                sourceMac[4],
                                                sourceMac[5]);
        System.out.println("Main - starting Server");
//      System.out.println(args[0]);
        int port = 3918;

        Server server = null;
        try {
            server = new Server(port);
        }
        catch (IOException e) {
            e.printStackTrace(System.err);
        }
        server.waitForConnections();
    }
}

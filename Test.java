 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// jcops
import org.umu.cops.common.COPSDebug;
import org.umu.cops.stack.*;
import org.umu.cops.common.COPS_def;
import org.umu.cops.prpep.COPSPepAgent;
import org.umu.cops.prpep.COPSPepConnection;
import org.umu.cops.prpep.COPSPepReqStateMan;

 
class TextMenuItem implements Runnable {
 
    private String title;
    private Runnable exec;
 
    protected TextMenuItem(String title) { this(title, null); }
 
    public TextMenuItem(String title, Runnable exec) {
        this.title= title;
        this.exec= exec;
    }
 
    public String getTitle() { return title; }
 
    public boolean isExec() { return exec != null; }
 
    protected void setExec(Runnable exec) { this.exec= exec; }
 
    public void run() {
 
        try {
            exec.run();
        }
        catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
 
class TextMenu extends TextMenuItem {
 
    private static final TextMenuItem quit= new TextMenuItem("quit", new Runnable() {
        public void run() {
            System.exit(0);
        }
    });
 
    private static final TextMenuItem back= new TextMenuItem("back");
 
    List<TextMenuItem> items;
 
    public TextMenu(String title, TextMenuItem ... items) { this(title, false, true, items); }
 
    public TextMenu(String title, boolean addBack, boolean addQuit, TextMenuItem ... items) {
        super(title);
        setExec(this);
 
        initialize(addBack, addQuit, items);
    }
 
    private void initialize(boolean addBack, boolean addQuit, TextMenuItem ... items) {
 
        this.items= new ArrayList<TextMenuItem>(Arrays.asList(items));
        if (addBack) this.items.add(back);
        if (addQuit) this.items.add(quit);
    }
 
    private void display() {
 
        int option= 1;
        System.out.println(getTitle());
        for (TextMenuItem item : items) {
            System.out.println((option++)+": "+item.getTitle());
        }
        System.out.print("Enter selection: ");
        System.out.flush();
    }
 
    private TextMenuItem prompt() throws IOException {
 
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
 
        while (true) {
 
            display();
 
            String line = br.readLine();
            try {
                int option= Integer.parseInt(line);
                if (option >= 1 && option <= items.size())
                    return items.get(option-1);
            }
            catch (NumberFormatException e) { }
 
            System.out.println("not a valid menu option: "+line);
        } 
    }
 
    public void run() {
 
        try {
            for (TextMenuItem item= prompt(); item.isExec(); item= prompt())
                item.run();
        }
        catch (Throwable t) {
            t.printStackTrace(System.out);
        }
    }
}



class Test {
 
    private static TextMenuItem item1= new TextMenuItem("Add Flow 1",new Runnable() {
        public void run() {
            System.out.println("Add Flow 1");
        }
    });
 
    private static TextMenuItem item2= new TextMenuItem("Add Flow 2",new Runnable() {
        public void run() {
            System.out.println("Add Flow 2");
        }
    });
 
    private static TextMenuItem item3= new TextMenuItem("Toggle Flow",new Runnable() {
        public void run() {
            System.out.println("Toggle Flow");
        }
    });

    private static TextMenuItem item4= new TextMenuItem("Remove Flow 1",new Runnable() {
        public void run() {
            System.out.println("Remove Flow 1");
        }
    });

    private static TextMenuItem item5= new TextMenuItem("Remove Flow 2",new Runnable() {
        public void run() {
            System.out.println("Remove Flow 2");
        }
    });
 
/*
    private static TextMenuItem item6= new TextMenuItem("Quit",new Runnable() {
        public void run() {
            System.out.println("Quit");
            System.exit(0);
        }
    });
*/

    private static TextMenu nestedMenu= new TextMenu("nested menu", true, false, item2, item3);
    //private static TextMenu topMenu= new TextMenu("top menu", false, true, item1, nestedMenu);
    private static TextMenu topMenu= new TextMenu("\nMenu        \n--------------", false, true, item1, item2, item3, item4, item5);
 
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 3918;
        byte[] data = "Hello World".getBytes();
	
        System.out.println("Test - starting Client");


        topMenu.run();
    }
}


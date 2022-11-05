import com.jcraft.jsch.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class Main extends Frame {

    final static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        // Create frame ( GUI )
        Frame frame = new Frame();
        JButton button = frame.addButton("Start");
        ArrayList<String> hosts = addDevices();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    connectToDevices(hosts, frame.getUsername(), frame.getPassword(), frame.getCommand(), frame.getTextArea());
                    frame.addScrollBar(frame.getTextArea());
                    frame.clearFields();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    /**
     * Connect to single device and execute command
     * @param username
     * @param password
     * @param host
     * @param port
     * @param command
     * @return
     * @throws Exception
     */
    public static String listFolderStructure(String username, String password,
                                             String host, int port, String command) throws Exception {

        Session session = null;
        ChannelExec channel = null;
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        PrintWriter pr = null;
        OutputStream out = null;
        InputStream in = null;
        try {

            session = new JSch().getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.setOutputStream(responseStream);
            channel.connect();
            // Elevate
            out = channel.getOutputStream();
            in = channel.getErrStream();
            InputStream errStream = channel.getErrStream();
            pr = new PrintWriter(out, true);
            pr.println("en");
            byte[] errTmp = new byte[1024];
            while (errStream.available() > 0) {
                int i = errStream.read(errTmp, 0, 1024);
                if (i < 0)
                    break;
                String sysout = new String(errTmp, 0, i);
                System.out.println("Error Line:" + sysout);
                if (sysout.toLowerCase().contains("sword")) {
                }
                pr.println("y"); // Works till here but not deleting file
            }
                while (channel.isConnected()) {
                    Thread.sleep(100);
                }

            String responseString = new String(responseStream.toByteArray());

            // Output formatting
            StringBuffer sb = new StringBuffer();
            String[] split = responseString.split(" ");
            for(String s : split) {
                sb.append(String.format("%-2s ",s));
            }
            // return output
                return sb.toString();

            }catch(Exception e) {
                return "Error occurred\n. Please make sure that credentials and command are correct and try again";
            }
            finally {
                if (session != null) {
                    session.disconnect();
                }
                if (channel != null) {
                    channel.disconnect();
                }
            }
    }


    /**
     * Read from file devices.txt list of devices
     * @return list of devices
     * @throws FileNotFoundException
     */
    public static ArrayList<String> addDevices () throws FileNotFoundException {
            String path = "devices.txt";
            Scanner ss = new Scanner(new File(path));
            ArrayList<String> list = new ArrayList<String>();
            while (ss.hasNext()) {
                list.add(ss.next());
            }
            ss.close();
            return list;
        }

    /**
     * Loop through list of devices to execute command
     * @param devices
     * @param username
     * @param password
     * @param command
     * @param area
     * @throws Exception
     */
        public static void connectToDevices (ArrayList < String > devices, String username, String password, String
        command, JTextArea area) throws Exception {
            StringBuffer sb = new StringBuffer();

            for (String host : devices) {
                area.append("=================================================================");
                area.append("\n");
                area.append("Host: " + host);
                area.append("\n");
                area.append("\n");
                area.append(listFolderStructure(username, password, host, 22, command));
                area.append("=================================================================");
            }
        }
    }





import java.net.*;
import java.io.*;

public class HTTPAsk {
    public static void main(String[] args) throws IOException {
        // Your code here
        int port = Integer.parseInt(args[0]);

        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            try {

                Socket connectionSocket = serverSocket.accept();

                BufferedReader input = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

                DataOutputStream output = new DataOutputStream(connectionSocket.getOutputStream());


                String url = input.readLine();
                String[] params = url.split("[? &=/]");
                StringBuilder sb = new StringBuilder();

                try {
                    if (params[0].equals("GET") && (params[7].equals("HTTP")|| params[9].equals("HTTP"))) {
                        if (params[2].equals("ask") && params[3].equals("hostname") && params[5].equals("port")) {
                            if (params.length == 9) {
                                sb.append("HTTP/1.1 200 OK\r\n\r\n");
                                sb.append(TCPClient.askServer(params[4], Integer.parseInt(params[6])));
                            } else if (params.length == 11 && params[7].equals("string")) {
                                sb.append("HTTP/1.1 200 OK\r\n\r\n");
                                sb.append(TCPClient.askServer(params[4], Integer.parseInt(params[6]), params[8]));
                            }
                            else {
                                sb.append("HTTP/1.1 404 Not Found");
                            }
                        } else {
                            sb.append("HTTP/1.1 404 Not Found\r\n");
                        }
                    }

                        else {
                            sb.append("HTTP/1.1 400 Bad Request\r\n");
                        }
                }catch (Exception ex){
                    sb = new StringBuilder();
                    sb.append("HTTP/1.1 404 Not Found\r\n");
                }


                System.out.println(connectionSocket.isConnected());

                System.out.println("klar");
                System.out.println(sb.toString());
                output.writeBytes(sb.toString());

                connectionSocket.close();
                output.close();
                input.close();
            } catch (Exception ex) {
                System.out.println("fail");
            }
        }
    }

}

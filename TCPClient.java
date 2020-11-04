import java.net.*;
import java.io.*;

public class TCPClient {

    public static String askServer(String hostname, int port, String ToServer) throws IOException {
        if (ToServer == null)
            return askServer(hostname, port);
        else {

            Socket socket = new Socket(hostname, port);
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            byte[] b = encode(ToServer);
            output.write(b);

            socket.setSoTimeout(2000);
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[1024];
            int byteLength = 0;
            while (byteLength != -1 && socket.isConnected()) {
                try {
                    byteLength = input.read(buffer);
                    if (byteLength != -1)
                        sb.append(decode(buffer, byteLength));
                } catch (Exception ex) {
                    byteLength = -1;
                }
            }

            socket.close();
            String s = sb.toString();
            return s;
        }
    }

    public static String askServer(String hostname, int port) throws IOException {

        Socket socket = new Socket(hostname, port);
        InputStream input = socket.getInputStream();

        socket.setSoTimeout(2000);
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];
        int byteLength = 0;
        while (byteLength != -1 && socket.isConnected()) {
            try {
                byteLength = input.read(buffer);
                if (byteLength != -1)
                    sb.append(decode(buffer, byteLength));

            } catch (Exception ex) {
                byteLength = -1;
            }
        }

        socket.close();
        String s = sb.toString();
        return s;
    }

    private static String decode(byte[] b, int length) throws UnsupportedEncodingException {
        String s = new String(b, 0, length, "UTF-8");
        return s;
    }

    private static byte[] encode(String s) throws UnsupportedEncodingException {
        s = s + '\n';
        byte[] bytes = s.getBytes("UTF-8");
        return bytes;
    }
}


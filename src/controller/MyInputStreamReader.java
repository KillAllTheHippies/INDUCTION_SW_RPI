package controller;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jamie on 17/05/16.
 */
class MyInputStreamReader extends Thread{
    boolean isStop = false;
    ReadEventHandler handler;
    String tag;
    InputStream in;
    public MyInputStreamReader(InputStream in)
    {
        this.in = in;
    }

    public void setHandler(ReadEventHandler handler) {
        this.handler = handler;
    }
    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public void run()
    {
        byte[] buff = new byte[8192];
        while (true) {
            //String line;
            try {
                int len  = in.read(buff);
                if (len == -1)
                {
                    return;
                }

                String line = new String(buff, 0, len);
                if (handler!=null)
                    handler.onReceived(line);
                System.out.println(tag +" " + line);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }
    public void dispose()
    {
        this.isStop = true;
    }
    public interface ReadEventHandler
    {
        void onReceived(String line);
    }
}

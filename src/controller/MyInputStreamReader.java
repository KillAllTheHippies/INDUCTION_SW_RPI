package controller;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jamie on 17/05/16.
 */
class MyInputStreamReader extends Thread{
    private ReadEventHandler handler;
    private String tag;
    private InputStream in;
    MyInputStreamReader(InputStream in)
    {
        this.in = in;
    }


    void setTag(String tag)
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
        boolean isStop = true;
    }
    public interface ReadEventHandler
    {
        void onReceived(String line);
    }
}

package cn.outsourceit;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created with IntelliJ IDEA.
 * User: Xin Gao
 * Date: 25/08/12
 * Time: 12:18 AM
 * Project: Torrent Tracker
 */
public class servermonitor {

    public static void main(String[] arg)  {
    if(arg.length ==0){
        System.err.println("password: YES");
        return;
    }
    String urlS = "http://23.23.88.223:8088/server/status";

    String password = arg[0];
if (arg.length ==2) {urlS = arg[1];
    check(urlS, password, "skljfpqwfdkl");
}

    //alert();

    while(true){
        sendOnPeriod(password);
        check("http://23.23.88.223:8088/server/status", password, "works63");
        try{Thread.sleep(600000);}catch (Exception e){System.err.println(e.getMessage());}
        check("http://23.23.88.223:8088/serverstatus", password, "Login Page");
        try{Thread.sleep(600000);}catch (Exception e){System.err.println(e.getMessage());}
        System.out.println();
    }

}
    private static void check(String urlS, String password, String keyword){
        try{
            //String urlS = "http://223.88.223:8088/server/status";
            //http://www.java-samples.com/showtutorial.php?tutorialid=401
            //

            URL hp = new URL(urlS);
            //oracle.wait(60000);
            //System.out.println("1");
            URLConnection hpCon = hp.openConnection();
            hpCon.setConnectTimeout(60000);
            hpCon.setReadTimeout(60000);

            InputStream input = hpCon.getInputStream();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(input));
            //System.out.println("2");

            String inputLine;
            boolean notFound = true;
            while ((inputLine = in.readLine()) != null) {
                if(inputLine.contains(keyword)){notFound=false;break;}
            }
            if(notFound)alert(password, keyword + " is not found");
            else                 System.out.println(inputLine+" "+new Date().toString());


            in.close();
        }catch (Exception e){
            System.err.println(e.getMessage()+" "+ new Date().toString());
            alert(password, e.getMessage());
        }

    }
    private static void alert(String p, String t) {

/*
        int numbeeps = 10;

        for(int x=0;x<numbeeps;x++)
        {
            java.awt.Toolkit.getDefaultToolkit().beep();
            try{Thread.sleep(2000);}catch (Exception e){System.err.print(e.getMessage());}
        }
*/

/*
        byte[] buf = new byte[ 1 ];;
        AudioFormat af = new AudioFormat( (float )44100, 8, 1, true, false );
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl = AudioSystem.getSourceDataLine( af );
        sdl.open( af );
        sdl.start();
        for( int i = 0; i < 1000 * (float )44100 / 1000; i++ ) {
            double angle = i / ( (float )44100 / 440 ) * 2.0 * Math.PI;
            buf[ 0 ] = (byte )( Math.sin( angle ) * 100 );
            sdl.write( buf, 0, 1 );
        }
        sdl.drain();
        sdl.stop();
        sdl.close();
*/
        t += " " +new Date().toString();
        final String username = "cnxingao@gmail.com";
        final String password = p;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("cnxingao@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("chinesegary@hotmail.com"));
            message.setSubject("Server Alert");
            message.setText(t);

            Transport.send(message);

            System.out.println("Mail Sent "+ t);

        } catch (MessagingException e) {
            System.err.println(e.getMessage());
            //throw new RuntimeException(e);
        }

    }

    static long startTime = System.currentTimeMillis();
    //static long h24InMs = 1000 * 60;
    static long h24InMs = 1000 * 60 * 60 * 8;

        protected static void sendOnPeriod(String p){
            long now = System.currentTimeMillis();
            if(startTime + h24InMs < now){
                startTime = now;
                alert(p, getIP());
            } else
                System.out.println("Send my IP is on waiting ...");
        }

        private static String getIP() {
            String ip="";

            try{
                //String urlS = "http://223.88.223:8088/server/status";
                //http://www.java-samples.com/showtutorial.php?tutorialid=401
                //

                URL hp = new URL("http://ip2location.com");
                //oracle.wait(60000);
                //System.out.println("1");
                URLConnection hpCon = hp.openConnection();
                hpCon.setConnectTimeout(60000);
                hpCon.setReadTimeout(60000);

                InputStream input = hpCon.getInputStream();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(input));
                //System.out.println("2");

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if(inputLine.contains("<td><label>")){
                        inputLine = inputLine.replaceAll("<td><label>", "").
                                replace("</label></td>", "").
                                replaceAll("IP Address", "").
                                trim();
                        if (!inputLine.isEmpty()){
                        System.out.println("IP: " + inputLine);
                        ip = inputLine;
                        break;
                        }
                    }
                }
/*
                if(notFound)alert(password, keyword + " is not found");
                else                 System.out.println(inputLine+" "+new Date().toString());
*/


                in.close();
            }catch (Exception e){
                System.err.println(e.getMessage()+" "+ new Date().toString());
                ip = e.getMessage();
                //alert(password, e.getMessage());
            }


            return ip;
        }



}

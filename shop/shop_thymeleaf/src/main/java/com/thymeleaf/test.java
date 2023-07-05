package com.thymeleaf;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class test {

    public static void main(String[] args) {
        try{
           FileInputStream fi=new FileInputStream(new File(""));
           FileChannel channel = fi.getChannel();
           FileOutputStream fo=new FileOutputStream(new File(""));
           FileChannel channel1 = fo.getChannel();
           ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
           int length=-1;
           int outLength=0;
           while((length=channel.read(byteBuffer))!=-1){
               byteBuffer.flip();
               outLength=0;
               while((outLength=channel1.write(byteBuffer))!=0){
                   channel1.write(byteBuffer);
               }
               byteBuffer.clear();
           }
        }catch (Exception e){

        }finally {

        }

    }
}

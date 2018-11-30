package uet.oop.bomberman.gui;

import sun.audio.*;
import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.*;
import java.io.*;

public class Sound {
    protected static AudioPlayer MGP = AudioPlayer.player;
    protected static AudioStream BGM;
    protected static AudioData MD;
    protected InputStream test;
    protected static boolean play=true;
    
    public static void MainSound() {       
        ContinuousAudioDataStream loop = null;

        try
        {
            InputStream test = new FileInputStream("res/Sounds/MainSound.wav");
            BGM = new AudioStream(test);
            AudioPlayer.player.start(BGM);
            MD = BGM.getData();
            loop = new ContinuousAudioDataStream(MD);
        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
        MGP.start(loop);
    }
    
    public static void StartSound() {       
        ContinuousAudioDataStream loop = null;

        try
        {
            InputStream test = new FileInputStream("res/Sounds/B_A000.wav");
            BGM = new AudioStream(test);
            AudioPlayer.player.start(BGM);
        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
        MGP.start(loop);
    }
    
    public static void PlaceBombSound() {       
        ContinuousAudioDataStream loop = null;

        try
        {
            InputStream test = new FileInputStream("res/Sounds/PlaceBomb.wav");
            BGM = new AudioStream(test);
            AudioPlayer.player.start(BGM);
        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
        MGP.start(loop);
    }
	
    public static void FlameSound() {       
        ContinuousAudioDataStream loop = null;

        try
        {
            InputStream test = new FileInputStream("res/Sounds/BOM_11_L.wav");
            BGM = new AudioStream(test);
            AudioPlayer.player.start(BGM);
        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
        MGP.start(loop);
    }
    public static void KillEnemySound() {       
        ContinuousAudioDataStream loop = null;

        try
        {
            InputStream test = new FileInputStream("res/Sounds/killEnemy.wav");
            BGM = new AudioStream(test);
            AudioPlayer.player.start(BGM);
        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
        MGP.start(loop);
    }
    
    
    public static void FinishLevelSound() {       
        ContinuousAudioDataStream loop = null;

        try
        {
            InputStream test = new FileInputStream("res/Sounds/finishLevel.wav");
            BGM = new AudioStream(test);
            AudioPlayer.player.start(BGM);
        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
        MGP.start(loop);
    }
    public static void FinishGameSound() {       
        ContinuousAudioDataStream loop = null;

        try
        {
            InputStream test = new FileInputStream("res/Sounds/GameOver.wav");
            BGM = new AudioStream(test);
            AudioPlayer.player.start(BGM);
        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
        MGP.start(loop);
    }
    public static void PlayerDieSound() {       
        ContinuousAudioDataStream loop = null;

        try
        {
            InputStream test = new FileInputStream("res/Sounds/playerdie.wav");
            BGM = new AudioStream(test);
            AudioPlayer.player.start(BGM);
        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
        MGP.start(loop);
    }
    public static void CollectSound() {       
        ContinuousAudioDataStream loop = null;

        try
        {
            InputStream test = new FileInputStream("res/Sounds/CRYSTAL_A_11K.wav");
            BGM = new AudioStream(test);
            AudioPlayer.player.start(BGM);
        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
        MGP.start(loop);
    }
}
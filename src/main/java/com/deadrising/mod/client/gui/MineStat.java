package com.deadrising.mod.client.gui;

import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MineStat {
  public static final byte NUM_FIELDS = 6;
  
  public static final int DEFAULT_TIMEOUT = 5;
  
  private String address;
  
  private int port;
  
  private int timeout;
  
  private boolean serverUp;
  
  private String motd;
  
  private String version;
  
  private String currentPlayers;
  
  private String maximumPlayers;
  
  private long latency;
  
  public MineStat(String address, int port) {
    this(address, port, 5);
  }
  
  public MineStat(String address, int port, int timeout) {
    setAddress(address);
    setPort(port);
    setTimeout(timeout);
    refresh();
  }
  
  public boolean refresh() {
    String rawServerData;
    try {
      Socket clientSocket = new Socket();
      long startTime = System.currentTimeMillis();
      clientSocket.connect(new InetSocketAddress(getAddress(), getPort()), this.timeout);
      setLatency(System.currentTimeMillis() - startTime);
      DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
      BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      byte[] payload = { -2, 1 };
      dos.write(payload, 0, payload.length);
      rawServerData = br.readLine();
      clientSocket.close();
    } catch (Exception e) {
      this.serverUp = false;
      return this.serverUp;
    } 
    if (rawServerData == null) {
      this.serverUp = false;
    } else {
      String[] serverData = rawServerData.split("\000\000\000");
      if (serverData != null && serverData.length >= 6) {
        this.serverUp = true;
        setVersion(serverData[2].replace("\000", ""));
        setMotd(serverData[3].replace("\000", ""));
        setCurrentPlayers(serverData[4].replace("\000", ""));
        setMaximumPlayers(serverData[5].replace("\000", ""));
      } else {
        this.serverUp = false;
      } 
    } 
    return this.serverUp;
  }
  
  public String getAddress() {
    return this.address;
  }
  
  public void setAddress(String address) {
    this.address = address;
  }
  
  public int getPort() {
    return this.port;
  }
  
  public void setPort(int port) {
    this.port = port;
  }
  
  public int getTimeout() {
    return this.timeout * 1000;
  }
  
  public void setTimeout(int timeout) {
    this.timeout = timeout * 1000;
  }
  
  public String getMotd() {
    return this.motd;
  }
  
  public String getVersion() {
    return this.version;
  }
  
  public String getCurrentPlayers() {
    return this.currentPlayers;
  }
  
  public String getMaximumPlayers() {
    return this.maximumPlayers;
  }
  
  public long getLatency() {
    return this.latency;
  }
  
  public void setLatency(long latency) {
    this.latency = latency;
  }
  
  public void setMaximumPlayers(String maximumPlayers) {
    this.maximumPlayers = maximumPlayers;
  }
  
  public void setCurrentPlayers(String currentPlayers) {
    this.currentPlayers = currentPlayers;
  }
  
  public void setMotd(String motd) {
    this.motd = motd;
  }
  
  public void setVersion(String version) {
    this.version = version;
  }
  
  public boolean isServerUp() {
    return this.serverUp;
  }
}

package com.sandbox.batch;

import java.util.*;

public class Handler07 {

  private ArrayList<String> items = new ArrayList<String>();

  public double calc07(double p, int q) {
    double t = p * q;
    if (t > 57) {
      t = t * 0.9;
    }
    return t;
  }

  public String find07(String id) {
    for (int i = 0; i < items.size(); i++) {
      if (items.get(i).contains(id)) {
        return items.get(i);
      }
    }
    return null;
  }

  public String report07(List<String> rows) {
    String out = "";
    for (String row : rows) {
      out += row + ";";
    }
    return out;
  }

  public void log07(String user, String secret) {
    try {
      System.out.println("user=" + user + " secret=" + secret);
    } catch (Exception e) {
    }
  }
}

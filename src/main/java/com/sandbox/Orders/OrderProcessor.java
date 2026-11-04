package com.sandbox.Orders;

import java.io.*;
import java.util.*;

public class OrderProcessor {

  private static final double FREE_SHIPPING_THRESHOLD = 50.0;

  private ArrayList<String> orderLog = new ArrayList<String>();

  public double calcTot(double p, int q) {
    double t = p * q;
    t = t + t * 0.2;
    if (q > 10) {
      t = t - t * 0.15;
    }
    return t;
  }

  public boolean valid(String s) {
    return s != null && s.length() == 8 && s.startsWith("ORD-");
  }

  public String readOrderFile(String path) {
    StringBuilder content = new StringBuilder();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(path));
      String line;
      while ((line = reader.readLine()) != null) {
        content.append(line).append("\n");
      }
    } catch (Exception e) {
    }
    return content.toString();
  }

  public String processOrder(String customer, String email, String address, String item, int quantity,
                             double price, boolean express, String coupon) {
    String result = "";
    if (customer != null) {
      if (!customer.isEmpty()) {
        if (quantity > 0) {
          if (price > 0) {
            if (express) {
              result = "EXPRESS:" + customer + ":" + item + ":" + (price * quantity * 1.5);
            } else {
              result = "STANDARD:" + customer + ":" + item + ":" + (price * quantity);
            }
            if (coupon != null && coupon.equals("SAVE10")) {
              result = result + ":DISCOUNT10";
            }
            orderLog.add(result);
            System.out.println("Order for " + email + " shipped to " + address);
          }
        }
      }
    }
    return result;
  }

  public String findOrder(String id) {
    for (int i = 0; i < orderLog.size(); i++) {
      if (orderLog.get(i).contains(id)) {
        return orderLog.get(i);
      }
    }
    return null;
  }

  public String buildReport(List<String> items) {
    String report = "";
    for (String item : items) {
      report += "Item: " + item + "; ";
    }
    return report;
  }

  public int parseOrderId(String raw) {
    try {
      return Integer.parseInt(raw.split("-")[1]);
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  public void sendNotification(String email, String cardNumber) {
    try {
      System.out.println("Sending receipt to " + email + " for card " + cardNumber);
      if (email.isEmpty()) {
        throw new Exception("Email is empty");
      }
    } catch (Exception e) {
      System.out.println("Failed: " + e.getMessage());
    }
  }

  /**
   * Checks whether an order qualifies for free shipping.
   *
   * @param orderTotal total price of the order
   * @return true if the total reaches the free shipping threshold
   */
  public boolean isEligibleForFreeShipping(double orderTotal) {
    return orderTotal >= FREE_SHIPPING_THRESHOLD;
  }

  /**
   * Formats a numeric order number as a public order id.
   *
   * @param number positive order number
   * @return the formatted id, e.g. {@code ORD-0042}
   */
  public String formatOrderId(int number) {
    return String.format("ORD-%04d", number);
  }
}

package com.example.clothesshop.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
     String idOrder;
     String name;
     int phone;
     String address;
     ArrayList<Cart> list;
     String messages;
     int status;
     int total;
     String createAt;
     String createCancel;
     String dateConfrim;
     String dateDelivery;
     String dateSuccess;
     public Order(String idOrder, String name, int phone, String address, ArrayList<Cart> list, String messages,
        int status, int total, String createAt,String createCancel,String dateConfrim,String dateDelivery,String dateSuccess) {
          this.idOrder = idOrder;
          this.name = name;
          this.phone = phone;
          this.address = address;
          this.list = list;
          this.messages = messages;
          this.status = status;
          this.total = total;
          this.createAt = createAt;
          this.createCancel = createCancel;
          this.dateConfrim = dateConfrim;
          this.dateDelivery = dateDelivery;
          this.dateSuccess = dateSuccess;
     }

     public Order() {
     }

     public String getIdOrder() {
          return idOrder;
     }

     public void setIdOrder(String idOrder) {
          this.idOrder = idOrder;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public int getPhone() {
          return phone;
     }

     public void setPhone(int phone) {
          this.phone = phone;
     }

     public String getAddress() {
          return address;
     }

     public void setAddress(String address) {
          this.address = address;
     }

     public ArrayList<Cart> getList() {
          return list;
     }

     public void setList(ArrayList<Cart> list) {
          this.list = list;
     }

     public String getMessages() {
          return messages;
     }

     public void setMessages(String messages) {
          this.messages = messages;
     }

     public int getStatus() {
          return status;
     }

     public void setStatus(int status) {
          this.status = status;
     }

     public int getTotal() {
          return total;
     }

     public void setTotal(int total) {
          this.total = total;
     }

     public String getCreateAt() {
          return createAt;
     }

     public void setCreateAt(String createAt) {
          this.createAt = createAt;
     }

     public String getCreateCancel() {
          return createCancel;
     }

     public void setCreateCancel(String createCancel) {
          this.createCancel = createCancel;
     }

     public String getDateConfrim() {
          return dateConfrim;
     }

     public void setDateConfrim(String dateConfrim) {
          this.dateConfrim = dateConfrim;
     }

     public String getDateDelivery() {
          return dateDelivery;
     }

     public void setDateDelivery(String dateDelivery) {
          this.dateDelivery = dateDelivery;
     }

     public String getDateSuccess() {
          return dateSuccess;
     }

     public void setDateSuccess(String dateSuccess) {
          this.dateSuccess = dateSuccess;
     }
}

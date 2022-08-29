package io.vacco.ziminiar;

public class ZnRestaurant {

  public int id;
  public String name;
  public String addr, city;
  public String phone;
  public String type, rClass;

  @Override public String toString() {
    return String.format("[%d, %s, %s, %s, %s, %s, %s]", id, name, addr, city, phone, type, rClass);
  }
}

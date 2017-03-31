import org.sql2o.*;

public class Stylist {
  private int id;
  private String name;

  public Stylist(String name) {
    this.name = name;

  }

  public String getName() {
    return this.name;
  }

  public int getId() {
    return this.id;
  }

}

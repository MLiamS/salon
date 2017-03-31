import org.sql2o.*;

public class Client {
  private int id;
  private String name;

  public Client(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public int getId() {
    return this.id;
  }
}

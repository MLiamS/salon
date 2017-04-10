import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Stylist {
  private int id;
  private String name;
  private String rate;
  private boolean color;


  public Stylist(String name, String rate, boolean color) {
    this.name = name;
    this.rate = rate;
    this.color = color;

  }

  public String getName() {
    return this.name;
  }

  public String getRate() {
    return this.rate;
  }

  public boolean getColor() {
    return this.color;
  }

  public int getId() {
    return this.id;
  }

  public static List<Stylist> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists";
      return con.createQuery(sql)
      .addColumnMapping("rate", "rate")
      .addColumnMapping("color", "color")
      .executeAndFetch(Stylist.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylists (name, rate, color) VALUES (:name, :rate, :color)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("rate", this.rate)
      .addParameter("color", this.color)
      .executeUpdate()
      .getKey();
    }
  }

  @Override
  public boolean equals(Object stylist) {
    if(!(stylist instanceof Stylist)) {
      return false;
    } else {
      Stylist newStylist = (Stylist) stylist;
      return this.getName().equals(newStylist.getName()) && this.getId() == newStylist.getId();
    }
  }


  public static Stylist find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists WHERE id = :id";
      Stylist stylist = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylist.class);
      return stylist;
    }
  }
}

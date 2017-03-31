import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

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

  public static List<Stylist> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists";
      return con.createQuery(sql)
      .executeAndFetch(Stylist.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylists (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
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

}

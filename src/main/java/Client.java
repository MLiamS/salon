import org.sql2o.*;
import java.util.List;

public class Client {
  private int id;
  private String name;
  private String phone;
  private int stylistId;

  public Client(String name, String phone, int stylistId) {
    this.name = name;
    this.phone = phone;
    this.stylistId = stylistId;
  }

  public String getName() {
    return this.name;
  }

  public String getPhone() {
    return this.phone;
  }

  public int getId() {
    return this.id;
  }

  public int getStylistId() {
    return this.stylistId;
  }



  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients (name, phone, id_stylists) VALUES (:name, :phone, :id_stylists)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("phone", this.phone)
      .addParameter("id_stylists", this.stylistId)
      .executeUpdate()
      .getKey();
    }
  }

  public static List<Client> all() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM clients";
    return con.createQuery(sql)
    .addColumnMapping("id_stylists", "stylistId")
    .executeAndFetch(Client.class);
    }
  }

  @Override
  public boolean equals(Object oldClient) {
    if(!(oldClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) oldClient;
      return this.getName().equals(newClient.getName()) && this.getId() == newClient.getId();
    }
  }

  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .addColumnMapping("id_stylists", "stylistId")
        .executeAndFetchFirst(Client.class);
    }
  }

    public static List<Client> allFromStylist(int stylistId) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM clients WHERE id_stylists = :id";
        return con.createQuery(sql)
        .addParameter("id", stylistId)
        .addColumnMapping("id_stylists", "stylistId")
        .executeAndFetch(Client.class);
      }
    }

    public void update(String phone) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE clients SET phone = :phone WHERE id = :id";
        con.createQuery(sql)
        .addParameter("phone", phone)
        .addParameter("id",id)
        .executeUpdate();
      }
    }

    public void delete() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "DELETE FROM clients WHERE id = :id;";
        con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
      }
    }
}

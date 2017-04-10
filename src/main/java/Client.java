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
    .addColumnMapping("id_stylists", "StylistId")
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
      Client client = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Client.class);
      return client;
    }
  }

    public static List<Client> allFromStylist(int stylistId) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM clients WHERE id_stylists = :id";
        return con.createQuery(sql)
        .addParameter("id", stylistId)
        .addColumnMapping("id_stylists", "StylistId")
        .executeAndFetch(Client.class);
      }
    }
}

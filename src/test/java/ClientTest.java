import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);
  }

  @After
  public void tearDown() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients *;";
      con.createQuery(sql).executeUpdate();
    }
  }
  @Test
  public void Client_instantiatesCorrectly_True() {
    Client client = new Client("Frank", "5031112345", 1);
    assertTrue(client instanceof Client);
  }

  @Test
  public void clientGettersReturn_true() {
    Client client = new Client ("Frank", "5031112345", 1);
    assertEquals(client.getName(), "Frank");
    assertEquals(client.getPhone(),"5031112345");
    assertEquals(client.getStylistId(), 1);
  }

  @Test
  public void save_savesClientsToDataBase_True() {
    Client client = new Client ("Frank", "5031112345", 1);
    client.save();
    assertTrue(Client.all().get(0).equals(client));
  }

    @Test
    public void allFromStylist_returnsAllClientsFromStylistByStylitsId_True() {
      Client client1 = new Client("Sam", "5031112345", 1);
      client1.save();
      Client client2 = new Client("Gary", "5031112345", 2);
      client2.save();
      assertTrue(Client.allFromStylist(2).contains(client2));
      assertFalse(Client.allFromStylist(2).contains(client1));
    }
  }

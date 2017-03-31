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
      String sql = "DELETE FROM Clients *;";
      con.createQuery(sql).executeUpdate();
    }
  }
  @Test
  public void Client_instantiatesCorrectly_True() {
    Client client = new Client("Frank", 1);
    assertTrue(client instanceof Client);
  }

  @Test
  public void Client_clientGetttersReturn_true() {
    Client client = new Client ("Frank", 1);
    assertEquals(client.getName() , "Frank");
  }

}

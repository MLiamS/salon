import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class StylistTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);
  }

  @After
  public void tearDown() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM stylists *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Stylist_instantiatesCorrectly_True() {
    Stylist stylist = new Stylist("Frank");
    assertTrue(stylist instanceof Stylist);
  }

  @Test
  public void Stylist_stylistGetttersReturn_true() {
    Stylist stylist = new Stylist ("Frank");
    assertEquals(stylist.getName() , "Frank");
  }

  @Test
  public void Stylist_savesToDatabase_true() {
    Stylist stylist = new Stylist("Frank");
    stylist.save();
    assertTrue(Stylist.all().get(0).equals(stylist));
  }

}

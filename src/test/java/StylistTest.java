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
    Stylist stylist = new Stylist("Frank", "12", true);
    assertTrue(stylist instanceof Stylist);
  }

  @Test
  public void Stylist_stylistGetttersReturn_true() {
    Stylist stylist = new Stylist ("Frank", "12", true);
    assertEquals(stylist.getName() , "Frank");
  }

  @Test
  public void Stylist_savesToDatabase_true() {
    Stylist stylist = new Stylist("Frank", "12", true);
    stylist.save();
    assertTrue(Stylist.all().get(0).equals(stylist));
  }

  @Test
  public void Stylist_foundFromDatabase_true() {
    Stylist stylist1 = new Stylist("Frank", "12", true);
    stylist1.save();
    Stylist stylist2 = new Stylist("Frank", "12", true);
    stylist2.save();
    assertEquals(stylist2, Stylist.find(stylist2.getId()));

  }
}

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/stylist-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String rate = request.queryParams("rate");
      boolean color;
      if (request.queryParams("color") == null) {
        color = false;
      } else {
        color = true;
      }
      Stylist newStylist = new Stylist(name, rate, color);
      newStylist.save();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("editStylist", true);
      model.put("stylist", stylist);
      model.put("clients", Client.allFromStylist(Integer.parseInt(request.params(":id"))));
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id/clients/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put ("stylist", stylist);
      model.put("template", "templates/client-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("client-name");
      String phone = request.queryParams("client-phone");
      Client client = new Client(name, phone, Integer.parseInt(request.params(":id")));
      client.save();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("editStylist", true);
      model.put("stylist", stylist);
      model.put("clients", Client.allFromStylist(Integer.parseInt(request.params(":id"))));
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("stylists/:stylist_id/clients/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
      Client client = Client.find(Integer.parseInt(request.params(":id")));
      model.put("editClient", true);
      model.put("stylist", stylist);
      model.put("client", client);
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:stylist_id/clients/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String option = request.queryParams("option");
      Client client = Client.find(Integer.parseInt(request.params("id")));
      Stylist stylist = Stylist.find(client.getStylistId());
      if (option.equals("edit")) {
        String url = String.format("/stylists/%d/clients/%d", stylist.getId(), client.getId());
      String phoneNum = request.queryParams("phoneNumber");
      client.update(phoneNum);
      response.redirect(url);
      }
      else {client.delete();
        String url = String.format("/stylists/%d", stylist.getId());
        response.redirect(url);
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/stylists/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylist);
      // model.put("clients", Client.allFromStylist(Integer.parseInt(request.params(":id"))));
      model.put("template", "templates/deleteprompt.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylist);
      stylist.delete();
      response.redirect("/stylists");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylist);
      model.put("template", "templates/stylist-edit.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylist);
      String option = request.queryParams("option");
          if (option.equals("rate")) {
        String url = String.format("/stylists/%d", stylist.getId());
      String rate = request.queryParams("rate");
      stylist.updateRate(rate);
      response.redirect(url);
      }
      else if (option.equals("color")) {
        String url = String.format("/stylists/%d", stylist.getId());
        boolean color;
        if (request.queryParams("color") == null) {
          color = false;
        } else {
          color = true;
        }
      stylist.updateColor(color);
      response.redirect(url);
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}

package RachlinBabies.API;

import RachlinBabies.Model.Product;
import RachlinBabies.Service.ProductDao;
import RachlinBabies.Utils.ResponseMessage;

import static RachlinBabies.Utils.JsonUtil.json;
import static RachlinBabies.Utils.JsonUtil.toJson;
import static spark.Spark.exception;
import static spark.Spark.get;

class ProductController {

  ProductController(final ProductDao productService) {
    get("/products/search/:name", (req, res) -> {
      String name = req.params(":name");
      return productService.searchProduct(name, null);
    }, json());

    get("/products/search/:name/:limit", (req, res) -> {
      String name = req.params(":name");
      Integer limit = Integer.parseInt(req.params("limit"));
      return productService.searchProduct(name, limit);
    }, json());


    get("/products/:ndb", (req, res) -> {
      int ndb = Integer.parseInt(req.params(":ndb"));
      Product product = productService.get(ndb);
      if (product != null) {
        return product;
      }
      res.status(404);
      return new ResponseMessage(String.format("No product with ndb %d found", ndb));
    }, json());

    exception(Exception.class, (e, req, res) -> {
      res.status(400);
      res.body(toJson(new ResponseMessage(e)));
    });
  }
}

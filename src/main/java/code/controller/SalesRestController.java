package code.controller;

import code.JSON.OrderedJSON;
import code.JSON.StatusJSON;
import code.JSON.ToyJSON;
import code.model.Account;
import code.model.Ordered;
import code.model.Toy;
import code.service.AccountService;
import code.service.OrderedService;
import code.service.ToyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SalesRestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderedService orderedService;

    @Autowired
    private ToyService toyService;

    @RequestMapping(value = "/delivering/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderedJSON>> listDelivering(Principal principal) {

        if (principal != null) {

            Account account = accountService.findAccountByUsername(principal.getName());

            if ("ROLE_SELLER".equals(account.getRole())) {

                List<Ordered> orders = orderedService.findAllByStatusAndAccountId("DELIVERING", account.getId());

                if (!orders.isEmpty()) {

                    List<OrderedJSON> ordersJSON = new ArrayList<>();

                    for (Ordered order : orders) {

                        OrderedJSON ord = new OrderedJSON();

                        ord.setId(order.getId());
                        ord.setOrderDate(order.getOrderDate().toString());
                        ord.setQuantity(order.getQuantity());
                        ord.setTotalPrice(order.getQuantity() * order.getToy().getPrice());
                        ord.setCustomerName(order.getAccount().getName());
                        ord.setCustomerPhone(order.getAccount().getPhone());
                        ord.setCustomerEmail(order.getAccount().getEmail());
                        ord.setCustomerAddress(order.getAccount().getAddress());
                        ord.setCustomerId(order.getAccount().getId());
                        ord.setToyName(order.getToy().getName());
                        ord.setToyId(order.getToy().getId());

                        ordersJSON.add(ord);
                    }

                    return new ResponseEntity<>(ordersJSON, HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/history/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderedJSON>> historyOrders(Principal principal) {

        if (principal != null) {

            Account account = accountService.findAccountByUsername(principal.getName());

            if ("ROLE_SELLER".equals(account.getRole())) {

                List<Ordered> orders = orderedService.findAllByStatusNotAndAccountId("DELIVERING", account.getId());

                if (!orders.isEmpty()) {

                    List<OrderedJSON> ordersJSON = new ArrayList<>();

                    for (Ordered order : orders) {

                        OrderedJSON ord = new OrderedJSON();

                        ord.setId(order.getId());
                        ord.setOrderDate(order.getOrderDate().toString());
                        ord.setQuantity(order.getQuantity());
                        ord.setTotalPrice(order.getQuantity() * order.getToy().getPrice());
                        ord.setCustomerName(order.getAccount().getName());
                        ord.setCustomerPhone(order.getAccount().getPhone());
                        ord.setCustomerEmail(order.getAccount().getEmail());
                        ord.setCustomerAddress(order.getAccount().getAddress());
                        ord.setCustomerId(order.getAccount().getId());
                        ord.setToyName(order.getToy().getName());
                        ord.setToyId(order.getToy().getId());
                        ord.setDeliveredDate(order.getDeliveredDate().toString());
                        ord.setStatus(order.getStatus());

                        ordersJSON.add(ord);
                    }

                    return new ResponseEntity<>(ordersJSON, HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/remove/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createComment(@RequestBody StatusJSON status, Principal principal) {

        if (principal != null) {

            Account account = accountService.findAccountByUsername(principal.getName());

            List<Ordered> orders = orderedService.findAllByStatusAndAccountId("DELIVERING", account.getId());

            for (Ordered order : orders) {

                if (order.getId() == status.getOrderId()) {
                    order.setStatus(status.getStatus());
                    order.setDeliveredDate(new Date(System.currentTimeMillis()));
                    orderedService.save(order);
                    return new ResponseEntity<Void>(HttpStatus.CREATED);
                }

            }
        }

        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/inventory/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ToyJSON>> listToys(Principal principal) {

        if (principal != null) {

            Account account = accountService.findAccountByUsername(principal.getName());

            if ("ROLE_SELLER".equals(account.getRole())) {

                List<Toy> toys = toyService.findBySellerId(account.getId());

                if (!toys.isEmpty()) {

                    List<ToyJSON> toysJSON = new ArrayList<>();

                    for (Toy toy : toys) {

                        ToyJSON toyJSON = new ToyJSON();

                        toyJSON.setId(toy.getId());
                        toyJSON.setName(toy.getName());
                        toyJSON.setImage(toy.getImage());
                        toyJSON.setPrice(toy.getPrice());
                        toyJSON.setQuantityInStock(toy.getQuantityInStock());
                        toyJSON.setManufacturingDate(toy.getManufacturingDate().toString());
                        toyJSON.setDescription(toy.getDescription());
                        toyJSON.setInformation(toy.getInformation());
                        toyJSON.setOldPrice(toy.getOldPrice());
                        toyJSON.setBrandId(toy.getBrand().getId());
                        toyJSON.setBrandName(toy.getBrand().getName());
                        toyJSON.setCategoryId(toy.getCategory().getId());
                        toyJSON.setCategoryName(toy.getCategory().getName());

                        toysJSON.add(toyJSON);
                    }

                    return new ResponseEntity<>(toysJSON, HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/toy/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id, Principal principal) {

        if (principal != null) {

            Account account = accountService.findAccountByUsername(principal.getName());

            if ("ROLE_SELLER".equals(account.getRole())) {

                Toy toy = toyService.findById(id);

                if (toy.getAccount().getId() == account.getId()) {
                    toyService.remove(id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

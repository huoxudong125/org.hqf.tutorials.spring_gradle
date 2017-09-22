package  org.hqf.tutorials.spring.restapi.controller;

import java.util.List;

import org.hqf.tutorials.spring.restapi.dao.CustomerDAO;
import org.hqf.tutorials.spring.restapi.po.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class CustomerRestController {


    @Autowired
    private CustomerDAO customerDAO;


    @RequestMapping(value = "/customers",method = RequestMethod.POST)
    public List getCustomers() {
        return customerDAO.list();
    }

    @RequestMapping(value="/customers/{id}",method=RequestMethod.GET)
    public ResponseEntity getCustomer(@PathVariable("id") Long id) {

        Customer customer = customerDAO.get(id);
        if (customer == null) {
            return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(customer, HttpStatus.OK);
    }

    @RequestMapping(value = "/customers",method = RequestMethod.POST)
    public ResponseEntity createCustomer(@RequestBody Customer customer) {

        customerDAO.create(customer);

        return new ResponseEntity(customer, HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/{id}",method = RequestMethod.DELETE)
    public ResponseEntity deleteCustomer(@PathVariable Long id) {

        if (null == customerDAO.delete(id)) {
            return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(id, HttpStatus.OK);

    }

    @RequestMapping(value = "/customers/{id}",method = RequestMethod.PUT)
    public ResponseEntity updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {

        customer = customerDAO.update(id, customer);

        if (null == customer) {
            return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(customer, HttpStatus.OK);
    }

}
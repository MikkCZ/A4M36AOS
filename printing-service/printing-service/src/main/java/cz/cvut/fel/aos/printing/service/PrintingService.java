package cz.cvut.fel.aos.printing.service;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "PrintingService")
@Stateless()
public class PrintingService {

    @WebMethod(operationName = "print")
    public String print(@WebParam(name = "jsonstring") String jsonstring) {
        System.out.println("AHOOOJ");
        return "";
    }
}

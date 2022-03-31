package softuni.exam.service;

import softuni.exam.models.entity.Seller;

import javax.xml.bind.JAXBException;
import java.io.IOException;

//ToDo - Before start App implement this Service and set areImported to return false
public interface SellerService {
    
    boolean areImported();

    String readSellersFromFile() throws IOException;

    String importSellers() throws IOException, JAXBException;

    boolean isExistById(Long id);

    Seller findById(Long id);
}

package hiberspring.service.impl;

import hiberspring.domain.dtos.xml.ProductSeedRootDto;
import hiberspring.domain.entities.Product;
import hiberspring.repository.ProductRepository;
import hiberspring.service.BranchService;
import hiberspring.service.ProductService;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_FILE_PATH = "src/main/resources/files/products.xml";

    private final ProductRepository productRepository;
    private final BranchService branchService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, BranchService branchService, ModelMapper modelMapper,
                              ValidationUtil validationUtil, FileUtil fileUtil, XmlParser xmlParser) {
        this.productRepository = productRepository;
        this.branchService = branchService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public Boolean productsAreImported() {
        return this.productRepository.count() >0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return fileUtil.readFile(PRODUCT_FILE_PATH);
    }

    @Override
    public String importProducts() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(PRODUCT_FILE_PATH, ProductSeedRootDto.class)
                .getProducts()
                .stream()
                .filter(productSeedDto -> {

                    boolean isValid = validationUtil.isValid(productSeedDto)
                            && branchService.isExistEntity(productSeedDto.getBranch());

                    sb.append(isValid
                    ? String.format("Successfully imported Product %s.", productSeedDto.getName())
                            : "Error: Invalid data.")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(productSeedDto -> {
                    Product product = modelMapper.map(productSeedDto, Product.class);

                    product.setBranch(branchService.findByName(productSeedDto.getBranch()));

                    return product;
                })
                .forEach(productRepository::save);
        return sb.toString();
    }
}

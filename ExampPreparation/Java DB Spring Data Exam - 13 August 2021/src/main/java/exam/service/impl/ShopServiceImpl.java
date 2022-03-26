package exam.service.impl;

import exam.model.entity.Shop;
import exam.model.dto.xml.ShopSeedRootDto;
import exam.repository.ShopRepository;
import exam.service.ShopService;
import exam.service.TownService;
import exam.util.ValidationUtil;
import exam.util.XmlParse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    public static final String SHOP_FILE_PATH = "src/main/resources/files/xml/shops.xml";

    private final ShopRepository shopRepository;
    private final TownService townService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParse xmlParse;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, TownService townService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParse xmlParse) {
        this.shopRepository = shopRepository;
        this.townService = townService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParse = xmlParse;
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files
                .readString(Path.of(SHOP_FILE_PATH));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {

        StringBuilder sb = new StringBuilder();

        List<String> shopNames = new ArrayList<>();

        xmlParse.fromFile(SHOP_FILE_PATH, ShopSeedRootDto.class)
                .getShops()
                .stream()
                .filter(shopSeedDto -> {

                    boolean isValid = validationUtil.isValid(shopSeedDto);

                    String shopSeedDtoName = shopSeedDto.getName();
                    if (shopNames.contains(shopSeedDtoName)){
                        isValid = false;
                    }else {
                        shopNames.add(shopSeedDtoName);
                    }

                    sb.append(isValid
                    ? String.format("Successfully imported Shop %s - %.0f",
                            shopSeedDtoName, shopSeedDto.getIncome())
                    : "Invalid shop");

                    sb.append(System.lineSeparator());

                    return isValid;
                })
                .map(shopSeedDto -> {
                    Shop shop = modelMapper.map(shopSeedDto, Shop.class);
                    shop.setTown(townService.findByName(shopSeedDto.getTown().getName()));
                    return shop;
                })
                .forEach(shopRepository::save);

        return sb.toString();
    }

    @Override
    public Shop findByName(String name) {

        return shopRepository.findByName(name);
    }
}

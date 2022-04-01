package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.json.LaptopSeedDto;
import exam.model.entity.Laptop;
import exam.repository.LaptopRepository;
import exam.service.LaptopService;
import exam.service.ShopService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class LaptopServiceImpl implements LaptopService {

    public static final String LAPTOP_FILE_PATH = "src/main/resources/files/json/laptops.json";

    private final LaptopRepository laptopRepository;
    private final ShopService shopService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopService shopService, ModelMapper modelMapper,
                             ValidationUtil validationUtil, Gson gson) {
        this.laptopRepository = laptopRepository;
        this.shopService = shopService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(LAPTOP_FILE_PATH));
    }

    @Override
    public String importLaptops() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readLaptopsFileContent(), LaptopSeedDto[].class))
                .filter(laptopSeedDto -> {

                    boolean isValid = validationUtil.isValid(laptopSeedDto)
                            && !isExistEntity(laptopSeedDto.getMacAddress());

                    sb.append(isValid
                                    ? String.format("Successfully imported Laptop %s - %.2f - %d - %d",
                                    laptopSeedDto.getMacAddress(), laptopSeedDto.getCpuSpeed(),
                                    laptopSeedDto.getRam(), laptopSeedDto.getStorage())
                                    : "Invalid Laptop")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(laptopSeedDto -> {
                    Laptop laptop = modelMapper.map(laptopSeedDto, Laptop.class);
                    laptop.setShop(shopService.findByName(laptopSeedDto.getShop().getName()));
                    return laptop;
                })
                .forEach(laptopRepository::save);

        return sb.toString().trim();
    }

    private boolean isExistEntity(String macAddress) {
        return laptopRepository.existsByMacAddress(macAddress);
    }

    @Override
    public String exportBestLaptops() {
        return laptopRepository.findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddressAsc()
                .stream().map(laptop ->
                        String.format("Laptop - %s%n" +
                        "*Cpu speed - %.2f%n" +
                        "**Ram - %d%n" +
                        "***Storage - %d%n" +
                        "****Price - %.2f%n" +
                        "#Shop name - %s%n" +
                        "##Town - %s%n", laptop.getMacAddress(), laptop.getCpuSpeed(), laptop.getRam(),
                                laptop.getStorage(), laptop.getPrice(), laptop.getShop().getName(),
                                laptop.getShop().getTown().getName()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}

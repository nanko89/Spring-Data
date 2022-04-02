package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.ApartmentSeedRootDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParse;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    public static final String APARTMENT_PATH = "src/main/resources/files/xml/apartments.xml";

    private final ApartmentRepository apartmentRepository;
    private final TownService townService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParse xmlParse;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownService townService,
                                ModelMapper modelMapper, ValidationUtil validationUtil, XmlParse xmlParse) {
        this.apartmentRepository = apartmentRepository;
        this.townService = townService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParse = xmlParse;
    }

    @Override
    public boolean areImported() {
        return apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(APARTMENT_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParse.fromFile(APARTMENT_PATH, ApartmentSeedRootDto.class)
                .getApartments()
                .stream()
                .filter(apartmentSeedDto -> {
                    boolean isValid = validationUtil.isValid(apartmentSeedDto)
                            && townService.isExistTown(apartmentSeedDto.getTown())
                            && !isExistSameAreAndTownName(apartmentSeedDto.getArea(), apartmentSeedDto.getTown());

                    sb.append(isValid
                                    ? String.format("Successfully imported apartment %s - %.2f",
                                    apartmentSeedDto.getApartmentType(), apartmentSeedDto.getArea())
                                    : "Invalid apartment")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(apartmentSeedDto -> {
                    Apartment apartment = modelMapper.map(apartmentSeedDto, Apartment.class);
                    apartment.setTown(townService.findByTownName(apartmentSeedDto.getTown()));
                    return apartment;
                })
                .forEach(apartmentRepository::save);
        return sb.toString().trim();
    }


    @Override
    public Apartment findById(Long id) {
        return apartmentRepository.findById(id).orElse(null);
    }

    private boolean isExistSameAreAndTownName(Double area, String townName) {
        Town town = townService.findByTownName(townName);
        return apartmentRepository.existsByAreaAndTown(area, town);
    }
}

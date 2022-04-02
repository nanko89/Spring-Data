package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.OfferSeedRootDto;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.AgentService;
import softuni.exam.service.ApartmentService;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParse;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    public static final String OFFER_PATH = "src/main/resources/files/xml/offers.xml";

    private final OfferRepository offerRepository;
    private final AgentService agentService;
    private final ApartmentService apartmentService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParse xmlParse;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, AgentService agentService,
                            ApartmentService apartmentService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParse xmlParse) {
        this.offerRepository = offerRepository;
        this.agentService = agentService;
        this.apartmentService = apartmentService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParse = xmlParse;
    }


    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFER_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParse.fromFile(OFFER_PATH, OfferSeedRootDto.class)
                .getOffers()
                .stream()
                .filter(offerSeedDto -> {

                    boolean isValid = validationUtil.isValid(offerSeedDto)
                            && agentService.isExistByName(offerSeedDto.getAgent().getName());
                    sb.append(isValid
                    ? String.format("Successfully imported offer %.2f", offerSeedDto.getPrice())
                            : "Invalid offer")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(offerSeedDto -> {

                    Offer offer = modelMapper.map(offerSeedDto, Offer.class);
                    offer.setAgent(agentService.findByName(offerSeedDto.getAgent().getName()));
                    offer.setApartment(apartmentService.findById(offerSeedDto.getApartment().getId()));
                    return offer;
                })
                .forEach(offerRepository::save);

        return sb.toString().trim();
    }

    @Override
    public String exportOffers() {
        return offerRepository.findAllByApartment_ApartmentTypeOrderByApartment_AreaDescPriceAsc()
                .stream()
                .map(offer -> String.format("""
                                Agent %s %s with offer №%d:
                                   \t-Apartment area: %.2f
                                   \t--Town: %s
                                   \t---Price: %.2f$
                                """,
                        offer.getAgent().getFirstName(), offer.getAgent().getLastName(),offer.getId(),
                        offer.getApartment().getArea(), offer.getApartment().getTown().getTownName(),
                        offer.getPrice()))
                .collect(Collectors.joining());
    }
}
